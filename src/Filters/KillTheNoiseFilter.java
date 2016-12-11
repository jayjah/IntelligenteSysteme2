package Filters;

import Main.Data;

/**
* "Kill the noise" filter
* 
* <P>This filter kills the noise in the given data object and leaves the hills by setting every value below the average height of the current line to zero, modified by a treshold value
* 
* @author Kim Oliver Schweikert, Markus Krebs
* @version 1.0
*/
public class KillTheNoiseFilter extends Filter {
	double treshold=30.11;
	
	/**
	 * Constructor
	 */
	public KillTheNoiseFilter(){
		super();
	}
	
	/**
	 * Constructor
	 * @param treshold	treshold value for noise killing
	 */
	public KillTheNoiseFilter(double treshold){
		super();
		this.treshold=treshold;
	}
	
	/**
	 * Applies the filter to the data object (baked!)
	 * Kills noise in the data object by comparing the average row height and a treshold value to the current element height of each row, sets everything below to 0.0f and keeps the hilltops
	 * @param dd	Data to apply the filter to
	 */
	public void work(Data dd){
		super.work(dd);
				float[][] data=dd.getData();
				for(int x=0;x<dd.getXdim();x++){
					double average=super.getAverageHeightOfRow(dd, x);
					for(int y=0;y<dd.getYdim();y++)
						data[x][y]=(data[x][y]<=(float)average+(average/treshold))?(float)0.0f:dd.getData()[x][y];	
				}
	}
}

