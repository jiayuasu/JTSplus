package jtsplustest;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class GeometryToStringTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		GeometryFactory geometryFactory = new GeometryFactory();
		Coordinate coordinate1 = new Coordinate(1.0,2.0);
		Point point1 = geometryFactory.createPoint(coordinate1);
		point1.setUserData("payload1");
		
		Coordinate coordinate2 = new Coordinate(3.0,4.0);
		Point point2 = geometryFactory.createPoint(coordinate2);
		point2.setUserData("payload2");
		
		assert point1.toString().equals("POINT (1 2)	payload1");
		assert point2.toString().equals("POINT (3 4)	payload2");
		}

}
