import java.util.ArrayList;

import javax.vecmath.Point2d;


//Irgendwie so hab ich mir das gedacht.
//Von dieser Klasse verschiedene Filter ableiten. Jeder Filter kann Filter enthalten.


public class Filter {
	private ArrayList<Filter> filters;
	
	protected double getAverageHeightOfLine(Data d, int line){
		
		double retval=0.0;
		for(int i=0;i<d.getXdim();i++)
			retval+=d.getData()[i][line];
		retval=retval/d.getXdim();
		
		return retval;
		
		
	}
	
protected double getAverageHeightOfRow(Data d, int row){
		
		double retval=0.0;
		for(int i=0;i<d.getYdim();i++)
			retval+=d.getData()[row][i];
		retval=retval/d.getYdim();
		
		return retval;
		
		
	}
	
	public ArrayList<Filter> getFilters() {
		return filters;
	}

	public void setFilters(ArrayList<Filter> filters) {
		this.filters = filters;
	}
	
	public Filter(){
		this.filters=new ArrayList<Filter>();
		
	}
	public void addFilter(Filter f){
		this.filters.add(f);
	}
	
	public Data work(Data input){
		Data temp=input;
		
		//Filter Data by all Filters in the filterlist
		for(Filter f:this.filters)
			temp=f.work(temp);
		
		//Now do logic for own filter here:
		
		return temp;
		
	}
}
