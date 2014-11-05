package hieurl.experiment;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Experiment {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//run_one();
		//run_two();
		JLabel running_lable = new JLabel("Running, please wait!");
		JFrame frame = new JFrame("Running");
		JPanel panel = new JPanel();
		panel.add(running_lable);
		
		frame.getContentPane().add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(200, 200));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setVisible(true);
		
		run_three();
		frame.dispose();
		JOptionPane.showMessageDialog(null, "Done!");
	}
	
	public static void run_one() throws IOException {
		//process:
		//read 4 text files of 4 corner, for each file, run the experiment to get the 
		//intersection point with screen
		
		//get the 4 corner by weighted mean (Utility.GetCornerPosition)
		
		//pass 4 corner to the new experiment
		
		//Test get corner by weighted average
		//Point3D a = Utility.GetCornerPosition("tl_.txt");
		//System.out.print(a.getX() + "~" + a.getY());		
		
		//TODO!!!!!
		Plane pl = new Plane(0, 0, 1, -0.25);
		String angle = "30\r\n";
		
		
		Point3D topleft = new Point3D();
		Point3D topright = new Point3D();
		Point3D botleft = new Point3D();
		Point3D botright = new Point3D();
		
		//Create a simulation to find the coordinate of 4 corners of the screen
		SimulationQuarterScreen s = new SimulationQuarterScreen(pl);
		
		//prepare data
		FileWriter fstream_tl = new FileWriter("data_tl.txt");
		BufferedWriter out_tl = new BufferedWriter(fstream_tl);
		out_tl.write(angle);
		out_tl.close();
		
		Parser p = new Parser();
		//read the topleft corner
		p.parse("tl.txt", "data_tl.txt");	
		s.run("data_tl.txt", "TL_Output.txt");
		out_tl.close();
		//calculate using weighted mean
		topleft = Utility.GetCornerPosition("TL_Output.txt");
		

		//Move to top right
		FileWriter fstream_tr = new FileWriter("data_tr.txt");
		BufferedWriter out_tr = new BufferedWriter(fstream_tr);
		out_tr.write(angle);
		out_tr.close();
		
		//Parser p = new Parser();
		p.parse("tr.txt", "data_tr.txt");	
		s.run("data_tr.txt", "TR_Output.txt");
		out_tr.close();
		//calculate using weighted mean
		topright = Utility.GetCornerPosition("TR_Output.txt");
		
		
		//Bottom Left
		FileWriter fstream_bl = new FileWriter("data_bl.txt");
		BufferedWriter out_bl = new BufferedWriter(fstream_bl);
		out_bl.write(angle);
		out_bl.close();
		
		//Parser p = new Parser();
		p.parse("bl.txt", "data_bl.txt");	
		s.run("data_bl.txt", "BL_Output.txt");
		out_bl.close();
		//calculate using weighted mean
		botleft = Utility.GetCornerPosition("BL_Output.txt");

		//Bottom Right
		FileWriter fstream_br = new FileWriter("data_br.txt");
		BufferedWriter out_br = new BufferedWriter(fstream_br);
		out_br.write(angle);
		out_br.close();
		
		//Parser p = new Parser();
		p.parse("br.txt", "data_br.txt");	
		s.run("data_br.txt", "BR_Output.txt");
		out_br.close();
		//calculate using weighted mean
		botright = Utility.GetCornerPosition("BR_Output.txt");

		/*
		System.out.println(topleft.getX() + "~" + topleft.getY());
		System.out.println(topright.getX() + "~" + topright.getY());
		System.out.println(botleft.getX() + "~" + botleft.getY());
		System.out.println(botright.getX() + "~" + botright.getY());
		*/
		//Create a Simulation to find the position
		SimulationQuarterScreen ts = new SimulationQuarterScreen(topleft, topright, botleft, botright, pl);
		
		//prepare data for vnexpress
		FileWriter fstream = new FileWriter("data_vnexpress.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(angle);
		out.close();
		
		//Parser p = new Parser();
		p.parse("vnexpress.txt", "data_vnexpress.txt");	
		ts.run("data_vnexpress.txt", "Output_vnexpress.txt");

		//get quarter screen output
		ts.getPosition("Output_vnexpress.txt", "Position4_vnexpress.txt");
		
		//get 8 screen output
		Simulation8Screens s8 = new Simulation8Screens(topleft, topright, botleft, botright, pl);
		s8.getPosition("Output_vnexpress.txt", "Position8_vnexpress.txt");
		
		Utility.ScreenCounter("Position4_vnexpress.txt", "Count4_vn.txt");
		Utility.ScreenCounter("Position8_vnexpress.txt", "Count8_vn.txt");
		
		fstream = new FileWriter("data_tuoitre.txt");
		out = new BufferedWriter(fstream);
		out.write(angle);
		out.close();
		
		p.parse("tuoitre.txt", "data_tuoitre.txt");
		ts.run("data_tuoitre.txt", "Output_tuoitre.txt");
		ts.getPosition("Output_tuoitre.txt", "Position4_tuoitre.txt");
		s8.getPosition("Output_tuoitre.txt", "Position8_tuoitre.txt");
		
		Utility.ScreenCounter("Position4_tuoitre.txt", "Count4_tr.txt");
		Utility.ScreenCounter("Position8_tuoitre.txt", "Count8_tr.txt");
		/*
		String program = "C:\\Windows\\system32\\notepad.exe";
		File dir = new File(".");
		String file = dir.getCanonicalPath()+ "\\Position4.txt";
		
		Runtime load = Runtime.getRuntime();
		//load.exec(program + " " + file);
		
		file = dir.getCanonicalPath() + "\\Position8.txt";
		//load.exec(program + " " + file);
		
		file = dir.getCanonicalPath() + "\\Count8.txt"; 
		load.exec(program + " " + file);
		
		file = dir.getCanonicalPath() + "\\Count4.txt";
		load.exec(program + " " + file );
		/*
		 * 
		 */
		
		Point3D r[] = Utility.RefineScreenCornerPosition(topleft, topright, botleft, botright, 0);
		
		topleft = r[0];
		topright = r[1];
		botleft = r[2]; 
		botright = r[3];
		
		System.out.println(topleft.getX() + "~" + topleft.getY());
		System.out.println(topright.getX() + "~" + topright.getY());
		System.out.println(botleft.getX() + "~" + botleft.getY());
		System.out.println(botright.getX() + "~" + botright.getY());
		
		FileWriter fstream_cor = new FileWriter("Corner.txt");
		BufferedWriter out_cor = new BufferedWriter(fstream_cor);
		out_cor.write(topleft.getX() + "~" + topleft.getY() + "\r\n");
		out_cor.write(topright.getX() + "~" + topright.getY() + "\r\n");
		out_cor.write(botleft.getX() + "~" + botleft.getY() + "\r\n");
		out_cor.write(botright.getX() + "~" + botright.getY());
		out_cor.close();
	}
	
	public static void run_two() throws IOException {
		File f = new File(".");
		Plane pl = new Plane(0, 0, 1, -0.17);
		
		int angle = 30;
		
		//for vnexpress
		SimulationVnExpressStretch svn = new SimulationVnExpressStretch(pl);
		svn.CalculateCorners(f, angle);
		
		FileWriter fstream = new FileWriter("data_vnexpress.txt");
		BufferedWriter out = new BufferedWriter(fstream);

		out.write(angle);
		out.close();
		
		Parser p = new Parser();
		p.parse("vnexpress.txt", "data_vnexpress.txt");	
		svn.run("data_vnexpress.txt", f.getCanonicalPath() + "\\Output\\Output_vnexpress.txt");

		//get screen output
		svn.getPosition(f.getCanonicalPath() + "\\Output\\Output_vnexpress.txt", 
				f.getCanonicalPath() + "\\Output\\Position16_vnexpress.txt");

		Utility.ScreenCounter(f.getCanonicalPath() + "\\Output\\Position16_vnexpress.txt", 
				f.getCanonicalPath() + "\\Output\\Count16_vn.txt");
		
		//for tuoitre
		SimulationTuoitreStretch str = new SimulationTuoitreStretch(pl);
		str.CalculateCorners(f, angle);
		
		fstream = new FileWriter("data_tuoitre.txt");
		out = new BufferedWriter(fstream);
		
		out.write(angle);
		out.close();
		
		p.parse("tuoitre.txt", "data_tuoitre.txt");
		str.run("data_tuoitre.txt", 
				f.getCanonicalPath() + "\\Output\\Output_tuoitre.txt");
		
		str.getPosition(f.getCanonicalPath() + "\\Output\\Output_tuoitre.txt", 
				f.getCanonicalPath() + "\\Output\\Position16_tuoitre.txt");
		
		Utility.ScreenCounter(f.getCanonicalPath() + "\\Output\\Position16_tuoitre.txt", 
				f.getCanonicalPath() + "\\Output\\Count16_tr.txt");
	}
	
	public static void run_three() throws IOException {
		File dataFolder = new File(".\\data");
		File[] folders = dataFolder.listFiles();
		
		File f;
		for (int i = 0; i < folders.length; ++i) {
			f = folders[i];
			
			FileInputStream iStream = new FileInputStream(f.getCanonicalFile() + "\\setup.txt");
			DataInputStream in = new DataInputStream(iStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String[] temp = reader.readLine().split("~");
			
			Plane pl = new Plane(0, 0, 1, Double.parseDouble(temp[1])*(-1));
			
			temp = reader.readLine().split("~");
			int angle = Integer.parseInt(temp[1]);
			
			//for vnexpress
			SimulationVnExpressStretch svn = new SimulationVnExpressStretch(pl);
			svn.CalculateCorners(f, angle);
			
			FileWriter fstream = new FileWriter(f.getCanonicalFile()+"\\data_vnexpress.txt");
			BufferedWriter out = new BufferedWriter(fstream);

			out.write(angle);
			out.close();
			
			Parser p = new Parser();
			p.parse(f.getCanonicalPath()+"\\vnexpress.txt", f.getCanonicalFile()+"\\data_vnexpress.txt");	
			svn.run(f.getCanonicalFile()+"\\data_vnexpress.txt", f.getCanonicalPath() + "\\Output\\Output_vnexpress.txt");

			//get screen output
			svn.getPosition(f.getCanonicalPath() + "\\Output\\Output_vnexpress.txt", 
					f.getCanonicalPath() + "\\Output\\Position16_vnexpress.txt");

			Utility.ScreenCounter(f.getCanonicalPath() + "\\Output\\Position16_vnexpress.txt", 
					f.getCanonicalPath() + "\\Output\\Count16_vn.txt");
			
			//for tuoitre
			SimulationTuoitreStretch str = new SimulationTuoitreStretch(pl);
			str.CalculateCorners(f, angle);
			
			fstream = new FileWriter(f.getCanonicalFile()+"\\data_tuoitre.txt");
			out = new BufferedWriter(fstream);
			
			out.write(angle);
			out.close();
			
			p.parse(f.getCanonicalPath()+"\\tuoitre.txt", f.getCanonicalFile()+"\\data_tuoitre.txt");
			str.run(f.getCanonicalFile()+"\\data_tuoitre.txt", 
					f.getCanonicalPath() + "\\Output\\Output_tuoitre.txt");
			
			str.getPosition(f.getCanonicalPath() + "\\Output\\Output_tuoitre.txt", 
					f.getCanonicalPath() + "\\Output\\Position16_tuoitre.txt");
			
			Utility.ScreenCounter(f.getCanonicalPath() + "\\Output\\Position16_tuoitre.txt", 
					f.getCanonicalPath() + "\\Output\\Count16_tr.txt");
			
			//delete unnecessary files
			File tempF = new File(f.getCanonicalFile()+"\\data_tuoitre.txt");
			tempF.delete();
			tempF = new File(f.getCanonicalFile()+"\\data_vnexpress.txt");
			tempF.delete();
		}		
	}
}
