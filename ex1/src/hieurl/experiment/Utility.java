package hieurl.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class Utility {
	public static double[] Rotate(double x, double y, double angle) {
		double[] rt = new double[2];
		
		rt[0] = (x*Math.cos(angle) - y*Math.sin(angle));
		rt[1] = x*Math.sin(angle) + y*Math.cos(angle);
		
		return rt;
	}
	
	public static UnitVector GetUnitVector(double pitch, double yaw) {
		UnitVector rt = new UnitVector();
		
		//pitch = Math.PI/2 - pitch;
		yaw = Math.PI/2 + yaw;
		
		try {
			rt.setX((Math.cos(yaw))*(Math.cos(pitch)));
			rt.setY(Math.sin(pitch));
			rt.setZ(Math.cos(pitch)*Math.sin(yaw));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rt;
	}
	
	public static FLLineEquation GetEquation(UnitVector u, Point3D p) {
		FLLineEquation rt = new FLLineEquation(u, p);
		
		return rt;
	}
	
	public static Point3D GetIntersectionPoint(FLLineEquation equation, double z) {
		Point3D rt = new Point3D();
		
		
		
		return rt;
	}

	public static Point3D CalculateIntersectionPoint(FLLineEquation e, Plane pl) {
		Point3D p = new Point3D();
		
		double a, b, c, d;
		double x0, y0, z0;
		double a1, a2, a3;
		
		a = pl.geta();
		b = pl.getb();
		c = pl.getc();
		d = pl.getd();
		
		x0 = e.getp().getX();
		y0 = e.getp().getY();
		z0 = e.getp().getZ();
		
		a1 = e.getUnitVector().getX();
		a2 = e.getUnitVector().getY();
		a3 = e.getUnitVector().getZ();
		
		double t = -(a*x0 + b*y0 + c*z0 + d)/(a1*a + a2*b + a3*c);
		
		p.setX(x0 + t*a1);
		p.setY(y0 + t*a2);
		p.setZ(z0 + t*a3);
		
		return p;
	}
	
	public static UnitVector ShiftUnitVector(UnitVector u, Point3D p) {
		u.setX(u.getX()+p.getX());
		u.setY(u.getY()+p.getY());
		u.setZ(u.getZ()+p.getZ());
		
		return u;
	}
	
	public static Point3D ShiftEyeCoordinate(Point3D sp, Point3D np) {
		sp.setX(sp.getX()+np.getX());
		sp.setY(sp.getY()+np.getY());
		sp.setZ(sp.getX()+np.getZ());
		
		return sp;
	}
	
	//TODO
	public static double GetCornerRotationCorner(Point3D tl, Point3D tr) {
		double angle = Math.atan(Math.abs((tr.getY()-tl.getY())/(tr.getX()-tl.getY())));
		return angle;
	}
	public static Point3D[] RefineScreenCornerPosition(Point3D tl, Point3D tr, Point3D bl, Point3D br, double angle) {
		Point3D rt[] = new Point3D[4];
		/*
		//change to coordinate at top left
		tr.setX(tr.getX()-tl.getX());
		tr.setY(tr.getY()-tl.getY());
		
		bl.setX(bl.getX()-tl.getX());
		bl.setY(bl.getY()-tl.getY());
		
		br.setX(br.getX()-tl.getX());
		br.setY(br.getY()-tl.getY());
		
		//rotate at top left
		double td[] = Utility.Rotate(tr.getX(), tr.getY(), -angle);
		tr.setX(td[0]);
		tr.setY(td[1]);
		
		td = Utility.Rotate(bl.getX(), bl.getY(), -angle);
		bl.setX(td[0]);
		bl.setY(td[1]);
		
		td = Utility.Rotate(br.getX(), br.getY(), -angle);
		br.setX(td[0]);
		br.setY(td[1]);
		
		//back to original coordinate system
		tr.setX(tr.getX()+tl.getX());
		tr.setY(tr.getY()+tl.getY());
		
		bl.setX(bl.getX()+tl.getX());
		bl.setY(bl.getY()+tl.getY());
		
		br.setX(br.getX()+tl.getX());
		br.setY(br.getY()+tl.getY());
		*/
		
		tl.setX((tl.getX() + bl.getX())/2);
		tl.setY((tl.getY() + tr.getY())/2);
		
		tr.setX((tr.getX() + br.getX())/2);
		tr.setY(tl.getY());
		
		bl.setX(tl.getX());
		bl.setY((bl.getY() + br.getY())/2);
		
		br.setX(tr.getX());
		br.setY(bl.getY());
		
		rt[0] = tl;
		rt[1] = tr;
		rt[2] = bl;
		rt[3] = br;
		
		return rt;
	}
	
	public static Point3D GetCornerPosition(String file) throws IOException {
		Point3D p = new Point3D();
		
		FileInputStream iStream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(iStream);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		//read all value of corner position, put in a 2 dimentional array
		String r = "";
		LinkedList<String> values = new LinkedList<String>(); 
		while((r = reader.readLine()) != null) {
			values.add(r);
		}
			
		//skip first 1/4th rows and last 1/4th rows
		
		int skip = values.size()/4;
		
		int count = values.size() - skip;
		//initialize first value of p
		
		String ts[] = values.get(skip+1).split("~");
		p.setX(Double.parseDouble(ts[0]));
		p.setY(Double.parseDouble(ts[1]));
		
		for(int i = skip+2; i < count; ++i) {
			ts = values.get(i).split("~");
			//use weighted average to calculate the position of the corner
			p.setX(p.getX()*0.75 + Double.parseDouble(ts[0])*0.25);
			p.setY(p.getY()*0.75 + Double.parseDouble(ts[1])*0.25);
		}
		
		return p;
	}
	
	public static void ScreenCounter(String input, String output) throws IOException {
		FileInputStream iStream = new FileInputStream(input);
		DataInputStream in = new DataInputStream(iStream);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		LinkedList<String> ScreenList = new LinkedList<String>();
		LinkedList<Integer> ScreenCount = new LinkedList<Integer>();
		
		String line;
		
		int index;
		int numberOfLine = 0;
		
		//count the number of point for each screen
		while ((line = reader.readLine()) != null) {
			numberOfLine++;
			if ((index = ScreenList.indexOf(line)) == -1) {
				ScreenList.add(line);
				index = ScreenList.indexOf(line);
				ScreenCount.add(0);
			}else {
				ScreenCount.set(index, ScreenCount.get(index) + 1);
			}
		}
		
		BufferedWriter out = new BufferedWriter(new FileWriter(output));
		
		//write to file
		for (int i = 0; i < ScreenList.size(); ++i) {
			out.write(ScreenList.get(i) + ": " + ScreenCount.get(i) + " (" + ScreenCount.get(i)*100/numberOfLine + "%)\r\n");
		}
		
		out.close();
	}
}
