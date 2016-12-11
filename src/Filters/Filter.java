package Filters;



import java.util.ArrayList;

import Main.Data;


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
	
	
	
protected double getAverageHeightOfRow(Data d, int row){
		
		double retval=0.0;
		for(int i=0;i<d.getYdim();i++)
			retval+=d.getData()[row][i];
		retval=retval/d.getYdim();
		
		return retval;
		
		
	}
public Integer getAverageHillLengthOfRowFromTo(Data d, int rowFrom, int rowTo){
	//Reihen iterieren
	float[] rowsSumVal=new float[(rowTo-rowFrom)+1];
	
	for(int i=0;i<rowsSumVal.length;i++){
		rowsSumVal[i]=0;
	}
	
	for(int row=rowFrom;row<rowTo;row++){
		//Gegebene Reihe iterieren
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
