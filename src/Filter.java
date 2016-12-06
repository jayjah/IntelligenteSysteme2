import java.util.ArrayList;


//Irgendwie so hab ich mir das gedacht.
//Von dieser Klasse verschiedene Filter ableiten. Jeder Filter kann Filter enthalten.


public class Filter {
	private ArrayList<Filter> filters;

	public ArrayList<Filter> getFilters() {
		return filters;
	}

	public void setFilters(ArrayList<Filter> filters) {
		this.filters = filters;
	}
	
	public Filter(){
		
		
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
