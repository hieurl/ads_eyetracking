/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AdsPosterExperiment;

import ExperimentSetup.Point3D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @WhatIDo:
 * - Create grid table for the image, as small as possible
 * - Count the number of appearance for each cell
 * - Find the range of data
 * - Determine color range (reference heatmap of http://www.mbeckler.org/heatMap/)
 * - For each cell, compare its appearance with the range and paint with corresponding color
 * 
 * @author hieurl
 */
public final class HeatMap {
    String dataPath;
    String cornerPath;
    Image bg;
    
    Point3D topleft = new Point3D(), topright =  new Point3D(), 
            botleft = new Point3D(), botright = new Point3D();
    
    int COL = 100;
    int ROW = 80;
    
    int data[][] = new int[COL][ROW];
    int dataColorIndex[][] = new int[COL][ROW];
    
    Color[] colorRange;
    
    int numSteps;
    
    Dimension screenSize;
    
    public HeatMap(String cornerCoordinate, String dataFile, Image background, Dimension screenSizeInPixel, int steps) {
        this.dataPath = dataFile;
        this.cornerPath = cornerCoordinate;
        this.bg = background;
        this.screenSize = screenSizeInPixel;
        this.numSteps = steps;
        
        //initinalize data with value = 0
        for (int i = 0; i < COL; ++i) {
            for (int j = 0; j < ROW; ++j) {
                this.data[i][j] = 0;
            }
        }
        
        readCorner();
        readData();
        colorRange = createGradient(new Color(0, 255, 0, 0), new Color(255, 0, 0, 255));
        setDataColor();
    }
    
    protected void readCorner(){
        FileInputStream iStream = null;
        try {
            iStream = new FileInputStream(cornerPath);
            DataInputStream in = new DataInputStream(iStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            
            String values[] = line.split("~");
            
            //topleft
            topleft.setX(Double.parseDouble(values[0]));
            topleft.setY(Double.parseDouble(values[1]));
           
            //top right
            line = reader.readLine();
            values = line.split("~");
            topright.setX(Double.parseDouble(values[0]));
            topright.setY(Double.parseDouble(values[1]));
            
            //bot left
            line = reader.readLine();
            values = line.split("~");
            botleft.setX(Double.parseDouble(values[0]));
            botleft.setY(Double.parseDouble(values[1]));
            
            //bot right
            line = reader.readLine();
            values = line.split("~");
            botright.setX(Double.parseDouble(values[0]));
            botright.setY(Double.parseDouble(values[1]));
        } catch (IOException ex) {
            Logger.getLogger(HeatMap.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                iStream.close();
            } catch (IOException ex) {
                Logger.getLogger(HeatMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected void readData(){
        
        double sizeW = Math.abs(topleft.getX() - topright.getX());
        double cellW = sizeW / COL;
        
        double sizeH = Math.abs(topleft.getY() - botleft.getY());
        double cellH = sizeH / ROW;
        
        try {
            FileInputStream iStream = new FileInputStream(dataPath);

            DataInputStream in = new DataInputStream(iStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line_in;
            String[] values;

            double pX, pY;
            int pCol, pRow;
            while ((line_in = reader.readLine()) != null) {
                values = line_in.split("~");
                pX = Double.parseDouble(values[0]);
                pY = Double.parseDouble(values[1]);

                //get column
                double distanceX = Math.abs(pX - topleft.getX());
                pCol = (int) (distanceX / cellW);

                //get row
                double distanceY = Math.abs(pY - topleft.getY());
                pRow = (int) (distanceY / cellH);
                
                //System.out.println(pCol + "~" + pRow);
                
                if (pCol < COL && pRow < ROW) {
                    data[pCol][pRow] += 1;
                }
            }
            
            System.out.println("");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
    
    
    //function copied from http://www.mbeckler.org/heatMap/
    protected Color[] createGradient(final Color one, final Color two)
    {
        int r1 = one.getRed();
        int g1 = one.getGreen();
        int b1 = one.getBlue();
        int a1 = one.getAlpha();

        int r2 = two.getRed();
        int g2 = two.getGreen();
        int b2 = two.getBlue();
        int a2 = two.getAlpha();

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newA = 0;
        
        
        Color[] gradient = new Color[numSteps];
        double iNorm;
        for (int i = 0; i < numSteps; i++)
        {
            iNorm = i / (double)numSteps;
            newR = (int) (r1 + iNorm * (r2 - r1));
            newG = (int) (g1 + iNorm * (g2 - g1));
            newB = (int) (b1 + iNorm * (b2 - b1));
            newA = (int) (a1 + iNorm * (a2 - a1));
            gradient[i] = new Color(newR, newG, newB, newA);
        }

        System.out.println(gradient.length + "~" +numSteps);
        return gradient;
    }
    
    protected void setDataColor() {
        int max = 0, min = 0;
        
        //get min and max value of data
        for (int i = 0; i < COL; ++i) {
            for (int j = 0; j < ROW; ++j) {
                max = Math.max(max, data[i][j]);
                min = Math.min(min, data[i][j]);
            }
        }
        
        //compare each value with min max to find its level, assign that color
        int count = 0;
        for (int i = 0; i < COL; ++i) {
            for (int j = 0; j < ROW; ++j) {
                double norm = (1.0*data[i][j] - min)/(max - min);
                count += data[i][j];
                
                dataColorIndex[i][j] = (int) Math.floor(norm*numSteps);
                if (dataColorIndex[i][j] == numSteps)
                    dataColorIndex[i][j] = numSteps - 1;
                
                //System.out.println(dataColorIndex[i][j]);
                
                //System.out.println(max + "~" + min + "~" + data[i][j] +"!"+ norm + "~" + dataColorIndex[i][j]);
            }
        }
        
        //System.out.println(count);
    }
    
    public BufferedImage draw() {
        BufferedImage overlay = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = overlay.createGraphics();
        
        double cellW = screenSize.width / COL;
        double cellH = screenSize.height / ROW;
        
        //System.out.println(colorRange.length);
        for (int i = 0; i < COL; ++i) {
            for (int j = 0; j < ROW; ++j) {
                g.setColor(colorRange[dataColorIndex[i][j]]);
                g.fillOval(i*(int)cellW - ((int)cellW*2), j*(int)cellH - (int)cellH*2, (int)cellW*4, (int)cellH*4);
                
                //System.out.println((int)(i*cellW) + "~" + (int)(j*cellH) + "~" 
                //        + (int)cellW + "~" +(int)cellH + "~" + dataColorIndex[i][j]);
            }
        }
        
        BufferedImage rt = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
        g = rt.createGraphics();
        
        g.drawImage(bg, (Math.abs(screenSize.width - bg.getWidth(null)))/2, 
                (Math.abs(screenSize.height - bg.getHeight(null)))/2, null);
        g.drawImage(overlay, 0, 0, null);
        
        return rt;
    }
}
