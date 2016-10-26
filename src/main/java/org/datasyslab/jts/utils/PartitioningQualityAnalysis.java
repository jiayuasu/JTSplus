package org.datasyslab.jts.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.index.quadtree.Quadtree;
import com.vividsolutions.jts.index.strtree.GeometryItemDistance;
import com.vividsolutions.jts.index.strtree.STRtree;

public class PartitioningQualityAnalysis {

	public static void main(String[] args) {

		//Declare a partitioning method
			//Quadtree testtree=new Quadtree();
		  STRtree testtree=new STRtree();
		  //Quadtree testtree=new Quadtree();
		//This is to generate random test rectangles
		Random rand = new Random();
		//This is to store the entire test rectangles
		List<Envelope> spatialobjects=new ArrayList<Envelope>();
		//This is to store how times one spatial object has been overlapped/contained
		List<Integer> spatialobjectsStatus=new ArrayList<Integer>();
		//This is to count how many rectangles fall into each boundary
		List<Integer> counter=new ArrayList();
		//Total number of records
		int totalTestRectangles=3000;
		//Sum of records per boundary
		int sum=0;
		//Generate test data
		GeometryFactory fact = new GeometryFactory();
		
		for(int i=0;i<totalTestRectangles;i=i+20)
		{
			
			spatialobjects.add(new Envelope(i,i+rand.nextInt(2),i,i+rand.nextInt(2)));
			spatialobjects.add(new Envelope(i,i+rand.nextInt(2),i,i+rand.nextInt(2)));
			spatialobjects.add(new Envelope(i,i+rand.nextInt(2),i,i+rand.nextInt(2)));
			spatialobjects.add(new Envelope(i,i+rand.nextInt(2),i,i+rand.nextInt(2)));
			spatialobjects.add(new Envelope(i,i+rand.nextInt(2),i,i+rand.nextInt(2)));
			
		}
		//Build trees
		for(int i=0;i<spatialobjects.size();i++)
		{
			testtree.insert(spatialobjects.get(i), fact.toGeometry(spatialobjects.get(i)));
			//Initialize object status in one go
			spatialobjectsStatus.add(0);
		}
		//Retrieve boundaries
		List<Envelope> boundaries=testtree.queryBoundary();
		
		System.out.println("This tree returns "+boundaries.size()+" boundaries");
		//This is to print out boundaries and initialize each counter to 0
		for(int i=0;i<boundaries.size();i++)
		{
			counter.add(0);
			//System.out.print(boundaries.get(i).getMinX()+",");
			//System.out.print(boundaries.get(i).getMaxX()+",");
			//System.out.print(boundaries.get(i).getMinY()+",");
			//System.out.println(boundaries.get(i).getMaxY());
		}
		//Assign rectangle to each boundary: A boundary contains/intersects a rectangle
		for(int i=0;i<spatialobjects.size();i++)
		{
			for(int j=0;j<boundaries.size();j++)
			{
				//You can use ".contains()" or ".intersects()" for full contain or intersect
				if(boundaries.get(j).contains(spatialobjects.get(i)))
				{
					counter.set(j, counter.get(j)+1);
					spatialobjectsStatus.set(i,(spatialobjectsStatus.get(i)+1));
				}
			}
		}
		//Check the spatial relationship between boundaries: A boundary contains/intersects a boundary
		for(int i=0;i<boundaries.size();i++)
		{
			for(int j=0;j<boundaries.size();j++)
			{
				if(boundaries.get(i).contains(boundaries.get(j))&& i!=j)
				{
					//Print out contain/intersect boundary pair
					System.out.println("Boundary contain/intersect once " + i+ ":"+j);
				}
			}
		}
		for(int i=0;i<spatialobjectsStatus.size();i++)
		{
			if(spatialobjectsStatus.get(i)==0)//||spatialobjectsStatus.get(i)>1)
			{
				System.out.println("Warning: Object "+i+" has been touched: "+spatialobjectsStatus.get(i));
			}
			
		}
		//This is to output the counter value. You can know the data distribution in these boundaries.
		for(int i=0;i<counter.size();i++)
		{
			sum=sum+counter.get(i);
			if(counter.get(i)==0)
			{
				System.out.println("Warning: Boundary "+i+" has no rectangles inside");
			}
			System.out.println("Records in Boundary "+i+": "+counter.get(i));
		}
		
		System.out.println("Total number of test rectangles: "+totalTestRectangles/20*5+"; Sum of counters: "+sum);
		
		Object[] knn = testtree.kNearestNeighbour(new Envelope(-98.6361828, -95.0993852,46.88333326666667,48.392923),fact.toGeometry(new Envelope(-98.6361828, -95.0993852,46.88333326666667,48.392923)),new GeometryItemDistance(), 10);
		System.out.println("The K Nearest Neighbors are as follows: ");
		for(Object e:knn)
		{
			System.out.println((Geometry)e);
		}
	}

}