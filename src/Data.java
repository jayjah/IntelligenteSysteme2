import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Scanner;

import javax.vecmath.Point2d;


public class Data {

	private float[][] data;
	
	private int xdim = 0;
	private int ydim = 0;
	
	public int getXdim() {
		return xdim;
	}

	public void setXdim(int xdim) {
		this.xdim = xdim;
	}

	public int getYdim() {
		return ydim;
	}

	public void setYdim(int ydim) {
		this.ydim = ydim;
	}

	public float[][] getData() {
		return data;
	}

	public void setData(float[][] data) {
		this.data = data;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public ArrayList<Point2d> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<Point2d> labels) {
		this.labels = labels;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	
	private ArrayList<Point2d> labels=new ArrayList<Point2d>();
	private float min=100000.0f;
	private float max=0.0f;
	
	
	public Data(float[][] data, ArrayList<Point2d> labels, float min, float max) {
		this.data = data;
		this.labels = labels;
		this.min = min;
		this.max = max;
	}
	
	public Data(String file_data, String file_label) {
		readFromFile(file_data, file_label);
	}
	
	private void readFromFile(String file_data, String file_label) {
		Scanner scanner = null;
		Scanner scanner_label = null;
		
		try {
			scanner=new Scanner(new FileReader(file_data));
			scanner_label = new Scanner(new FileReader(file_label));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//read once to get the dimensions
		while (scanner.hasNextLine()) {
		this.ydim++;
			String line = scanner.nextLine();
			String[] array = line.split(",");
			this.xdim=array.length;

		}
		scanner.close();
		
		try {
			scanner=new Scanner(new FileReader(file_data));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Size the data array in the read dimensions (+2 because we generate a quad for 4 values later while stepping 2)
		this.data = new float[this.xdim + 2][this.ydim + 2];
		
		//Read second time into the initialized array - while doing that, also find the min and max value
		int cy=0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] array = line.split(",");
			int cx=0;
			for (String a : array) {
				this.data[cx][cy]=(Float.parseFloat(a));
				if(this.data[cx][cy]>max)
					this.max=data[cx][cy];
				if(this.data[cx][cy]<min)
					this.min=data[cx][cy];
				cx++;
			}
			cy++;
		}
		scanner.close();
		
		//clear the labels list and read the labels into one Point2d each. Then add them to the list
		this.labels.clear();
		while (scanner_label.hasNextLine()) {
			String line=scanner_label.nextLine();
			int x=(int)Float.parseFloat(line.split(",")[0]);
			int y=(int)Float.parseFloat(line.split(",")[1]);
			this.labels.add(new Point2d(x,y));
		}
		scanner_label.close();
		
	}
	
}
