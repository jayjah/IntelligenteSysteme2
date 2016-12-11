package Filters;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.vecmath.Point2d;

import Main.Data;

/**
* Floodfill local maxima filter
* 
* <P>Searches local maxima of hills in a 2 dimensional array while counting the polygons of the hill and destroying the hill to save runtime using an altered floodfill algorithm
*  
* @author Kim Oliver Schweikert, Markus Krebs
* @version 1.0
*/
public class FloodFillLocalMaximaFilter extends Filter {

	private double treshold=1.3f;
	private int minRange=10;
	private int rowScanWidth=128;
	
	/**
	 * Constructor
	 */
	public FloodFillLocalMaximaFilter(){
		super();
	}
	
	/**
	 * Constructor
	 * @param  treshold 	treshold value for detecting false (too small) matches
	 * @param  minRange 	Minimum range of another hill within found maximum to be valid
	 * @param  rowScanWidth Range of lines to use for average hill length calculation
	 */
	public FloodFillLocalMaximaFilter(double treshold, int minRange, int rowScanWidth){
		super();
		this.treshold=treshold;
		this.minRange=minRange;
		this.rowScanWidth=rowScanWidth;
	}
	
	/**
	 * Using the altered floodfill algorithm to destroy hills while saving the coordinates of their maximum value while counting vertices and comparing them to the average hill size,
	 * sorting out possible doubled matches by checking the range. Finally setting the value of all found matches to 900.0f.
	 * @param  dd 	Data to apply the filter to
	 */
	public void work(Data dd){
		super.work(dd);

		float[][] data=dd.getData();
		Set<Point2d> foundMaxima=new HashSet<Point2d>();
		Set<Point2d> removeMaxima=new HashSet<Point2d>();
		for(int y=0;y<dd.getYdim();y++){
			for(int x=0;x<dd.getXdim();x++){
				if(dd.getData()[x][y]!=0.0f){
				Point2d maximum=this.floodfindMax(new Point2d(x,y), 0.0f, dd);
					if(maximum.x!=-1){
						foundMaxima.add(maximum);
						for(Point2d maxi : foundMaxima)
							if((Math.abs(maxi.x-maximum.x)<this.minRange && Math.abs(maxi.y-maximum.y)<this.minRange) && maxi!=maximum){
								removeMaxima.add(maximum);
							}		
					}
				}
			}
		}
		foundMaxima.removeAll(removeMaxima);
		for(Point2d p : foundMaxima)
			data[(int) p.x][(int) p.y]=900.0f;	
	}
	
	/**
	 * Altered floodfill algorithm.
	 * Using a stack of Points to make the algorithm non-recursive.
	 * Anti-Flooding the values outlined by the given borderValue, starting at a certain point while calculating the maximum within the flooded area and counting polygons
	 * used to check for possible false positives.
	 * @param  point 		Point to start the algorithm at
	 * @param borderValue	Value seen as outline value
	 * @param data			Data object to work on
	 * @return				Coordinate of the found maximum. Returns x=-1 if detecting a false positive 			
	 */
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
		   if(polyCount>(super.getAverageHillLengthOfRowFromTo(dd,(int) maxPoint.y,(int) maxPoint.y+this.rowScanWidth))*this.treshold)
			   return maxPoint;
		   else return new Point2d(-1,0);
		}
}

