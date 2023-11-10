package ICS;

public class Gene {
    double x;
    double y;
    double r;

    public Gene() {
        x = y = r = 1.0;
    }

    public Gene(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Gene(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Gene(double r) {
        this.r = r;
    }

    public void setGene(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double distnce(double x, double y) {
        double d = Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2);
        return Math.sqrt(d);
    }
}
