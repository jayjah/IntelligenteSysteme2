import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Visualisator implements Runnable {

	private float[][] data;

	private int xdim;
	private float scale=1.0f;
	private int ydim;

	Visualisator(float[][] data, int xdim, int ydim, float min, float max) {
		this.ydim = ydim;
		this.xdim = xdim;
		scale=1.0f/(max-min);
		Random rnd = new Random();

		for (int y = 0; y < ydim + 2; y++)
			for (int x = 0; x < xdim + 2; x++)
				data[x][y] = (data[x][y]-min)*scale;
				//(float) Math.abs((Math.sin((x + rnd.nextFloat() * 2) / 4.0f)
						//+ Math.cos(((x / 4) * (y / 16)) / 64.0) + Math.sin((y + rnd.nextFloat() * 4) / 4.0f)) / 25.0f);
		
		
		this.data = data;
	}

	@Override
	public void run() {

		
		
		
		SimpleUniverse universe = new SimpleUniverse();

		// universe.getCanvas().setSize(300,300);

		BranchGroup branchgroup = new BranchGroup();
		TransformGroup transformgroup = new TransformGroup();
		Transform3D transform = new Transform3D();
		ColorCube cube = new ColorCube(0.3);

		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(new Color3f(0.2f, 0.6f, 0.8f),
				ColoringAttributes.ALLOW_COLOR_WRITE);
		app.setColoringAttributes(ca);

		// transformgroup.addChild(cube);
		transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		universe.getViewingPlatform().setNominalViewingTransform();

		QuadArray qa = new QuadArray(4 * xdim * ydim, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		TransformGroup main_matrix = new TransformGroup();
		main_matrix.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		float size = 0.0020f;
		for (int y = 0; y < ydim; y++)
			for (int x = 0; x < xdim; x++) {
				// p1

				Color3f[] colors = new Color3f[4];

				float px = (float) (x - (xdim / 2)) * size;
				float py = (float) (y - (ydim / 2)) * size;

				Point3f[] coords = new Point3f[4];
				coords[0] = new Point3f(px, py, -0.3f + data[x][y] * (size * 100));

				// p2
				coords[1] = new Point3f(px + size, py, -0.3f + data[x + 1][y] * (size * 100));

				// p3

				coords[2] = new Point3f(px + size, py + size, -0.3f + data[x + 1][y + 1] * (size * 100));
				coords[3] = new Point3f(px, py + size, -0.3f + data[x][y + 1] * (size * 100));

				// p4

				qa.setCoordinates(((y) * xdim + x) * 4, coords);

				colors[0] = new Color3f(data[x][y], data[x][y] * 0.0f, (1 - data[x][y])*0.6f);
				colors[1] = new Color3f(data[x + 1][y] , data[x + 1][y] *  0.0f, (1 - data[x + 1][y] )*0.6f);
				colors[2] = new Color3f(data[x + 1][y + 1] , data[x + 1][y + 1] * 0.0f,
						(1 - data[x + 1][y + 1])*0.6f );
				colors[3] = new Color3f(data[x][y + 1], data[x][y + 1] * 0.0f, (1 - data[x][y + 1])*0.6f );

				qa.setColors(((y) * xdim + x) * 4, colors);

			}

		Shape3D s3d = new Shape3D(qa, app);

		// s3d.addGeometry(qa);
		main_matrix.addChild(s3d);

		Transform3D mt = new Transform3D();
		mt.rotX((-Math.PI / 2) + 0.1f);
		main_matrix.setTransform(mt);
		transformgroup.addChild(main_matrix);
		branchgroup.addChild(transformgroup);
		branchgroup.compile();

		universe.addBranchGraph(branchgroup);
		float test = 0;
		for (;;) {
			// universe.cleanup();
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
