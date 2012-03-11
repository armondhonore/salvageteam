package com.monkygames.st.game;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
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
import com.monkygames.st.control.HudControl;
import com.monkygames.st.effects.TrashCollectionEffect;
import com.monkygames.st.input.KeyBinder;
import com.monkygames.st.io.ScoreStore;
import com.monkygames.st.listener.CollectableListener;
import com.monkygames.st.map.MapObjectExtractor;
import com.monkygames.st.objects.*;
import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test the scoring mechanics.
 */
public class RushGame extends SimpleApplication implements IGame{

// ============= Class variables ============== // 
    private static boolean DEBUG = false;
    private Nifty nifty;
    private MenuControl mc;
    private HudControl hc;
    private Score score;
    private BulletAppState bulletAppState = new BulletAppState();
    private CollectableListener collectableListener;
    private Ship ship;
    private MapObjectExtractor mapObjectExtractor;
    private ChaseCamera chaseCam;
    private ScoreStore scoreStore;
    private static final long maxTime = 60*1000;
    private boolean gamePaused = true;
    private AudioNode lvlMusicNode;
    private AudioNode menuMusicNode;
    private AudioNode collectionAudioNode;
    private float mapProgress = 0f;
    private boolean loadingMap;
    private boolean reinitMap;
    private int loadingCount;
    private int maxLoadingCount = 8;
    private boolean waitForScreenUpdate;
    private int waitCounter;
    private boolean isMapLoaded = false;
    private KeyBinder keyBinder;
    //private long count = 0l; // debug var to count frames
    
// ============= Constructors ============== //
    public static void main(String[] args) {
        RushGame app = new RushGame();
        app.start();
    }

// ============= Public Methods ============== //
    @Override
    public void simpleInitApp() {
        setDebug(DEBUG);
	// disables logging
	Logger.getLogger("").setLevel(Level.SEVERE);

	// create a new score
        scoreStore = new ScoreStore();
        initGUI(score);
        bulletAppState.setSpeed(0); //  initially
    }

    /**
     * Initializes the map for the first time.
     **/
    public void initMap(){
	loadingCount = 0;
	waitCounter = 0;
	if(!this.isMapLoaded){
            loadingMap = true;
	}else{
	    reinitMap = true;
	}
	waitForScreenUpdate = true;
    }
    public void loadingMap(){
	switch(loadingCount){
	    case 1:	
		updateMapProgress(0.10f, "Initializing Map");
		initStates();
		updateMapProgress(0.10f, "Create Ship");
		break;

	    case 2:
		// create ship
		ship = new Ship(stateManager);
		Vector3f loc = mapObjectExtractor.warp.getNode().getLocalTranslation();
		ship.setStartingPosition(loc.x,loc.y,0f);
		rootNode.attachChild(ship.getNode());
		updateMapProgress(0.10f, "Setup Shadows");
		break;
	    case 3:
		// setup shadows to off for now
		rootNode.setShadowMode(ShadowMode.Off);

		updateMapProgress(0.05f, "Setup Keyboard Input");
		break;
	    case 4:

		// create keybinder
		keyBinder = new KeyBinder(inputManager,ship);

		updateMapProgress(0.05f, "Setup Camera");
		break;
	    case 5:

		// setup camera
		flyCam.setEnabled(false);
		chaseCam = new ChaseCamera(cam, ship.getNode(), inputManager);
		chaseCam.setDefaultHorizontalRotation((float)(Math.PI/-2.0f));
		chaseCam.setDefaultVerticalRotation((float)(Math.PI/1f));
		chaseCam.setInvertHorizontalAxis(true);
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setToggleRotationTrigger(new KeyTrigger(KeyInput.KEY_M));
		updateMapProgress(0.10f, "Setup Sound Effects");
		break;
	    case 6:
		initSoundEffects();
		updateMapProgress(0.10f, "Setup Game Music");
		break;
	    case 7:
		initGameMusic();

		// update gui
		mc.setLvlMusic(lvlMusicNode);
		hc.setThrustTimer(ship.shipControl.effectTimer);

		mapProgress = 1f;
		updateMapProgress(0.00f, "Done");
		break;
	}
	loadingCount++;
    }

