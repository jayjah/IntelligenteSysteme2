import com.sun.j3d.utils.geometry.*;

import com.sun.j3d.utils.universe.*;

import javafx.geometry.Point3D;

import java.awt.GraphicsConfiguration;

import javax.media.j3d.*;

import javax.vecmath.*;


public class Main {

public Main()

{
	
	
	
   SimpleUniverse universe = new SimpleUniverse();
   
//universe.getCanvas().setSize(300,300);

   BranchGroup branchgroup = new BranchGroup();
   TransformGroup transformgroup=new TransformGroup();
   Transform3D transform=new Transform3D();
ColorCube cube=new ColorCube(0.3);
   
Appearance app = new Appearance();
ColoringAttributes ca = new ColoringAttributes(new Color3f(0.2f, 0.3f, 0.8f), ColoringAttributes.ALLOW_COLOR_WRITE);
app.setColoringAttributes(ca);
   
  // transformgroup.addChild(cube);
   transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);


   universe.getViewingPlatform().setNominalViewingTransform();
   
  
   TransformGroup main_matrix=new TransformGroup();
   float size=0.1f;
   for(int y=0;y<1000;y++)
	   for(int x=0;x<800;x++){
		   //p1
		   
		   QuadArray qa=new QuadArray(4,GeometryArray.COORDINATES);
		   
		   float px=(float)(x-400)*size;
		   float py=(float)(y-500)*size;
		   
		   Point3f[] coords=new Point3f[4];
		   coords[2]=new Point3f(px,py,0);
		   
		 //p2
		   coords[0]=new Point3f(px+size,py,0);
		   
		 //p3
		   coords[1]=new Point3f(px,py+size,0);
		   
		 //p4
		   coords[3]=new Point3f(px+size,py+size,0);
		   qa.setCoordinates(0, coords);
		   
		   Shape3D s3d=new Shape3D(qa,app);
		   s3d.setGeometry(qa);
		   //s3d.addGeometry(qa);
		   main_matrix.addChild(s3d);
	   }
   
   
   
   Transform3D mt=new Transform3D();
   mt.rotX((-Math.PI/2)+0.3);
   main_matrix.setTransform(mt);
   transformgroup.addChild(main_matrix);
   branchgroup.addChild(transformgroup);
   branchgroup.compile();
   
   universe.addBranchGraph(branchgroup);
   float test=0;
   for(;;){
	   //universe.cleanup();
	   transform.setRotation(new AxisAngle4f(new Vector3f(0.001f,0.0f,0.0f),test));
	   transformgroup.setTransform(transform);
	   test+=0.1;
	   
	   
	   try {
		Thread.currentThread();
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }

}

public static void main( String[] args ) {

   new Main();

}

} // end of class Hello3d
