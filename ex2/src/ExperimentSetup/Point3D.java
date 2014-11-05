/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExperimentSetup;

/**
 *
 * @author hieurl
 */
public class Point3D {

    private double x;
    private double y;
    private double z;

    public Point3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
