package com.monkygames.st.test;

//import com.bulletphysics.collision.broadphase.Dbvt.Node;
//import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
// === Monkygames imports === //
import com.monkygames.st.game.Score;
import com.monkygames.st.input.KeyBinder;
import com.monkygames.st.listener.CollectableListener;
import com.monkygames.st.map.MapObjectExtractor;
import com.monkygames.st.objects.*;

/**
 * Tests the collision detection for trash cans.
 */
public class TestMap extends SimpleApplication {

    public static void main(String[] args) {
        TestMap app = new TestMap();
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

	// add scene as map.
	Node scene = (Node)assetManager.loadModel("Scenes/BonusMap.j3o");
	// parse map objects 
	MapObjectExtractor mapObjectExtractor = new MapObjectExtractor(scene,stateManager,bulletAppState,rootNode);

	// setup physics listener
	CollectableListener collectableListener = new CollectableListener(mapObjectExtractor.trashV,mapObjectExtractor.collectablesNode,new Score());
	bulletAppState.getPhysicsSpace().addCollisionListener(collectableListener);

	// create ship
	Ship ship = new Ship(stateManager);
	Vector3f loc = mapObjectExtractor.warp.getNode().getLocalTranslation();
	ship.setStartingPosition(loc.x,loc.y,0f);
	rootNode.attachChild(ship.getNode());

	// setup collision detection app state
/*
	CollectAppState collectAppState = new CollectAppState();
	collectAppState.setCollectableObjects(mapObjectExtractor.trashV);
	collectAppState.setShip(ship);
	stateManager.attach(collectAppState);	
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
