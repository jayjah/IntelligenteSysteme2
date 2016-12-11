package Main;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.vecmath.Point2d;

import Filters.Filter;
import Filters.TestFilter;


public class Main {

	
	
	public static void main(String[] args) {
		Data d=new Data(Paths.get("","src","resources","data1.csv").toAbsolutePath().toString(),Paths.get("","src","resources","label1.csv").toAbsolutePath().toString());
		Data d2=new Data(Paths.get("","src","resources","data1.csv").toAbsolutePath().toString(),Paths.get("","src","resources","label1.csv").toAbsolutePath().toString());
		
		
		
		
		Filter testfilter=new TestFilter();
		
		d=testfilter.work(d);
		
		
		int result_count=0;
		for(int y=0;y<d.getYdim();y++)
			for(int x=0;x<d.getXdim();x++)
				if(d.getData()[x][y]!=0.0f)
					result_count++;
		
		
		
		
		Visualisator vis = new Visualisator(d.getData(),d.getLabels(),d.getXdim(),d.getYdim(),d.getMin(),d.getMax());
		Thread t1 = new Thread(vis);
		
		ArrayList<Point2d> templ=new ArrayList<Point2d>();
		for(int y=0;y<d.getYdim();y++)
			for(int x=0;x<d.getXdim();x++)
				if(d.getData()[x][y]!=0.0f)
					templ.add(new Point2d(x,y));
		
		int numberofcorrectfoundlabels=0;
		int neighbourhoodDistance=8;
		for(Point2d point : d.getLabels()){
			for(Point2d foundpoints : templ){
				if (	(foundpoints.x==point.x && foundpoints.y==point.y)
					||	(Math.abs(foundpoints.x-point.x)<neighbourhoodDistance && Math.abs(foundpoints.y-point.y)<neighbourhoodDistance && d2.getData()[(int) foundpoints.x][(int) foundpoints.y]==d2.getData()[(int) point.x][(int) point.y])
						){
					numberofcorrectfoundlabels++;
					break;
				}
			}
		}
		
		double p=(double)numberofcorrectfoundlabels/((double)result_count);
		
		double r=(double)numberofcorrectfoundlabels/(double)d.getLabels().size();
		
		Visualisator orig_vis = new Visualisator(d2.getData(), templ,d2.getXdim(),d2.getYdim(),d.getMin(),d2.getMax());
		Thread t2 = new Thread(orig_vis);
		
		System.out.print("p: "+numberofcorrectfoundlabels+"/"+result_count);
		System.out.print("="+p+"\n");
		System.out.print("r: "+numberofcorrectfoundlabels+"/"+d.getLabels().size());
		System.out.print("="+r+"\n");
		
		System.out.println("f1-score: "+harmonicMean(p,r));
		
		
		
		t1.start();
		t2.start();
		
	}

	public static double harmonicMean(double d1,double d2)  
	{  
		double sum = 0.0;

			sum += 1.0 / d1;
			sum += 1.0 / d2;
		 
		return 2 / sum; 
	}
	
} // end of class Hello3d