    public void reinit(){
	switch(loadingCount){
	    case 0:
		updateMapProgress(0.10f, "Reloading");
		break;
	    case 1:
		this.setPaused(true); // ensure it's paused
		//stateManager = new AppStateManager(this);        bulletAppState.cleanup();
		initStates();
		updateMapProgress(0.10f, "Create Ship");
		break;
	    case 2:
		// remove ship
		rootNode.detachChild(ship.getNode());
		//ship.detach();
		// create new ship
		//stateManager = new AppStateManager(this); // TODO: make this work
		if (ship == null) {
		    ship = new Ship(stateManager);
		}
		updateMapProgress(0.10f, "Loading Map");
		break;
	    case 3:
		//ship.stopAllForces();
		Vector3f loc = mapObjectExtractor.warp.getNode().getLocalTranslation();
		ship.setStartingPosition(loc.x,loc.y,0f);
		rootNode.attachChild(ship.getNode());
		updateMapProgress(0.10f, "Setup Keyboard Input");
		break;
		
	    case 4:
		// create keybinder
		KeyBinder keyBinder = new KeyBinder(inputManager,ship);
		updateMapProgress(0.10f, "Setup Camera");
		break;
	    case 5:
		// setup camera
		chaseCam.setEnabled(false);
		chaseCam = new ChaseCamera(cam, ship.getNode(), inputManager);
		chaseCam.setDefaultHorizontalRotation((float)(Math.PI/-2.0f));
		chaseCam.setDefaultVerticalRotation((float)(Math.PI/1f));
		chaseCam.setInvertHorizontalAxis(true);
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setToggleRotationTrigger(new KeyTrigger(KeyInput.KEY_M));
		updateMapProgress(0.10f, "Reading Game");
		break;
		
	    case 6:
		updateMapProgress(0.10f, "Finished");
		ship.stopAllForces();
		bulletAppState.setSpeed(0);
		break;
	    case 7:
		updateMapProgress(0.00f, "Done");
		break;
	}
	loadingCount++;

    }
    
    @Override
    public void simpleUpdate(float tpf) {
        mc.update(tpf);
	if(!gamePaused){
	    hc.update(tpf);
	}
	if(waitForScreenUpdate){
	    if(waitCounter == 20){
 	        waitForScreenUpdate = false;
	    }else{
	   	waitCounter++;
	    }
	}else if(loadingMap && loadingCount < maxLoadingCount ){
	    loadingMap();
	}else if(loadingMap && loadingCount >= maxLoadingCount){
	    // start the game!
	    getStateManager().getState(BulletAppState.class).setSpeed(1.0f);
	    mc.unPause();
	    loadingMap = false;
	    this.isMapLoaded = true;
	}else if(reinitMap && loadingCount < maxLoadingCount){
	    reinit();
	}else if(reinitMap && loadingCount >= maxLoadingCount){
	    getStateManager().getState(BulletAppState.class).setSpeed(1.0f);
	    mc.unPause();
	    reinitMap = false;
	}
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Signals the game should end.
     **/
    public void endGame(){
        bulletAppState.setSpeed(0f);
	// stop game
        score.getTime().stop();
        ship.reset(); // ensure we cannot collide with anything any more
       	// get user's name 
	mc.displayPlayerInput();
    }

    public void abortGame(){
        bulletAppState.setSpeed(0f);
	// stop game
        score.getTime().stop();
        ship.reset(); // ensure we cannot collide with anything any more
    }

    /**
     * Saves the score with the specified name.
     * @param name the name of the player.
     */
    public void saveScore(String name){
	score.setPlayer(new Player(name));
	// record time
        scoreStore.add(score);
        scoreStore.sort();
        scoreStore.persist();
    }

    public void setCursorVisible(boolean visible){
    	inputManager.setCursorVisible(visible);
    }

    public void setPaused(boolean on) {
        this.gamePaused = on;
        keyBinder.setPaused(on);
    }
    
    public boolean getPaused() {
        return gamePaused;
    }
    public void activateCollectionSound(){
	collectionAudioNode.stop();
	collectionAudioNode.play();
    }
    /**
     * Starts a new collection effect.
     * @param location the location of the effect.
     **/
    public void startCollectEffect(Vector3f location){
        TrashCollectionEffect effect = new TrashCollectionEffect(assetManager,location);
        rootNode.attachChild(effect.getNode());
        effect.startEffect();
    }

// ============= Private Methods ============== //
    /**
     * Initializes the Menu GUI and HUD.
     * @param score the score associated with this map.
     */
    private void initGUI(Score score) {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
	// disables nifty logging
	Logger.getLogger("de.lessvoid.nifty").setLevel(Level.SEVERE); 
	Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.SEVERE); 

	initMenuMusic();
	//initSoundEffects();
  
        mc = new MenuControl(scoreStore,menuMusicNode);
	hc = new HudControl(score);
        //nifty.fromXml("Interface/NiftyHUD.xml", "start", mc);
        nifty.fromXml("Interface/NiftyHUD.xml", "start", mc, hc);
	hc.setNifty(nifty);
        //nifty.setDebugOptionPanelColors(true);
        guiViewPort.addProcessor(niftyDisplay);
        mc.initialize(stateManager, this);
	hc.initialize(stateManager, this);
	// show mouse cursor
	//flyCam.setDragToRotate(true);
	flyCam.setEnabled(false);
    }

