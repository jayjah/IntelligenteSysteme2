package Main;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Scanner;

import javax.vecmath.Point2d;

/**
* Data class
* 
* <P>Contains all data to work with and apply filters to.
* 
* @author Kim Oliver Schweikert, Markus Krebs
* @version 1.0
*/
public class Data {

	private float[][] data;
	
	private int xdim = 0;
	private int ydim = 0;
	private ArrayList<Point2d> labels=new ArrayList<Point2d>();
	private float min=1000000.0f;
	private float max=0.0f;
	
	/**
	 * 
	 * @return get the X dimension of the data array
	 */
	public int getXdim() {
		return xdim;
	}

	/**
	 * 
	 * @param set the X dimension of the data array
	 */
	public void setXdim(int xdim) {
		this.xdim = xdim;
	}

	/**
	 * 
	 * @return get the Y dimension of the data array
	 */
	public int getYdim() {
		return ydim;
	}

	/**
	 * 
	 * @param set the Y dimension of the data array
	 */
	public void setYdim(int ydim) {
		this.ydim = ydim;
	}

	/**
	 * 
	 * @return get the data array
	 */
	public float[][] getData() {
		return data;
	}

	/**
	 * 
	 * @param set the X dimension of the data array
	 */
	public void setData(float[][] data) {
		this.data = data;
	}

	/**
	 * 
	 * @return get the minimum value in the data array
	 */
	public float getMin() {
		return min;
	}

	/**
	 * 
	 * @param set the minimum value in the data array
	 */
	public void setMin(float min) {
		this.min = min;
	}

	/**
	 * 
	 * @return get the labels of found maxima in the data array
	 */
	public ArrayList<Point2d> getLabels() {
		return labels;
	}

	/**
	 * 
	 * @param set the labels of found maxima in the data array
	 */
	public void setLabels(ArrayList<Point2d> labels) {
		this.labels = labels;
	}

	/**
	 * 
	 * @return get the maximum value in the data array
	 */
	public float getMax() {
		return max;
	}

	/**
	 * 
	 * @param set the maximum value in the data array
	 */
	public void setMax(float max) {
		this.max = max;
	}

	/**
	 * Constructor
	 * 
	 * @param file_data		Path of the csv containing the values
	 * @param file_label	Path of the csv containing the reference labels
	 */
	public Data(String file_data, String file_label) {
		readFromFile(file_data, file_label);
	}
	
	/**
	 * Read data and labels from file
	 * 
	 * @param file_data		Path of the csv containing the values
	 * @param file_label	Path of the csv containing the reference labels
	 */
	private void readFromFile(String file_data, String file_label) {
		Scanner scanner = null;
		Scanner scanner_label = null;
		
		try {
			scanner=new Scanner(new FileReader(file_data));
			scanner_label = new Scanner(new FileReader(file_label));
		} catch (FileNotFoundException e) {
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
