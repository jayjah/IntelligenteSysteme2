package Filters;

import java.util.ArrayList;

import javax.vecmath.Point2d;

import Main.Data;

/**
* Main filter
* 
* <P>This filter holds and executes all set filters, then creates labels from the remaining points.
* 
* @author Kim Oliver Schweikert, Markus Krebs
* @version 1.0
*/
public class MainFilterSet extends Filter {
	
	/**
	 * Constructor
	 */
	public MainFilterSet(){
		super();
	}
	
	/**
	 * Applies all filters in the filter list and sets all found labels in the data object.
	 * @param dd	Data object to work on
	 */
	public void work(Data dd){
		for(Filter f:this.getFilters())
			f.work(dd);

		ArrayList<Point2d> newLabels=new ArrayList<Point2d>();
		for(int y=0;y<dd.getYdim();y++)
			for(int x=0;x<dd.getXdim();x++)
				if(dd.getData()[x][y]!=0.0f)
					newLabels.add(new Point2d(x,y));
		dd.setLabels(newLabels);
	}
}