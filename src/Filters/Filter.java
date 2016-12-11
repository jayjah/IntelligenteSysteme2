package Filters;

import java.util.ArrayList;

import Main.Data;

/**
* Basic Filter class.
* 
* <P>Provides basic functionality for a filter that can be applied to a data object. Superclass for custom filters.
*  
* @author Kim Oliver Schweikert, Markus Krebs
* @version 1.0
*/
public class Filter {
	private ArrayList<Filter> filters;
	
	/**
	 * Calculates the average height of the given line
	 *
	 * @param  d 	the data object to search in
	 * @param  line the line to get the average height from
	 * @return      the average height of the line
	 */
	protected double getAverageHeightOfLine(Data d, int line){
		double retval=0.0;
		for(int i=0;i<d.getXdim();i++)
			retval+=d.getData()[i][line];
		retval=retval/d.getXdim();
		return retval;
	}
	
	/**
	 * Calculates the average height of the given row
	 *
	 * @param  d 	the data object to search in
	 * @param  row the row to get the average height from
	 * @return      the average height of the row
	 */
	protected double getAverageHeightOfRow(Data d, int row){
		double retval=0.0;
		for(int i=0;i<d.getYdim();i++)
			retval+=d.getData()[row][i];
		retval=retval/d.getYdim();
		return retval;
	}
	
	/**
	 * Calculates the maximum height of the given line
	 * @param  d 	the data object to search in
	 * @param  line the line to get the maximum height from
	 * @return      the maximum height of the line
	 */
	protected ArrayList<Float> getHillsMaximumHeightofLine(Data d, int line){
		ArrayList<Float> values=new ArrayList<Float>();
		boolean nexthill=false;
		for(int i=0;i<d.getXdim();i++){
			if(d.getData()[i][line]>0.0f){
				if(!nexthill){
					nexthill=true;
					values.add(d.getData()[i][line]);
				}
				if(d.getData()[i][line]>values.get(values.size()-1))
					values.set(values.size()-1,d.getData()[i][line]);
			}
			else{
				nexthill=false;
			}
		}
		return values;
	}
	
	/**
	 * Calculates the maximum height of the given row
	 *
	 * @param  d 	the data object to search in
	 * @param  row the row to get the maximum height from
	 * @return      the maximum height of the row
	 */
	protected ArrayList<Float> getHillsMaximumHeightofRow(Data d, int row){
		ArrayList<Float> values=new ArrayList<Float>();
		boolean nexthill=false;
		for(int i=0;i<d.getYdim();i++){
			if(d.getData()[row][i]>0.0f){
				if(!nexthill){
					nexthill=true;
					values.add(d.getData()[row][i]);
				}
				if(d.getData()[row][i]>values.get(values.size()-1))
					values.set(values.size()-1,d.getData()[row][i]);
			}
			else{
				nexthill=false;
			}
		}
		return values;
	}

	/**
	 * Calculates the average length of hills within a range of rows
	 *
	 * @param  d 		the data object to search in
	 * @param  rowFrom 	the first row of the range
	 * @param  rowFrom 	the last row of the range
	 * @return      	average hill length within row range
	 */
	public Integer getAverageHillLengthOfRowFromTo(Data d, int rowFrom, int rowTo){
		float[] rowsSumVal=new float[(rowTo-rowFrom)+1];
		
		for(int i=0;i<rowsSumVal.length;i++){
			rowsSumVal[i]=0;
		}
		for(int row=rowFrom;row<rowTo;row++){
			int hillsfound=0;
			boolean nexthill=false;
			for(int i=0;i<d.getYdim();i++){
				if(row<d.getYdim()){
					if(d.getData()[row][i]!=0.0f){
						rowsSumVal[row-rowFrom]++;
						if(!nexthill){
							hillsfound++;
							nexthill=true;
						}
					}
				}
				else
					nexthill=false;
			}
			rowsSumVal[row-rowFrom]=hillsfound>0?rowsSumVal[row-rowFrom]/hillsfound:0.0f;
		}
		
		int retlength=0;
		for(int i=0;i<rowsSumVal.length;i++){
			retlength+=rowsSumVal[i];
		}
		if(rowsSumVal.length>0)
			return (int)(retlength/rowsSumVal.length);
		else return 0;
	}
	
	/**
	 * 
	 * @return get the (sub)filter list of this filter
	 */
	public ArrayList<Filter> getFilters() {
		return filters;
	}
	
	/**
	 * 
	 * @param set the (sub)filter list of this filter
	 */
	public void setFilters(ArrayList<Filter> filters) {
		this.filters = filters;
	}
	/**
	 * Constructor
	 * 
	 * @param set the (sub)filter list of this filter
	 */
	public Filter(){
		this.filters=new ArrayList<Filter>();
		
	}
	
	/**
	 * 
	 * @param f add a filter to the (sub)filter list of this filter
	 */
	public void addFilter(Filter f){
		this.filters.add(f);
	}
	
	/**
	 * Applies the filter (and all containing sub filters) to the given data object (baked!)
	 * 
	 * @param data object to work on
	 */
	public void work(Data input){
		//Filter Data by all Filters in the filterlist
		for(Filter f:this.filters)
			f.work(input);
	}
}
