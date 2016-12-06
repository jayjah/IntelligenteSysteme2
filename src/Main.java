import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = null;
		int xdim = 0;
		int ydim = 0;
		try {
			scanner=new Scanner(new FileReader(Paths.get("","src","resources","data0.csv").toAbsolutePath().toString()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (scanner.hasNextLine()) {
		ydim++;
			String line = scanner.nextLine();
			String[] array = line.split(",");
			xdim=array.length;
			for (String a : array) {
				
			}
		}
		scanner.close();
		try {
			scanner=new Scanner(new FileReader(Paths.get("","src","resources","data0.csv").toAbsolutePath().toString()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float[][] data = new float[xdim + 2][ydim + 2];
		int cy=0;
		float min=100000.0f;
		float max=0.0f;
		
		while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				String[] array = line.split(",");
				int cx=0;
				for (String a : array) {
					data[cx][cy]=(Float.parseFloat(a));
					if(data[cx][cy]>max)
						max=data[cx][cy];
					if(data[cx][cy]<min)
						min=data[cx][cy];
					
					cx++;
				}
				cy++;
			}
		
		Visualisator vis = new Visualisator(data,xdim,ydim,min,max);
		Thread t1 = new Thread(vis);
		t1.start();

	}

} // end of class Hello3d
