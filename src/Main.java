import java.nio.file.Paths;


public class Main {

	
	
	public static void main(String[] args) {
		Data d=new Data(Paths.get("","src","resources","data2.csv").toAbsolutePath().toString(),Paths.get("","src","resources","label2.csv").toAbsolutePath().toString());
		
		Filter testfilter=new TestFilter();
		
		d=testfilter.work(d);
		
		Visualisator vis = new Visualisator(d.getData(),d.getLabels(),d.getXdim(),d.getYdim(),d.getMin(),d.getMax());
		for(int i=0;i<d.getYdim();i++)
			for(int j=0;j<d.getXdim();j++){
				//System.out.println(d.getData()[j][i]);
			}
		
		Thread t1 = new Thread(vis);
		t1.start();
		
	}

} // end of class Hello3d
