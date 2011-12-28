package com.monkygames.st.game;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.niftygui.NiftyJmeDisplay;
// === Monkygames imports === //
import com.monkygames.st.control.MenuControl;
import com.monkygames.st.game.Score;
import com.monkygames.st.input.KeyBinder;
import com.monkygames.st.io.ScoreStore;
import com.monkygames.st.listener.CollectableListener;
import com.monkygames.st.listener.InGameListener;
import com.monkygames.st.map.MapObjectExtractor;
import com.monkygames.st.objects.*;
import de.lessvoid.nifty.Nifty;

/**
 * Test the scoring mechanics.
 */
public class RushGame extends SimpleApplication implements IGame{

    private Nifty nifty;
    private MenuControl mc;
    private Score score;
    private BulletAppState bulletAppState = new BulletAppState();
    private CollectableListener collectableListener;
    private Ship ship;
    private MapObjectExtractor mapObjectExtractor;
    private ChaseCamera chaseCam;
    private ScoreStore scoreStore;
    private static final long maxTime = 60*1000;
    //private static final long maxTime = 3000;
    
    public static void main(String[] args) {
        RushGame app = new RushGame();
        app.start();
    }

    @Override
    public void simpleInitApp() {
	// add physics 
	stateManager.attach(bulletAppState);
	
	// setup physics state
	bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0f,-0.5f,0f));
	bulletAppState.getPhysicsSpace().setAccuracy(0.005f);

	// add scene as map.
	Node scene = (Node)assetManager.loadModel("Scenes/BonusMap.j3o");
	// parse map objects 
	mapObjectExtractor = new MapObjectExtractor(scene,stateManager,bulletAppState,rootNode);

	// setup score
	score = new Score(0,maxTime,this); // 1 minute of time

	// setup physics listener
	collectableListener = new CollectableListener(mapObjectExtractor.trashV,mapObjectExtractor.collectablesNode,score,this);
	bulletAppState.getPhysicsSpace().addCollisionListener(collectableListener);

	// create ship
	ship = new Ship(stateManager);
	Vector3f loc = mapObjectExtractor.warp.getNode().getLocalTranslation();
	ship.setStartingPosition(loc.x,loc.y,0f);
	rootNode.attachChild(ship.getNode());

	// setup shadows to off for now
       	rootNode.setShadowMode(ShadowMode.Off);

	// create keybinder
	KeyBinder keyBinder = new KeyBinder(inputManager,ship);

	// setup camera
	flyCam.setEnabled(false);
	chaseCam = new ChaseCamera(cam, ship.getNode(), inputManager);
        chaseCam.setDefaultHorizontalRotation((float)(Math.PI/-2.0f));
	chaseCam.setDefaultVerticalRotation((float)(Math.PI/1f));
	chaseCam.setInvertHorizontalAxis(true);
	chaseCam.setInvertVerticalAxis(true);
        chaseCam.setToggleRotationTrigger(new KeyTrigger(KeyInput.KEY_M));

	// GO GO GO
        scoreStore = new ScoreStore();
        initGUI(score);
        bulletAppState.setSpeed(0); //  initially
    }

    public void reinit(){
        // get new score
        score = new Score(0,maxTime,this); // 1 minute of time
        // resest collectibles
        collectableListener.reinit(score);
        mc.setScore(score);
        // remove ship
        rootNode.detachChild(ship.getNode());
        //ship.detach();
        // create new ship
        //stateManager = new AppStateManager(this); // TODO: make this work
        ship = new Ship(stateManager);
        //ship.stopAllForces();
	Vector3f loc = mapObjectExtractor.warp.getNode().getLocalTranslation();
	ship.setStartingPosition(loc.x,loc.y,0f);
	rootNode.attachChild(ship.getNode());
        
        // create keybinder
	KeyBinder keyBinder = new KeyBinder(inputManager,ship);
	// setup camera
	chaseCam.setEnabled(false);
	chaseCam = new ChaseCamera(cam, ship.getNode(), inputManager);
        chaseCam.setDefaultHorizontalRotation((float)(Math.PI/-2.0f));
	chaseCam.setDefaultVerticalRotation((float)(Math.PI/1f));
	chaseCam.setInvertHorizontalAxis(true);
	chaseCam.setInvertVerticalAxis(true);
        chaseCam.setToggleRotationTrigger(new KeyTrigger(KeyInput.KEY_M));
        
        ship.stopAllForces();
        bulletAppState.setSpeed(0);
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

    /**
     * Signals the game should end.
     **/
    public void endGame(){
	// stop game
        this.getStateManager().getState(BulletAppState.class).setSpeed(0f);
	// record time
        scoreStore.add(score);
        scoreStore.persist();
	// TODO get user name
	// save score using java io
	// return to main menu
        mc.displayRank(score);
    }

    private void initGUI(Score score) {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
  
        mc = new MenuControl(score, scoreStore);
        nifty.fromXml("Interface/NiftyHUD.xml", "start", mc);
        //nifty.setDebugOptionPanelColors(true);
        guiViewPort.addProcessor(niftyDisplay);
        mc.initialize(stateManager, this);
    }
}
