package hieurl.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimulationQuarterScreen extends Simulation {
	
	
	public SimulationQuarterScreen(Plane pl) {
		this.topleft = new Point3D();
		this.topright = new Point3D();
		this.botleft = new Point3D();
		this.botright = new Point3D();
		
		this.pl = pl;
		this.screen_rotation_angle = 0;
	}
	public SimulationQuarterScreen(Point3D tl, Point3D tr, Point3D bl, Point3D br, Plane pl) {
		this.pl = pl;
		
		this.screen_rotation_angle = Utility.GetCornerRotationCorner(tl, tr);
		Point3D r[] = Utility.RefineScreenCornerPosition(tl, tr, bl, br, 0);
		this.topleft = r[0];
		this.topright = r[1];
		this.botleft = r[2]; 
		this.botright = r[3];
		/*
		System.out.println(this.screen_rotation_angle);
		System.out.println(topleft.getX() + "~" + topleft.getY());
		System.out.println(topright.getX() + "~" + topright.getY());
		System.out.println(botleft.getX() + "~" + botleft.getY());
		System.out.println(botright.getX() + "~" + botright.getY());
		*/
	}
	
	//@Override
	
	@Override
	public void getPosition(String infile, String outfile){
		FileInputStream iStream;
		
		String line_in;
		String [] values;
		
		double x12 = (topleft.getX() + topright.getX())/2;
		double y13 = (topleft.getY() + botleft.getY())/2;
		double y24 = (topright.getY() + botright.getY())/2;
		double x34 = (botleft.getX() + botright.getX())/2;
		
		double x = 0, y = 0;
		String position = "";
		
		try {
			iStream = new FileInputStream(infile);
		
			DataInputStream in = new DataInputStream(iStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));			

			FileWriter fstream = new FileWriter(outfile);
			BufferedWriter out =  new BufferedWriter(fstream);
			
			out.write("Screen 1\r\nScreen 2\r\nScreen 3\r\nScreen 4\r\n");
		
			while((line_in = reader.readLine()) != null) {
				values = line_in.split("~");
				x = Double.parseDouble(values[0]);
				y = Double.parseDouble(values[1]);
				
				if((x < x12) && (x >= topleft.getX()) && (y <= topleft.getY()) && (y > y13)) {
					position = "Screen 1";
				}
				else if ((x >= x12) && (x < topright.getX()) && (y <= topright.getY()) && (y > y24)) {
					position = "Screen 2";
				}
				else if ((x < x34) && (x > botleft.getX()) && (y <= y13) && (y > botleft.getY())) {
					position = "Screen 3";
				}
				else if ((x < botright.getX()) && (x >= x34) && (y <= y24) && (y > botright.getY())) {
					position = "Screen 4";
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
