package Main;
import java.util.ArrayList;

import javax.media.j3d.BranchGroup;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point2d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class Visualisator implements Runnable {

	
	private SimpleUniverse universe = new SimpleUniverse();

	private BranchGroup branchgroup = new BranchGroup();
	private TransformGroup transformgroup = new TransformGroup();
	private Transform3D transform = new Transform3D();
	private Shape3D s3d;
	private TransformGroup main_matrix = new TransformGroup();
	
	private float[][] data;
	private ArrayList<Point2d> labels;
	private int xdim;
	private float scale=1.0f;
	private int ydim;

	public Visualisator(float[][] data, ArrayList<Point2d> labels, int xdim, int ydim, float min, float max) {
		this.labels=labels;
		this.updateData(data, xdim, ydim, min, max);
		
	}
	
	public void updateData(float[][] data, int xdim, int ydim, float min, float max){
		this.ydim = ydim;
		this.xdim = xdim;
		scale=1.0f/(max-min);
		
		this.data=new float[xdim+2][ydim+2];
		
		
		
		
		//Scale the data for the view: Substract the minimum value to put the lowest to 0.0 - Then scale everything so that the maximum value is 1.0.
		for (int y = 0; y < ydim + 2; y++)
			for (int x = 0; x < xdim + 2; x++){
				this.data[x][y] = ((data[x][y]-min)*scale)>=0?(data[x][y]-min)*scale:0.0f;	
			}
		
		transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		universe.getViewingPlatform().setNominalViewingTransform();
		main_matrix=new TransformGroup();
		main_matrix.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	}
	

	@Override
	public void run() {
		
		
		
		QuadArray qa = new QuadArray(4 * xdim * ydim, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		
		float size = 0.0020f;
		for (int y = 0; y < ydim; y++)
			for (int x = 0; x < xdim; x++) {

				Color3f[] colors = new Color3f[4];
				Point3f[] coords = new Point3f[4];
				
				float px = (float) (x - (xdim / 2)) * size;
				float py = (float) (y - (ydim / 2)) * size;

				boolean islabel[]={false,false,false,false};
				for(Point2d p : this.labels){
					if(p.x == x && p.y == y)
							islabel[0]=true;
					if(p.x==x+1 && p.y == y)
						islabel[1]=true;
					if(p.x==x+1 && p.y == y+1)
						islabel[2]=true;
					if(p.x==x && p.y == y+1)
						islabel[3]=true;
				}
				
				
				
				//Polygons for the Map
				coords[0] = new Point3f(px, py, -0.3f + data[x][y] * (size * 100));
				coords[1] = new Point3f(px + size, py, -0.3f + data[x + 1][y] * (size * 100));
				coords[2] = new Point3f(px + size, py + size, -0.3f + data[x + 1][y + 1] * (size * 100));
				coords[3] = new Point3f(px, py + size, -0.3f + data[x][y + 1] * (size * 100));
				
				//Add the quad of 4 polygons
				qa.setCoordinates(((y) * xdim + x) * 4, coords);

				//generate colors for those polygons
				if(islabel[0])
					colors[0] = new Color3f(0,1,0);
				else
					colors[0] = new Color3f(data[x][y], data[x][y] * 0.0f, (1 - data[x][y])*0.4f);
				
				if(islabel[1])
					colors[1] = new Color3f(0,1,0);
				else
					colors[1] = new Color3f(data[x + 1][y] , data[x + 1][y] *  0.0f, (1 - data[x + 1][y] )*0.4f);
				
				if(islabel[2])
					colors[2] = new Color3f(0,1,0);
				else
					colors[2] = new Color3f(data[x + 1][y + 1] , data[x + 1][y + 1] * 0.0f,	(1 - data[x + 1][y + 1])*0.4f );
						
				if(islabel[3])
					colors[3] = new Color3f(0,1,0);
				else
					colors[3] = new Color3f(data[x][y + 1], data[x][y + 1] * 0.0f, (1 - data[x][y + 1])*0.4f );
					
				
				
				
				
				
				//set the colors to the quad
				qa.setColors(((y) * xdim + x) * 4, colors);
			}
		//Make a shape out of all quads
		s3d = new Shape3D(qa);

		//And add it to the matrix
		main_matrix.addChild(s3d);

		//Rotate it on the x-axis so we don't view from top
		Transform3D mt = new Transform3D();
		mt.rotX((-Math.PI / 2) + 0.0f);
		main_matrix.setTransform(mt);
		
		//Add the matrix to the transformgroup
		transformgroup.addChild(main_matrix);
		branchgroup.addChild(transformgroup);
		branchgroup.compile();

		//Add everything to the universe
		universe.addBranchGraph(branchgroup);

		float test = 0;
		
		//Infinite loop: rotate the view
		for (;;) {
			transform.setRotation(new AxisAngle4f(new Vector3f(0.0f, 0.0001f, 00f), test));
			transformgroup.setTransform(transform);
			test += 0.001;

			try {
				Thread.currentThread();
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}