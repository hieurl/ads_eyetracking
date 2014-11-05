/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Apps.java
 *
 * Created on Aug 8, 2011, 1:54:34 PM
 */
package AdsPosterExperiment;

import ExperimentSetup.Parser;
import ExperimentSetup.Plane;
import ExperimentSetup.SimulationAds;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author hieurl
 */
public class Apps extends javax.swing.JFrame {

    JFileChooser dataFC = new JFileChooser();
    JFileChooser imgFC = new JFileChooser();
    Hashtable dataFolderList = new Hashtable();
    
    Hashtable imageList = new Hashtable();
    
    int ROW = 20;
    int COL = 26;
    
    /** Creates new form Apps */
    public Apps() {
        initComponents();
        
        dataFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        imgFC.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                
                String ex = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 && i < s.length() - 1) {
                    ex = s.substring(i + 1).toLowerCase();
                }
         
                
                if (ex.equals("png") || ex.equals("jpg") || ex.equals("gif"))
                    return true;
                
                return false;
            }

            @Override
            public String getDescription() {
                return "PNG (*.png), JPEG(*.jpg), GIF(*.gif)";
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListData = new javax.swing.JList();
        jTextFieldDataFolder = new javax.swing.JTextField();
        jButtonDataFC = new javax.swing.JButton();
        jButtonSetReg = new javax.swing.JButton();
        jButtonRun = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jListData);

        jTextFieldDataFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDataFolderActionPerformed(evt);
            }
        });

        jButtonDataFC.setText("Choose datafolder");
        jButtonDataFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDataFCActionPerformed(evt);
            }
        });

        jButtonSetReg.setText("Set region");
        jButtonSetReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetRegActionPerformed(evt);
            }
        });

        jButtonRun.setText("Run");
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jTextFieldDataFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonDataFC)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonRun)
                        .addComponent(jButtonSetReg)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDataFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDataFC))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSetReg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
                        .addComponent(jButtonRun))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDataFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDataFCActionPerformed
        //if a file is choosen
        if (dataFC.showDialog(this, "Select") == JFileChooser.APPROVE_OPTION) {
            File sl = dataFC.getSelectedFile();
            File[] children = sl.listFiles();
            
            DefaultListModel listFolder = new DefaultListModel();
            listFolder.clear();
            int index = 0;
            try {
                for (int i = 0; i < children.length; ++i) {
                    if (children[i].isDirectory()) {
                        dataFolderList.put(children[i].getName(), children[i].getCanonicalPath());
                        listFolder.add(index++, children[i].getName());
                    }
                }
             
                jListData.setModel(listFolder);
                jTextFieldDataFolder.setText(sl.getCanonicalPath().toString());
            } catch (IOException ex) {
                Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //</editor-fold>
    }//GEN-LAST:event_jButtonDataFCActionPerformed

    private void jTextFieldDataFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDataFolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDataFolderActionPerformed

    private void jButtonSetRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetRegActionPerformed
        int index = jListData.getSelectedIndex();
        if (index >= 0) {
            String path = (String) dataFolderList.get(jListData.getSelectedValue());
            
            //choose the image
            imgFC.setCurrentDirectory(new File(path));
            if (imgFC.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION) {
                File imagePath = imgFC.getSelectedFile();
                try {
                    BufferedImage img = resizeImage(ImageIO.read(imagePath), 500, 400);
                    
                    //write image link to out put file
                    FileWriter fstream = new FileWriter(path + "\\screenDivision.txt");
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write(imagePath.getCanonicalPath()+"\r\n");
                    out.close();
                    
                    //open diaglog
                    PictureRegionSelect psl = new PictureRegionSelect(img, 500, 400, new File(path));
                    psl.show();
                    
                } catch (IOException ex) {
                    Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButtonSetRegActionPerformed

    private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
        try {
            String msg = validateOnRun();
            if (!msg.equals("OK")) {
                JOptionPane.showMessageDialog(rootPane, msg);
            } else {    
                runExperiment("sthere");
                JOptionPane.showMessageDialog(this, "Done!");
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonRunActionPerformed

    protected String validateOnRun() throws IOException {
        String path;
        Enumeration e = dataFolderList.elements();
        
        File f = new File (dataFC.getSelectedFile().getCanonicalFile() + "\\tl.txt");
        if (!f.exists())
            return "Corner files required!";
        
        f = new File (dataFC.getSelectedFile().getCanonicalFile() + "\\tr.txt");
        if (!f.exists())
            return "Corner files required!";
        
        f = new File (dataFC.getSelectedFile().getCanonicalFile() + "\\bl.txt");
        if (!f.exists())
            return "Corner files required!";
        
        f = new File (dataFC.getSelectedFile().getCanonicalFile() + "\\br.txt");
        if (!f.exists())
            return "Corner files required!";
        
        f = new File (dataFC.getSelectedFile().getCanonicalFile() + "\\setup.txt");
        if (!f.exists())
            return "setup.txt file required!";
        
        while (e.hasMoreElements()) {
            //check each data path for input.txt and ScreenDivision.txt
            path = (String) e.nextElement();
            f = new File(path+ "\\input.txt");
            if (!f.exists())
                return "input.txt for " + path + " needed!";
            
            f = new File(path+ "\\screenDivision.txt");
            if (!f.exists())
                return "screenDivision.txt for " + path + " needed!"; 
        }
        return "OK";
    }
    public BufferedImage resizeImage(final Image image, int width, int height) {
        double ratio = 1;
        
        int maxW = width;
        int maxH = height;
        
        //resize to fit width
        if (maxW != image.getWidth(null)) {
            ratio = 1.0*maxW/image.getWidth(null);
            
            height = (int) (image.getHeight(null)*ratio);
            width = maxW;
        }
        
        System.out.println(width + "~" + height);
        
        //if width is fit but height is still to big
        if (maxH < height) {
            ratio = 1.0*maxH/height;
            
            height = maxH;
            width = (int) (width*ratio);
        }
        
        System.out.println(width + "~" + height);
        
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        
        return bufferedImage;
    }
    
    protected void runExperiment(String outputFile) throws FileNotFoundException, IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(dataFC.getSelectedFile().getCanonicalFile() 
                + "\\setup.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String[] temp = reader.readLine().split("~");

        Plane pl = new Plane(0, 0, 1, Double.parseDouble(temp[1]) * (-1));

        temp = reader.readLine().split("~");
        int angle = Integer.parseInt(temp[1]);
        
        SimulationAds simulation = new SimulationAds(pl);
        simulation.CalculateCorners(dataFC.getSelectedFile(), angle);
        
        Enumeration e = dataFolderList.elements();
        String folderPath;
        while (e.hasMoreElements()) {            
            folderPath = null;
            folderPath = (String) e.nextElement();
            
            if (folderPath != null) {
                
                BufferedWriter out = new BufferedWriter(new FileWriter(folderPath + "\\data_ads.txt"));
                out.write(angle);
                out.close();
                
                //prepare input
                Parser p = new Parser();
                p.parse(folderPath + "\\input.txt", folderPath + "\\data_ads.txt");

                //get intersection points
                simulation.run(folderPath + "\\data_ads.txt", folderPath + "\\output_points.txt");

                //get output
                simulation.getPosition(folderPath + "\\output_points.txt", folderPath + "\\output_position.txt");

                //count
                //Utility.ScreenCounter(folderPath + "\\output_position.txt", folderPath + "\\output_counter.txt");
                ScreenCounter(folderPath + "\\output_position.txt", folderPath + "\\output_counter.txt", readSetupFile(folderPath + "\\screenDivision.txt"));
                
                //draw the image for gaze direction
                //get the image from file, resize to screen size
                FileInputStream iStream = new FileInputStream(folderPath + "\\screenDivision.txt");

                in = new DataInputStream(iStream);
                reader = new BufferedReader(new InputStreamReader(in));
                
                String imagePath = reader.readLine();
                
                BufferedImage img = resizeImage(ImageIO.read(new File(imagePath)), 1208, 1024);
                
                //create heatmap
                String cornerFile = dataFC.getSelectedFile().getCanonicalFile() + "\\Corner.txt";
                HeatMap heatmap = new HeatMap(cornerFile, folderPath + "\\output_points.txt", 
                        img, new Dimension(1208, 1024), 4);
                BufferedImage newImg = heatmap.draw();
                ImageIO.write(newImg, "png", new File(folderPath, "output.png"));
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    protected void ScreenCounter(String pointFileInput, String ouput, Hashtable screenSetup) throws FileNotFoundException, IOException {
        FileInputStream iStream = new FileInputStream(pointFileInput);
        DataInputStream in = new DataInputStream(iStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        LinkedList<String> screenList = new LinkedList<String>();
        LinkedList<Integer> screenCount = new LinkedList<Integer>();
        
        String line;
        Integer cell;
        
        //for each cell, check what screen it belongs to
        int lineCount = 0;
        String screen = "";
        while ((line = reader.readLine()) != null) {
            lineCount ++;
            
            cell = Integer.parseInt(line.trim());
            
            screen = (String) screenSetup.get(cell);            
            
            if (screen == null) {
                screen = "Out of screen";
            }
            
            if (screenList.contains(screen)) {
                int index = screenList.indexOf(screen);
                screenCount.set(index, screenCount.get(index)+1);
            }
            else {
                screenList.addLast(screen);
                screenCount.addLast(1);
            }
        }
        
        //read all screen values and calculate
        BufferedWriter out = new BufferedWriter(new FileWriter(ouput));
        //write to file
        for (int i = 0; i < screenList.size(); ++i) {
            out.write(screenList.get(i) + ": " + screenCount.get(i) + "\r\n");
        }
        out.close();
    }
    
    protected Hashtable readSetupFile(String filename) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader(new DataInputStream(new FileInputStream(filename))));
        
        String lineIn;
        Hashtable screen = new Hashtable();
        String[] rowCell;
        int cellCounter = 0;
        
        //skip first line of image url
        lineIn = reader.readLine();
        
        while ((lineIn = reader.readLine()) != null) {
            //get the screen name of each cell
            rowCell = lineIn.split(" ");
            for (int i = 0; i < COL; ++ i) {
                //for each cell, put it into hashtable with corresponding screen name
                screen.put(cellCounter, rowCell[i]);
                cellCounter ++;
            }
        }
        
        return screen;
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Apps a = new Apps();
                a.setLocationRelativeTo(null);
                a.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDataFC;
    private javax.swing.JButton jButtonRun;
    private javax.swing.JButton jButtonSetReg;
    private javax.swing.JList jListData;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDataFolder;
    // End of variables declaration//GEN-END:variables

}
