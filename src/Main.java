public class Main {

	public static void main(String[] args) {
		int xdim = 800;
		int ydim = 1000;
		float[][] data = new float[xdim + 2][ydim + 2];
		Visualisator vis = new Visualisator(data,xdim,ydim);
		Thread t1 = new Thread(vis);
		t1.start();

	}

} // end of class Hello3d
