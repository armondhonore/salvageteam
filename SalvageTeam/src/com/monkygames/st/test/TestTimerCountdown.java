package com.monkygames.st.test;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.niftygui.NiftyJmeDisplay;
// === Monkygames imports === //
import com.monkygames.st.control.MenuControl;
import com.monkygames.st.game.Score;
import com.monkygames.st.input.KeyBinder;
import com.monkygames.st.listener.CollectableListener;
import com.monkygames.st.map.MapObjectExtractor;
import com.monkygames.st.objects.*;
import de.lessvoid.nifty.Nifty;

/**
 * Test the scoring mechanics.
 */
public class TestTimerCountdown extends SimpleApplication {

    private Nifty nifty;
    private MenuControl mc;
    private Score score;

    public static void main(String[] args) {
        TestTimerCountdown app = new TestTimerCountdown();
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

	// setup score
	score = new Score(0,60*1000); // 1 minute of time

	// setup physics listener
	CollectableListener collectableListener = new CollectableListener(mapObjectExtractor.trashV,mapObjectExtractor.collectablesNode,score);
	bulletAppState.getPhysicsSpace().addCollisionListener(collectableListener);

	// create ship
	Ship ship = new Ship(stateManager);
	Vector3f loc = mapObjectExtractor.warp.getNode().getLocalTranslation();
	ship.setStartingPosition(loc.x,loc.y,0f);
	rootNode.attachChild(ship.getNode());

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
        initGUI(score);
        bulletAppState.setSpeed(0); //  initially
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
	mc.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    private void initGUI(Score score) {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        mc = new MenuControl(score);
        nifty.fromXml("Interface/NiftyHUD.xml", "start", mc);
        //nifty.setDebugOptionPanelColors(true);
        guiViewPort.addProcessor(niftyDisplay);
        mc.initialize(stateManager, this);
    }
}
