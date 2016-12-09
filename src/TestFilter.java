import java.util.ArrayList;

public class TestFilter extends Filter {

	public TestFilter(){
		super();
	}
	public Data work(Data d){
		Data dd= super.work(d);
		float[][] data=dd.getData();
		//Hier Zeugs mit den Daten machen.
		int threshold=30;
		
		//pass1
		for(int x=0;x<d.getXdim();x++){
			double average=super.getAverageHeightOfRow(d, x);
			for(int y=0;y<d.getYdim();y++)
				data[x][y]=(data[x][y]<=(float)average+(average/threshold))?(float)0.0f:dd.getData()[x][y];
				
		}
		
		//pass2
		threshold=-10;
		for(int x=0;x<d.getXdim();x++){
			double average=super.getAverageHeightOfRow(d, x);
			for(int y=0;y<d.getYdim();y++)
				data[x][y]=(data[x][y]<=(float)average+(average/threshold))?(float)0.0f:dd.getData()[x][y];
				
		}
		
		dd.setData(data);
		data=dd.getData();
		
		//nu gehts weiter: durchschnittliche berglänge ermitteln, berge unter durchschnitt killen
		int checkwidth=64;
	
		for(int i=0;i<(dd.getXdim()/checkwidth);i++){
			int length=super.getAverageHillLengthOfRowFromTo(dd, (i*checkwidth), (i*checkwidth)+checkwidth);
			System.out.println(length);
		}
		
		ArrayList<ArrayList<Float>> peakpoints_lines=new ArrayList<ArrayList<Float>>();
		for(int y=0;y<d.getYdim();y++){
			peakpoints_lines.add(super.getHillsMaximumHeightofLine(dd, y));
			
		}
		
		ArrayList<ArrayList<Float>> peakpoints_rows=new ArrayList<ArrayList<Float>>();
		for(int x=0;x<d.getXdim();x++){
			peakpoints_rows.add(super.getHillsMaximumHeightofRow(dd, x));
			
		}
		
		
		
				
				//maximum pro bergreihe finden, rest planieren
				for(int y=0;y<d.getYdim();y++){
					ArrayList<Float> averages=peakpoints_lines.get(y);
					int avcount=0;
					float tempval=0.0f;
					if(averages.size()>0){
					for(int x=0;x<d.getXdim();x++)
						if(data[x][y]!=averages.get(avcount))
							data[x][y]=0.0f;
						else{
							if(data[x][y]!=tempval){
								if(avcount<averages.size()-1)
									avcount++;
								tempval=data[x][y];
							}
						}
					}
						
				}
				
				
				dd.setData(data);
				data=dd.getData();
				/*
				//maximum pro bergspalte finden, rest planieren
				for(int x=0;x<d.getXdim();x++){
					ArrayList<Float> averages=peakpoints_rows.get(x);
					int avcount=0;
					float tempval=0.0f;
					
					if(averages.size()>0){
					for(int y=0;y<d.getYdim();y++)
						if(data[x][y]!=averages.get(avcount))
							data[x][y]=0.0f;
						else{
							if(data[x][y]!=tempval){
								if(avcount<averages.size()-1)
									avcount++;
								tempval=data[x][y];
							}
						}
					
					}
				}
				*/
				
				
				
				for(int x=0;x<d.getXdim();x++){
					ArrayList<Float> averages=peakpoints_rows.get(x);
					
				
					
					if(averages.size()>0){
						
						for(int y=0;y<d.getYdim();y++){
							boolean inAverages=false;
							for(float f :averages){
								if(data[x][y]==f)
									inAverages=true;
								
								
							}
							if(!inAverages)
								data[x][y]=0.0f;
							
						}
						
					
					
					}
				}
				
				
				
				dd.setData(data);
				data=dd.getData();
		
		return  dd;
		
		
	}
}
