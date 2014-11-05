/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AdsPosterExperiment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author hieurl
 */
public class PictureRegionSelect {

    /**
     * @param args the command line arguments
     */
    int WIDTH = 500;
    int HEIGHT = 400;
    
    int ROW = 20;
    int COL = 26;
    
    Image img;
    JFrame frame = new JFrame("Picture Region Select");
    JTable table;
    
    File outPath;

    public PictureRegionSelect(final Image img, int w, int h, File outputPath) {
        this.img = img;
        this.WIDTH = w;
        this.HEIGHT = h;
        this.outPath = outputPath;

        table = new JTable(ROW, COL) {

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // We want renderer component to be transparent so background image is visible
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
            // Hard coded value. In your sub-class add a function for this.
            ImageIcon image = new ImageIcon(img);

            @Override
            public void paint(Graphics g) {
                // First draw the background image - tiled 
                Dimension d = getSize();

                int x = (d.width - image.getIconWidth()) / 2;
                int y = (d.height - image.getIconHeight()) / 2;
                g.drawImage(image.getImage(), x, y, null, null);

                super.paint(g);
            }
        };
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                Window win = e.getWindow();
                win.setVisible(false);
                win.dispose();
            }
        });

        // Create your own sub-class of JTable rather than using anonymous class

        // Set the table transparent
        table.setOpaque(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);

        table.setSize(WIDTH, HEIGHT);
        table.setRowHeight(HEIGHT/ROW);
        table.setTableHeader(null);

        table.setForeground(Color.red);

        TableColumn col = null;
        for (int i = 0; i < COL; ++i) {
            col = table.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTH/COL);
        }
    }

    public void show() {
        JScrollPane sp = new JScrollPane(table);
        sp.setSize(WIDTH, HEIGHT);
        
        JButton b = new JButton("Save");
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeToFile("screenDivision.txt");
                    JOptionPane.showMessageDialog(null, "OK!");
                } catch (IOException ex) {
                    Logger.getLogger(PictureRegionSelect.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        frame.getContentPane().add(sp, BorderLayout.CENTER);
        frame.getContentPane().add(b, BorderLayout.SOUTH);
        frame.setSize(550, 500);
        frame.setLocationRelativeTo(null);
        frame.show();
    }
    
    protected void writeToFile(String filename) throws IOException {
        if (!outPath.exists()) {
            outPath.mkdir();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(outPath.getCanonicalFile() + "\\" + filename, true));
        
        String val;
        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                val = (String) table.getValueAt(i, j);
                
                if (val == null)
                    val = "0";
                else {
                    val.trim();
                    
                    if (val.equals("") || val.equals(" "))
                        val = "0";
                }
                
                out.append(val + " ");
            }
            out.append("\r\n");
        }
        
        out.close();
    }
}
