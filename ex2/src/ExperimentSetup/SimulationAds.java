/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExperimentSetup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author hieurl
 */
public class SimulationAds extends Simulation {

    int ROW = 20;
    int COL = 26;
    
    public SimulationAds(Plane pl) {
        this.pl = pl;

        topleft = new Point3D();
        topright = new Point3D();
        botleft = new Point3D();
        botright = new Point3D();
    }
    @Override
    public void getPosition(String inFile, String outFile) {
        FileInputStream iStream;
		
	String line_in;
	String [] values;
	
        double screenW = Math.abs(topleft.getX() - topright.getX());
        double cellW = screenW / COL;
        
        double screenH = Math.abs(topleft.getY() - botleft.getY());
        double cellH = screenH / ROW;
        
        int row, col;
        int cell;
	
        double pX = 0, pY = 0;
        String position = "";

        try {
            iStream = new FileInputStream(inFile);

            DataInputStream in = new DataInputStream(iStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            FileWriter fstream = new FileWriter(outFile);
            BufferedWriter out = new BufferedWriter(fstream);
            
            while ((line_in = reader.readLine()) != null) {
                values = line_in.split("~");
                pX = Double.parseDouble(values[0]);
                pY = Double.parseDouble(values[1]);
                
                //get column
                double distanceX = Math.abs(pX - topleft.getX());
                col = (int) (distanceX / cellW);
                
                //get row
                double distanceY = Math.abs(pY - topleft.getY());
                row = (int) (distanceY / cellH);
                
                //get cell number
                position = Integer.toString(row * COL + col);
                
                out.write(position + "\r\n");
            }
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }   
    }
}
