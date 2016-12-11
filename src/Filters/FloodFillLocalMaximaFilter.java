package Filters;




import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.vecmath.Point2d;

import Main.Data;

public class FloodFillLocalMaximaFilter extends Filter {

	public FloodFillLocalMaximaFilter(){
		super();
	}
	
	
	public Data work(Data d){
		Data dd= super.work(d);
		
		Data dataCopy=new Data(dd);
		
		float[][] data=dd.getData();
		//Hier Zeugs mit den Daten machen.
		Set<Point2d> foundMaxima=new HashSet<Point2d>();
		
		Set<Point2d> removeMaxima=new HashSet<Point2d>();
		
		
		for(int y=0;y<dd.getYdim();y++){
			for(int x=0;x<dd.getXdim();x++){
				
				if(dd.getData()[x][y]!=0.0f){
				Point2d maximum=this.floodfindMax(new Point2d(x,y), 0.0f, dd);
				
					if(maximum.x!=-1){
						foundMaxima.add(maximum);
					
						for(Point2d maxi : foundMaxima)
							if((Math.abs(maxi.x-maximum.x)<10 && Math.abs(maxi.y-maximum.y)<10) && maxi!=maximum){
								removeMaxima.add(maximum);
							}
							
								
					}
				
					
				}
				
			}
		}
		
		foundMaxima.removeAll(removeMaxima);
		
		for(Point2d p : foundMaxima){
			
				data[(int) p.x][(int) p.y]=900.0f;
			
		}
				dd.setData(data);
				data=dd.getData();
		
		return  dd;
		
		
	}
	
	
	
	
	
	
	//Altered floodfill algorithm: Doesn't fill but is using floodfill to find the maximum while "filling" within boarders of value borderValue
	private Point2d floodfindMax(Point2d point, float borderValue, Data dd) {
		int polyCount=0;
		Stack<Point2d> pointstack=new Stack<Point2d>();
		float maxval=0.0f;
		Point2d maxPoint=new Point2d(0,0);
		float[][] sdata=dd.getData();
		pointstack.push(point);
			
		   while(!pointstack.empty()) {
		      Point2d p = pointstack.pop();

		      
		      if (dd.getData()[(int) p.x][(int) p.y] != borderValue) {
		    	  if(dd.getData()[(int) p.x][(int) p.y]>maxval){
		    		  maxval=dd.getData()[(int) p.x][(int) p.y];
		    		  maxPoint=p;
		    		  
		    	  }
		    	  polyCount++;
		    	  sdata[(int) p.x][(int) p.y]=0.0f;
		    	  if(p.y<dd.getYdim()-1)
		    		 pointstack.push(new Point2d(p.x, p.y + 1));
		         if(p.y>0)
		        	 pointstack.push(new Point2d(p.x,p. y - 1));
		         if(p.x<dd.getXdim()-1)
		        	 pointstack.push(new Point2d(p.x + 1, p.y));
		         if(p.x>0)
		        	 pointstack.push(new Point2d(p.x - 1, p.y));
		      }
		   }
		   dd.setData(sdata);
		   if(polyCount>(super.getAverageHillLengthOfRowFromTo(dd,(int) maxPoint.y,(int) maxPoint.y+128))*1.3)
			   return maxPoint;
		   else return new Point2d(-1,0);
		}
}

