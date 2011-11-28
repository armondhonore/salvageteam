package com.monkygames.st.test;

//import com.bulletphysics.collision.broadphase.Dbvt.Node;
//import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Node;
// === Monkygames imports === //
import com.monkygames.st.input.KeyBinder;
import com.monkygames.st.map.MapObjectExtractor;
import com.monkygames.st.objects.*;

/**
 * test
 */
public class TestModelLoad extends SimpleApplication {

    public static void main(String[] args) {
        TestModelLoad app = new TestModelLoad();
        app.start();
    }

    @Override
    public void simpleInitApp() {
	// add physics 
	BulletAppState bulletAppState = new BulletAppState();
	stateManager.attach(bulletAppState);
	
	// setup physics state
	bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0f,-0.5f,0f));
	bulletAppState.getPhysicsSpace().setAccuracy(0.005f);

	// add a box
        Box b = new Box(new Vector3f(0f,0f,0f), 1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        geom.updateModelBound();

        //Material mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("m_Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

	RigidBodyControl boxControl = new RigidBodyControl(0);
	geom.addControl(boxControl);
	boxControl.setPhysicsLocation(new Vector3f(4f,4f,0f));
	bulletAppState.getPhysicsSpace().add(boxControl);
        rootNode.attachChild(geom);

	// 2nd box
	Box myBoxx = new Box(1,1,1);
	Spatial myBox = new Geometry("Box",myBoxx);
	myBox.addControl(new RigidBodyControl( 0.0f ));
	myBox.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(-4,4,0));
	bulletAppState.getPhysicsSpace().add(myBox);
        //Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("m_Color", ColorRGBA.Red);
        myBox.setMaterial(mat2);
	rootNode.attachChild(myBox);
    
	// create Trash
	/*
	Trash trash = new Trash(stateManager);
	trash.setStartingPositionNonPhysics(4,-4,0);
	rootNode.attachChild(trash.getNode());
	*/

	// create warp point
	Warp warp = new Warp(stateManager);
	warp.setStartingPositionNonPhysics(0,0,-1);
	rootNode.attachChild(warp.getNode());

	// create ship
	Ship ship = new Ship(stateManager);
	ship.setStartingPosition(0f,0f,0f);
	rootNode.attachChild(ship.getNode());

	// add scene as map.
	Node scene = (Node)assetManager.loadModel("Scenes/testMap.j3o");
	// parse map objects
	MapObjectExtractor mapObjectExtractor = new MapObjectExtractor(scene,stateManager,bulletAppState,rootNode);

	/*
	// We set up collision detection for the scene by creating a
    	// compound collision shape and a static physics node with mass zero.
       	CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(scene);
    	RigidBodyControl landscape = new RigidBodyControl(sceneShape, 0);
    	scene.addControl(landscape);
    	bulletAppState.getPhysicsSpace().add(landscape);
	rootNode.attachChild(scene);
	*/

	// setup shadows to off for now
       	rootNode.setShadowMode(ShadowMode.Off);


	// create keybinder
	KeyBinder keyBinder = new KeyBinder(inputManager,ship);

	// setup camera
	flyCam.setEnabled(false);
	ChaseCamera chaseCam = new ChaseCamera(cam, ship.getNode(), inputManager);
        chaseCam.setDefaultHorizontalRotation((float)(Math.PI/-2.0f));
	chaseCam.setDefaultVerticalRotation((float)(Math.PI/1f));
	chaseCam.setInvertHorizontalAxis(true);
	chaseCam.setInvertVerticalAxis(true);

	// GO GO GO
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
