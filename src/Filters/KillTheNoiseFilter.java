package Filters;

import Main.Data;

public class KillTheNoiseFilter extends Filter {
	double treshold=30.11;
	
	public KillTheNoiseFilter(){
		super();
	}
	public KillTheNoiseFilter(double treshold){
		super();
		this.treshold=treshold;
	}
	
	public void work(Data dd){
		super.work(dd);
				float[][] data=dd.getData();
				for(int x=0;x<dd.getXdim();x++){
					double average=super.getAverageHeightOfRow(dd, x);
					for(int y=0;y<dd.getYdim();y++)
						data[x][y]=(data[x][y]<=(float)average+(average/treshold))?(float)0.0f:dd.getData()[x][y];	
				}
	}
}

