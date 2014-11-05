/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExperimentSetup;

/**
 *
 * @author hieurl
 */
public class Plane {

    private double a;
    private double b;
    private double c;
    private double d;

    public Plane() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
    }

    public Plane(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void seta(double a) {
        this.a = a;
    }

    public void setb(double b) {
        this.b = b;
    }

    public void setc(double c) {
        this.c = c;
    }

    public void setd(double d) {
        this.d = d;
    }

    public double geta() {
        return this.a;
    }

    public double getb() {
        return this.b;
    }

    public double getc() {
        return this.c;
    }

    public double getd() {
        return this.d;
    }
}
