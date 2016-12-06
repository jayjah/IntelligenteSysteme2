import java.nio.file.Paths;


public class Main {

	public static void main(String[] args) {
		Data d=new Data(Paths.get("","src","resources","data0.csv").toAbsolutePath().toString(),Paths.get("","src","resources","label0.csv").toAbsolutePath().toString());
		
		Visualisator vis = new Visualisator(d.getData(),d.getXdim(),d.getYdim(),d.getMin(),d.getMax());
		Thread t1 = new Thread(vis);
		t1.start();

	}

} // end of class Hello3d
