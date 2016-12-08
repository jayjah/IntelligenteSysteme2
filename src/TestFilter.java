
public class TestFilter extends Filter {

	public TestFilter(){
		super();
	}
	public Data work(Data d){
		Data dd= super.work(d);
		float[][] data=dd.getData();
		//Hier Zeugs mit den Daten machen.
		
		for(int x=0;x<d.getXdim();x++){
			double average=super.getAverageHeightOfRow(d, x);
			for(int y=0;y<d.getYdim();y++)
				data[x][y]=(data[x][y]<=(float)average)?(float)0.0f:dd.getData()[x][y];
				
		}
		dd.setData(data);
		return  dd;
		
		
	}
}
