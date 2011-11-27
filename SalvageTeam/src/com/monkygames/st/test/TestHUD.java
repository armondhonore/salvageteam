package com.monkygames.st.test;

//import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
//import com.monkygames.st.gui.NiftyHUD;
import com.monkygames.st.input.KeyBinder;
import com.monkygames.st.objects.Ship;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * test
 * @author normenhansen
 */
public class TestHUD extends SimpleApplication implements ScreenController {
    
    Nifty nifty;

    public static void main(String[] args) {
        TestHUD app = new TestHUD();
        app.start();
    }

    @Override
    public void simpleInitApp() {
	// setup hud
	//NiftyHUD niftyHUD = new NiftyHUD(assetManager,inputManager,audioRenderer,viewPort);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        //nifty.fromX
        nifty.fromXml("Interface/NiftyHUD.xml", "hud", this);
        guiViewPort.addProcessor(niftyDisplay);

	// add physics 
	BulletAppState bulletAppState = new BulletAppState();
	stateManager.attach(bulletAppState);
	
	// setup physics state
	bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0f,-0.5f,0f));
	bulletAppState.getPhysicsSpace().setAccuracy(0.005f);


	// create ship
	Ship ship = new Ship(stateManager);
	ship.setStartingPosition(0f,0f,0f);
	rootNode.attachChild(ship.getNode());

	// add scene as map.
	Spatial scene = assetManager.loadModel("Scenes/testMap.j3o");
	// We set up collision detection for the scene by creating a
    	// compound collision shape and a static physics node with mass zero.
       	CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(scene);
    	RigidBodyControl landscape = new RigidBodyControl(sceneShape, 0);
    	scene.addControl(landscape);
    	bulletAppState.getPhysicsSpace().add(landscape);
	rootNode.attachChild(scene);

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
        DirectionalLight light = new DirectionalLight();
        //light.setDirection(new Vector3f(-0.25f, -0.25f, -1f));
        light.setDirection(new Vector3f(0f, 0f, -1f));
        rootNode.addLight(light);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void bind(Nifty nifty, Screen screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
