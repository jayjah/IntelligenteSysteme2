package Main;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.vecmath.Point2d;

import Filters.Filter;
import Filters.FloodFillLocalMaximaFilter;
import Filters.KillTheNoiseFilter;
import Filters.MainVisualFilterSet;

public class Main {
	public static void main(String[] args) {
		//Reading the data. The filters will modify the workingData until only labels are left, then update the label list
		Data workingData=new Data(Paths.get("","src","resources",args[0]).toAbsolutePath().toString(),Paths.get("","src","resources",args[1]).toAbsolutePath().toString());
		//The original data remains untouched for validation
		Data originalData=new Data(Paths.get("","src","resources",args[0]).toAbsolutePath().toString(),Paths.get("","src","resources",args[1]).toAbsolutePath().toString());
		
		//For visualization reasons: Showing the second step
		Data secondStepData=new Data(Paths.get("","src","resources",args[0]).toAbsolutePath().toString(),Paths.get("","src","resources",args[1]).toAbsolutePath().toString());
		new KillTheNoiseFilter(30.11).work(secondStepData);
		
		//Let the filters do their job: Kill the noise with a treshold of 30.11. Then find local maxima with floodfill while excluding hills with a surface too short compared to other hill lengths in the same 128 lines multiplied by 1.3. throw out double values within the distance of 10.
		Filter mainFilterSet=new MainVisualFilterSet();
		mainFilterSet.addFilter(new KillTheNoiseFilter(30.11));
		mainFilterSet.addFilter(new FloodFillLocalMaximaFilter(1.3,10,128));
		mainFilterSet.work(workingData);

		//Counting the results: Everything that's left after the filters should be a label
		ArrayList<Point2d> templ=new ArrayList<Point2d>();
		templ.addAll(workingData.getLabels());
		int result_count=workingData.getLabels().size();
				
		//Validate if the found labels match with the original ones within neighbourhoodDistance and same height
		int numberofcorrectfoundlabels=0;
		int neighbourhoodDistance=8;
		for(Point2d point : originalData.getLabels()){
			for(Point2d foundpoints : templ){
				if (	(foundpoints.x==point.x && foundpoints.y==point.y)
					||	(Math.abs(foundpoints.x-point.x)<neighbourhoodDistance && Math.abs(foundpoints.y-point.y)<neighbourhoodDistance && originalData.getData()[(int) foundpoints.x][(int) foundpoints.y]==originalData.getData()[(int) point.x][(int) point.y])
						){
					numberofcorrectfoundlabels++;
					break;
				}
			}
		}
		
		//Calculate p and r
		double p=(double)numberofcorrectfoundlabels/((double)result_count);
		double r=(double)numberofcorrectfoundlabels/(double)originalData.getLabels().size();
		
		//Print the scores
		System.out.print("p: "+numberofcorrectfoundlabels+"/"+result_count);
		System.out.print("="+p+"\n");
		System.out.print("r: "+numberofcorrectfoundlabels+"/"+originalData.getLabels().size());
		System.out.print("="+r+"\n");
		System.out.println("f1-score: "+harmonicMean(p,r));
		
		//Visualize original Data with the labels we have found
		Visualisator originalVis = new Visualisator(originalData.getData(),workingData.getLabels(),originalData.getXdim(),originalData.getYdim(),originalData.getMin(),originalData.getMax());
		Thread t1 = new Thread(originalVis);
		t1.start();
		
		//Visualize our found labels as data with original labels
		Visualisator labelVis = new Visualisator(workingData.getData(),originalData.getLabels(),originalData.getXdim(),originalData.getYdim(),originalData.getMin(),originalData.getMax());
		Thread t2 = new Thread(labelVis);
		t2.start();
	
		//Visualize the second filter step with original labels
		Visualisator secondStepVis = new Visualisator(secondStepData.getData(),originalData.getLabels(),originalData.getXdim(),originalData.getYdim(),originalData.getMin(),originalData.getMax());
		Thread t3 = new Thread(secondStepVis);
		t3.start();
	}

	//Calculate the harmonic mean for 2 values
	public static double harmonicMean(double d1,double d2){  
		return 2 / ((1.0 / d1)+(1.0 / d2));
	}
}


