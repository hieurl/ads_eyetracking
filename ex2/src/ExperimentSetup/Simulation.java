/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExperimentSetup;

/**
 *
 * @author hieurl
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Simulation {

    Point3D topleft;
    Point3D topright;
    Point3D botleft;
    Point3D botright;
    Plane pl;
    double screen_rotation_angle;

    public Simulation() {
        topleft = new Point3D();
        topright = new Point3D();
        botleft = new Point3D();
        botright = new Point3D();

        pl = new Plane();

        screen_rotation_angle = 0;
    }

    public Simulation(Point3D tl, Point3D tr, Point3D bl, Point3D br, Plane plane) {
        pl = plane;

        screen_rotation_angle = Utility.GetCornerRotationCorner(tl, tr);
        Point3D r[] = Utility.RefineScreenCornerPosition(tl, tr, bl, br, 0);

        topleft = r[0];
        topright = r[1];
        botleft = r[2];
        botright = r[3];

    }

    public Simulation(Plane plane) {
        pl = plane;

        topleft = new Point3D();
        topright = new Point3D();
        botleft = new Point3D();
        botright = new Point3D();
    }

    public void SetCorner(Point3D tl, Point3D tr, Point3D bl, Point3D br) {
        this.topleft = tl;
        this.topright = tr;
        this.botleft = bl;
        this.botright = br;
    }

    public void SetPlane(Plane pl) {
        this.pl = pl;
    }

    public void CalculateCorners(File dir, int angle) throws IOException {
        //Check if output folder is created
        File oDir = new File(dir.getCanonicalPath() + "\\Output");
        if (!oDir.exists()) {
            //oDir.mkdir();
        }
        //Get top left
        String in_file = dir.getCanonicalPath() + "\\tl.txt";
        String data_file = dir.getCanonicalPath() + "\\data_tl.txt";
        String out_file = dir.getCanonicalPath() + "\\TL_Output.txt";

        FileWriter fstream = new FileWriter(data_file);
        BufferedWriter out = new BufferedWriter(fstream);

        out.write(Integer.toString(angle) + "\r\n");
        out.close();

        Parser p = new Parser();
        p.parse(in_file, data_file);

        this.run(data_file, out_file);

        this.topleft = Utility.GetCornerPosition(out_file);

        //Delete unnecessary files
        File tempF = new File(data_file);
        tempF.delete();
        tempF = new File(out_file);
        tempF.delete();

        //Get top right
        in_file = dir.getCanonicalPath() + "\\tr.txt";
        data_file = dir.getCanonicalPath() + "\\data_tr.txt";
        out_file = dir.getCanonicalPath() + "\\TR_Output.txt";

        fstream = new FileWriter(data_file);
        out = new BufferedWriter(fstream);

        out.write(Integer.toString(angle) + "\r\n");
        out.close();

        p.parse(in_file, data_file);

        this.run(data_file, out_file);

        this.topright = Utility.GetCornerPosition(out_file);

        //delete unnecessary files
        tempF = new File(data_file);
        tempF.delete();
        tempF = new File(out_file);
        tempF.delete();

        //Get bot left
        in_file = dir.getCanonicalPath() + "\\bl.txt";
        data_file = dir.getCanonicalPath() + "\\data_bl.txt";
        out_file = dir.getCanonicalPath() + "\\BL_Output.txt";

        fstream = new FileWriter(data_file);
        out = new BufferedWriter(fstream);

        out.write(Integer.toString(angle) + "\r\n");
        out.close();

        p.parse(in_file, data_file);

        this.run(data_file, out_file);

        this.botleft = Utility.GetCornerPosition(out_file);

        //delete unnecessary files
        tempF = new File(data_file);
        tempF.delete();
        tempF = new File(out_file);
        tempF.delete();

        //Get bot right
        in_file = dir.getCanonicalPath() + "\\br.txt";
        data_file = dir.getCanonicalPath() + "\\data_br.txt";
        out_file = dir.getCanonicalPath() + "\\BR_Output.txt";

        fstream = new FileWriter(data_file);
        out = new BufferedWriter(fstream);

        out.write(Integer.toString(angle) + "\r\n");
        out.close();

        p.parse(in_file, data_file);

        this.run(data_file, out_file);

        this.botright = Utility.GetCornerPosition(out_file);

        //delete unnecessary files
        tempF = new File(data_file);
        tempF.delete();
        tempF = new File(out_file);
        tempF.delete();

        //refine!
        topleft.setX((topleft.getX() + botleft.getX()) / 2);
        topleft.setY((topleft.getY() + topright.getY()) / 2);

        topright.setX((topright.getX() + botright.getX()) / 2);
        topright.setY(topleft.getY());

        botleft.setX(topleft.getX());
        botleft.setY((botleft.getY() + botright.getY()) / 2);

        botright.setX(topright.getX());
        botright.setY(botleft.getY());

        String corner_out = dir.getCanonicalPath() + "\\Corner.txt";
        fstream = new FileWriter(corner_out);
        out = new BufferedWriter(fstream);

        out.write(topleft.getX() + "~" + topleft.getY() + "\r\n");
        out.write(topright.getX() + "~" + topright.getY() + "\r\n");
        out.write(botleft.getX() + "~" + botleft.getY() + "\r\n");
        out.write(botright.getX() + "~" + botright.getY());
        out.close();
    }

    public void writeCorner(String path) throws IOException {
        FileWriter fstream_cor = new FileWriter(path);
        BufferedWriter out_cor = new BufferedWriter(fstream_cor);
        out_cor.write(topleft.getX() + "~" + topleft.getY() + "\r\n");
        out_cor.write(topright.getX() + "~" + topright.getY() + "\r\n");
        out_cor.write(botleft.getX() + "~" + botleft.getY() + "\r\n");
        out_cor.write(botright.getX() + "~" + botright.getY());
        out_cor.close();
    }

    public void run(String datafile, String pOutput) {
        try {
            FileInputStream iStream = new FileInputStream(datafile);

            DataInputStream in = new DataInputStream(iStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line_in;
            String[] values;

            double pitch_l, yaw_l, eye_x_l, eye_y_l, eye_z_l;
            double pitch_r, yaw_r, eye_x_r, eye_y_r, eye_z_r;

            UnitVector unitvector = new UnitVector();

            String line_out;

            FileWriter fstream = new FileWriter(pOutput);
            BufferedWriter out = new BufferedWriter(fstream);

            //FileWriter unitVectorStream = new FileWriter("unitvector_ouput.txt");
            //BufferedWriter uOut = new BufferedWriter(unitVectorStream);

            //skip first line for alpha;
            //read the next line to get values and calculate
            int counter = 0;
            while ((line_in = reader.readLine()) != null) {
                //print line in
                //System.out.println("in: "+line_in);
                values = line_in.split("~");

                if (values.length < 11) {
                    continue;
                }

                //frame = Integer.parseInt(values[0]);

                //get the right eye
                eye_x_r = Double.parseDouble(values[1]);

                //double d[] = Utility.Rotate(Double.parseDouble(values[2]), Double.parseDouble(values[3]), -alpha);

                eye_y_r = Double.parseDouble(values[2]);
                eye_z_r = Double.parseDouble(values[3]);

                //eye_y_r = d[0];
                //eye_z_r = d[1];

                pitch_r = Double.parseDouble(values[4]);
                yaw_r = Double.parseDouble(values[5]);

                //get the left eye
                eye_x_l = Double.parseDouble(values[6]);

                //d = Utility.Rotate(Double.parseDouble(values[7]), Double.parseDouble(values[8]), -alpha);

                eye_y_l = Double.parseDouble(values[7]);
                eye_z_l = Double.parseDouble(values[8]);

                pitch_l = Double.parseDouble(values[9]);
                yaw_l = Double.parseDouble(values[10]);

                //eye_y_l = d[0];
                //eye_z_l = d[1];


                /* get intersection point of right eye */

                unitvector = Utility.GetUnitVector(pitch_r, yaw_r);
                Point3D np = new Point3D(eye_x_r, eye_y_r, -eye_z_r);
                unitvector = Utility.ShiftUnitVector(unitvector, np);
                FLLineEquation equation = Utility.GetEquation(unitvector, np);

                //calculate intersection point
                Point3D p_right = Utility.CalculateIntersectionPoint(equation, pl);

                /* Get the intersection point for the left eye*/

                unitvector = Utility.GetUnitVector(pitch_l, yaw_l);
                //System.out.println(unitvector.getX() + "~" + unitvector.getY() + "~" + unitvector.getZ());
                Point3D np2 = new Point3D(eye_x_l, eye_y_l, -eye_z_l);
                unitvector = Utility.ShiftUnitVector(unitvector, np2);

                FLLineEquation equation2 = Utility.GetEquation(unitvector, np2);

                Point3D p_left = Utility.CalculateIntersectionPoint(equation2, pl);

                String intersec = (p_left.getX() + p_right.getX()) / 2 + "~" + (p_left.getY() + p_right.getY()) / 2;
                //System.out.println("IntersectionPoint: " + p.getX() + ", " + p.getY() + ", " + p.getZ());

                //write 
                counter++;

                line_out = /*Integer.toString(counter) + "	" +*/ intersec /*+ "				" +getPosition(p) /*+ */ + "\r\n";
                out.write(line_out);
                //System.out.print(line_out);
                //System.out.println();
            }

            out.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    public void getPosition(String infile, String outfile) {
        
    }
}