    private void initGameMusic(){
	lvlMusicNode = new AudioNode(assetManager,"Sound/Music/faster_than_light-stephen_burns.ogg",false);
        lvlMusicNode.setLooping(true);  // activate continuous playing
        lvlMusicNode.setVolume(0.5f);
        rootNode.attachChild(lvlMusicNode);
    }
    private void initMenuMusic(){
	menuMusicNode = new AudioNode(assetManager,"Sound/Music/collider-stephen_burns.ogg",false);
        menuMusicNode.setLooping(true);  // activate continuous playing
        menuMusicNode.setVolume(0.5f);
        rootNode.attachChild(menuMusicNode);
    }
    private void initSoundEffects(){
	collectionAudioNode = new AudioNode(assetManager,"Sound/Action/Power-Up-KP-1879176533.wav",false);
        collectionAudioNode.setLooping(false);  // activate continuous playing
        collectionAudioNode.setVolume(2.0f);
        rootNode.attachChild(collectionAudioNode);
    }

    private void initStates() {
	mapProgress = 0f;
	updateMapProgress(0.20f, "Loading Map");
        Node scene = null;
        // add scene as map.
        scene = (Node)assetManager.loadModel("Scenes/BonusMap.j3o");
        if (mapObjectExtractor == null) {
            // add physics 
            stateManager.attach(bulletAppState);
	
            // setup physics state
            bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0f,-0.5f,0f));
            bulletAppState.getPhysicsSpace().setAccuracy(0.005f);
            // parse map objects
            mapObjectExtractor = new MapObjectExtractor(scene,stateManager,bulletAppState,rootNode);
        }
	updateMapProgress(0.10f, "Setup new score");

	// setup score
	score = new Score(0,maxTime,this); // 1 minute of time or maxTime time
	mc.setScore(score);
	hc.setScore(score);
	updateMapProgress(0.10f, "Setup Physics");

	// setup physics listener
        if (collectableListener != null) {
            collectableListener.reinit(score);
        } else {
            collectableListener = new CollectableListener(mapObjectExtractor.trashV,mapObjectExtractor.collectablesNode,score,this);
            bulletAppState.getPhysicsSpace().addCollisionListener(collectableListener);
        }
    }

    private void setDebug(boolean debug) {
        setDisplayFps(debug);
        setDisplayStatView(debug);
        setShowSettings(debug);
    }
    private void updateMapProgress(float percentage, String text){
	mc.updateMapProgress(mapProgress, text);
	mapProgress+=percentage;
    }
}
