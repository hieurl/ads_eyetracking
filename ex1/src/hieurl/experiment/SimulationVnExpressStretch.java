package hieurl.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimulationVnExpressStretch extends Simulation {
	
	public SimulationVnExpressStretch(Plane pl) {
		this.pl = pl;
		
		topleft = new Point3D();
		topright = new Point3D();
		botleft = new Point3D();
		botright = new Point3D();
	}
	
	@Override
	public void getPosition(String infile, String outfile) {
		FileInputStream iStream;
		
		String line_in;
		String [] values;
		
	    double x = topleft.getX();
        double x4 = topright.getX();
        double x2 = (x + x4)/2;
        double x1 = (x + x2)/2;
        double x3 = (x4 + x2)/2;
        
        x1 = (x + x1)/2;
        x3 = (x3 + x4)/2;
        
        double y = botleft.getY();
        double y4 = topleft.getY();
        double y2 = (y + y4)/2;
        double y1 = (y + y2)/2;
        double y3 = (y4 + y2)/2;
    
		
		double pX = 0, pY = 0;
		String position = "";
		
		try {
			iStream = new FileInputStream(infile);
		
			DataInputStream in = new DataInputStream(iStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));			

			FileWriter fstream = new FileWriter(outfile);
			BufferedWriter out =  new BufferedWriter(fstream);
			
			out.write("Screen 1\r\nScreen 2\r\nScreen 3\r\nScreen 4\r\nScreen 5\r\nScreen 6\r\nScreen 7\r\nScreen 8\r\n");
			out.write("Screen 9\r\nScreen 10\r\nScreen 11\r\nScreen 12\r\nScreen 13\r\nScreen 14\r\nScreen 15\r\nScreen 16\r\n");
		
			while((line_in = reader.readLine()) != null) {
				values = line_in.split("~");
				pX = Double.parseDouble(values[0]);
				pY = Double.parseDouble(values[1]);
				
				if(((x <= pX) && (pX < x1)) && ((y3<pY) && (pY<=y4))) {
					position = "Screen 1";
				}
				else if (((x1 <= pX) && (pX < x2)) && ((y3<pY) && (pY<=y4))) {
					position = "Screen 2";
				}
				else if (((x2 <= pX) && (pX < x3)) && ((y3<pY) && (pY<=y4))) {
					position = "Screen 3";
				}
				else if (((x3 <= pX) && (pX < x4)) && ((y3<pY) && (pY<=y4))) {
					position = "Screen 4";
				}
				else if (((x <= pX) && (pX < x1)) && ((y2<pY) && (pY<=y3))) {
					position = "Screen 5";
				}
				else if (((x1 <= pX) && (pX < x2)) && ((y2<pY) && (pY<=y3))) {
					position = "Screen 6";
				}
				else if (((x2 <= pX) && (pX < x3)) && ((y2<pY) && (pY<=y3))) {
					position = "Screen 7";
				}
				else if (((x3 <= pX) && (pX < x4)) && ((y2<pY) && (pY<=y3))) {
					position = "Screen 8";
				}
				else if (((x <= pX) && (pX < x1)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 9";
				}
				else if (((x1 <= pX) && (pX < x2)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 10";
				}
				else if (((x2 <= pX) && (pX < x3)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 11";
				}
				else if (((x3 <= pX) && (pX < x4)) && ((y1<pY) && (pY<=y2))) {
					position = "Screen 12";
				}
				else if (((x <= pX) && (pX < x1)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 13";
				}
				else if (((x1 <= pX) && (pX < x2)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 14";
				}
				else if (((x2 <= pX) && (pX < x3)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 15";
				}
				else if (((x3 <= pX) && (pX < x4)) && ((y<pY) && (pY<=y1))) {
					position = "Screen 16";
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
