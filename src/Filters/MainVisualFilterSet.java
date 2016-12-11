package Filters;
import java.util.ArrayList;

import javax.vecmath.Point2d;

import Main.Data;


public class MainVisualFilterSet extends Filter {
	
	public MainVisualFilterSet(){
		super();
	}
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