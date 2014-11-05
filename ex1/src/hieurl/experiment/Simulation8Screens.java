package hieurl.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Simulation8Screens extends Simulation {
	
	public Simulation8Screens() {
		topleft = new Point3D();
		topright = new Point3D();
		botleft = new Point3D();
		botright = new Point3D();
		
		pl = new Plane();
		
		screen_rotation_angle = 0;
	}
	
	public Simulation8Screens(Point3D tl, Point3D tr, Point3D bl, Point3D br, Plane plane) {
		pl = plane;
		
		screen_rotation_angle = Utility.GetCornerRotationCorner(tl, tr);
		Point3D r[] = Utility.RefineScreenCornerPosition(tl, tr, bl, br, 0);
		
		topleft = r[0];
		topright = r[1];
		botleft = r[2]; 
		botright = r[3];
	}
	
	
	@Override
	public void getPosition(String infile, String outfile) {
		FileInputStream iStream;
		
		String line_in;
		String [] values;
		
	    double x = topleft.getX();
        double x4 = topright.getX();
        double y = botleft.getY();
        double y2 = topleft.getY();
	
        double x2 = (x + x4)/2;
        double x1 = (x + x2)/2;
        double x3 = (x4 + x2)/2;
           
        double y1 = (y+y2)/2;
    
		
		double pX = 0, pY = 0;
		String position = "";
		
		try {
			iStream = new FileInputStream(infile);
		
			DataInputStream in = new DataInputStream(iStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));			

			FileWriter fstream = new FileWriter(outfile);
			BufferedWriter out =  new BufferedWriter(fstream);
			
			out.write("Screen 1\r\nScreen 2\r\nScreen 3\r\nScreen 4\r\nScreen 5\r\nScreen 6\r\nScreen 7\r\nScreen 8\r\n");
		
			while((line_in = reader.readLine()) != null) {
				values = line_in.split("~");
				pX = Double.parseDouble(values[0]);
				pY = Double.parseDouble(values[1]);
				
				if(((x <= pX) && (pX < x1)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 1";
				}
				else if (((x1 <= pX) && (pX < x2)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 2";
				}
				else if (((x2 <= pX) && (pX < x3)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 3";
				}
				else if (((x3 <= pX) && (pX < x4)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 4";
				}
		                else if(((x <= pX) && (pX < x1)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 5";
				}        
		                else if(((x1 <= pX) && (pX < x2)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 6";
				}
		                else if(((x2 <= pX) && (pX < x3)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 7";
				}
		                else if(((x3 <= pX) && (pX < x4)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 8";
				}        
				else {
					position = "Out of screen";
				}		
				
				out.write(position + "\r\n");
			}
			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
