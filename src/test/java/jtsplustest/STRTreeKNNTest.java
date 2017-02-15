package jtsplustest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.datasyslab.jts.utils.DistanceComparator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.index.strtree.GeometryItemDistance;
import com.vividsolutions.jts.index.strtree.STRtree;

public class STRTreeKNNTest {
	
	public static STRtree strtree;
	public static int topK;
	public static int totalRecords;
	public static GeometryFactory geometryFactory;
	public static Coordinate coordinate;
	public static Point queryCenter;
	public static int valueRange;
	public static List<Geometry> testDataset;
	public static List<Geometry> correctData;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		topK = 10000;
		totalRecords = 100000;
		geometryFactory = new GeometryFactory();
		coordinate = new Coordinate(10.1,-10.1);
		queryCenter = geometryFactory.createPoint(coordinate);
		valueRange = 1000;
		testDataset = new ArrayList<Geometry>();
		correctData = new ArrayList<Geometry>();
		Random random = new Random();
		/*
		 * Generate the test data set
		 */
		for(int i=0;i<totalRecords;i++)
		{
		    Coordinate coordinate = new Coordinate(-100+random.nextInt(valueRange)*1.1,random.nextInt(valueRange)*(-5.1));
		    Point spatialObject = geometryFactory.createPoint(coordinate);
		    testDataset.add(spatialObject);
		}
		/*
		 * Sort the original data set and make sure the elements are sorted in an ascending order
		 */
		Collections.sort(testDataset,new DistanceComparator(queryCenter,true));
		/*
		 * Get the correct top K
		 */
		for(int i=0;i<topK;i++)
		{
			correctData.add(testDataset.get(i));
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCorrectness() {
		strtree = new STRtree();
		DistanceComparator distanceComparator = new DistanceComparator(this.queryCenter,true);
		for(int i=0;i<totalRecords;i++)
		{
		    strtree.insert(testDataset.get(i).getEnvelopeInternal(), testDataset.get(i));
		}
		/*
		 * Shoot a random query to make sure the STR-Tree is built.
		 */
		strtree.query(new Envelope(1+0.1,1+0.1,2+0.1,2+0.1));
		Object[] testTopK = (Object[])strtree.kNearestNeighbour(queryCenter.getEnvelopeInternal(), queryCenter, new GeometryItemDistance(), topK);
		List topKList = Arrays.asList(testTopK);
		Collections.sort(topKList,distanceComparator);
		int difference = 0;
		for(int i = 0;i<this.topK;i++)
		{
			if(distanceComparator.compare(correctData.get(i), (Geometry)topKList.get(i))!=0)
			{
				difference++;
			}
		}
		assert difference==0;
	}
}
