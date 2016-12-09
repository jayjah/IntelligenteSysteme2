import java.nio.file.Paths;


public class Main {

	
	
	public static void main(String[] args) {
		Data d=new Data(Paths.get("","src","resources","data2.csv").toAbsolutePath().toString(),Paths.get("","src","resources","label2.csv").toAbsolutePath().toString());
		
		Visualisator orig_vis = new Visualisator(d.getData(),d.getLabels(),d.getXdim(),d.getYdim(),d.getMin(),d.getMax());
		Thread t2 = new Thread(orig_vis);
		
		Filter testfilter=new TestFilter();
		
		d=testfilter.work(d);
		
		Visualisator vis = new Visualisator(d.getData(),d.getLabels(),d.getXdim(),d.getYdim(),d.getMin(),d.getMax());
		Thread t1 = new Thread(vis);
		
		
		t1.start();
		t2.start();
		
	}

} // end of class Hello3d
