public class PVector {
	private double xComp, yComp;
	
	public double getxComp() {
		return xComp;
	}
	public double getyComp() {
		return yComp;
	}
	public void setxComp(double xComp) {
		this.xComp = xComp;
	}
	public void setyComp(double yComp) {
		this.yComp = yComp;
	}

	public PVector (double xComp, double yComp) {
		this.xComp = xComp;
		this.yComp = yComp;
	}
	
	public PVector add (PVector vector) {
		xComp += vector.getxComp();
		yComp += vector.getyComp();
		return this;
	}
	
	public static PVector add (PVector v1, PVector v2) {
		return new PVector(v1.getxComp() + v2.getxComp(), v1.getyComp() + v2.getyComp());
	}
	
	public void print () {
		System.out.println("(" + xComp + ", " + yComp + ")");
	}
	
	public static double distance (double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	public static double distance (PVector v1, PVector v2) {
		return distance(v1.getxComp(), v2.getxComp(), v1.getyComp(), v2.getyComp());
	}
}