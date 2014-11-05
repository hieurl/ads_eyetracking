/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExperimentSetup;

/**
 *
 * @author hieurl
 */
public class FLLineEquation {

    private Point3D p;
    private UnitVector u;

    public FLLineEquation() {
        // TODO Auto-generated constructor stub
        p = new Point3D();
        u = new UnitVector();
    }

    public FLLineEquation(UnitVector u, Point3D p) {
        this.u = u;
        this.p = p;
    }

    public Point3D getp() {
        return this.p;
    }

    public UnitVector getUnitVector() {
        return this.u;
    }

    public void sett(Point3D p) {
        this.p = p;
    }

    public void setUnitVector(UnitVector u) {
        this.u = u;
    }
}
