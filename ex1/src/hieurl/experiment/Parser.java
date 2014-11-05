// 0  : Frame 
// 11 : EYEBALL_R_X     
// 12 : EYEBALL_R_Y     
// 13 : EYEBALL_R_Z    
// 14 : GAZE_ROT_R_X    
// 15 : GAZE_ROT_R_Y
// 17 : EYEBALL_L_X     
// 18 : EYEBALL_L_Y     
// 19 : EYEBALL_L_Z    
// 20 : GAZE_ROT_L_X    
// 21 : GAZE_ROT_L_Y
package hieurl.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

//import sun.io.Converters;

public class Parser {

	/**
	 * @param args
	 */
	
	//Output: EYEBALL_R_X~EYEBALL_R_Y~EYEBALL_R_Z~GAZE_ROT_R_X~GAZE_ROT_R_Y
	public void parse(String input, String output) {
		File file = new File(input);       // Doc file  
        BufferedReader reader = null;
        FileWriter fstream = null;
		try {
			fstream = new FileWriter(output, true); // Xuat file 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        BufferedWriter out = new BufferedWriter(fstream);
        
        int k = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            String word = null;
            
            String line;

            // repeat until all lines is read
            text = reader.readLine();
            while ((text = reader.readLine()) != null) { // Doc 1 dong trong file roi lua lai vo text
          	
            	k = 0;
            	StringTokenizer st = new StringTokenizer(text); // Xoa cac khoang trang trong tung line roi luu lai vo st
            	
            	line = "";
            	boolean write = true;
                while (st.hasMoreTokens()) {// khi nao con column thi doc tiep
                	word = st.nextToken();
                	if ((k == 10 || k == 16) && !(word.equals("3"))) {
                		write = false;
                		k++;
                		continue;
                	}
                	if (k==0 || (k>10 && k<16) || (k>16 && k<22)) {
                	//if (k == 0 || (k>10 && k<16))
                		line = line + word + "~";
                	}
                	else if (k >=22) {
                		break;
                	}
                		//out.append(word + "~"); // xuat ra
                		//System.out.print(word + " "); // Xuat ra console 
                    k++;
                }
                if (write) {
                	out.append(line + "\r\n");
                }
                
            }
            
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
        	reader.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
