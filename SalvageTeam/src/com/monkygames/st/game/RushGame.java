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
import com.monkygames.st.input.KeyBinder;
import com.monkygames.st.io.ScoreStore;
import com.monkygames.st.listener.CollectableListener;
import com.monkygames.st.map.MapObjectExtractor;
import com.monkygames.st.objects.*;
import de.lessvoid.nifty.Nifty;

/**
 * Test the scoring mechanics.
 */
public class RushGame extends SimpleApplication implements IGame{
    
    private static boolean DEBUG = false;

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
    private boolean gamePaused = false;
    private AudioNode lvlMusicNode;
    private AudioNode menuMusicNode;
    private AudioNode collectionAudioNode;
    
    public static void main(String[] args) {
        RushGame app = new RushGame();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDebug(DEBUG);
        initStates();

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
        this.setPaused(true); // ensure it's paused
        //stateManager = new AppStateManager(this);        bulletAppState.cleanup();
        initStates();
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
        if (ship == null) {
            ship = new Ship(stateManager);
        }
        
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
        bulletAppState.setSpeed(0f);
	// stop game
        score.getTime().stop();
        //ship.stopZLock(); // ensure we cannot collide with anything any more
        ship.reset();
        
	// record time
        scoreStore.add(score);
        scoreStore.sort();
	// TODO get user name
	// save score using java io
	// return to main menu
        mc.displayRank(score, scoreStore);
        scoreStore.persist();
    }

    private void initGUI(Score score) {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();

	initMusic();
	initSoundEffects();
  
        mc = new MenuControl(score, scoreStore,lvlMusicNode,menuMusicNode);
        nifty.fromXml("Interface/NiftyHUD.xml", "start", mc);
        //nifty.setDebugOptionPanelColors(true);
        guiViewPort.addProcessor(niftyDisplay);
        mc.initialize(stateManager, this);
    }

    private void initMusic(){
	lvlMusicNode = new AudioNode(assetManager,"Sound/Music/faster_than_light-stephen_burns.ogg",false);
        lvlMusicNode.setLooping(true);  // activate continuous playing
        lvlMusicNode.setVolume(0.5f);
        rootNode.attachChild(lvlMusicNode);

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

	// setup score
	score = new Score(0,maxTime,this); // 1 minute of time or maxTime time

	// setup physics listener
        if (collectableListener != null) {
            collectableListener.reinit(score);
        } else {
            collectableListener = new CollectableListener(mapObjectExtractor.trashV,mapObjectExtractor.collectablesNode,score,this);
            bulletAppState.getPhysicsSpace().addCollisionListener(collectableListener);
        }
    }

    public void setPaused(boolean on) {
        this.gamePaused = on;
    }
    
    public boolean getPaused() {
        return gamePaused;
    }
    public void activateCollectionSound(){
	collectionAudioNode.stop();
	collectionAudioNode.play();
    }

    private void setDebug(boolean debug) {
        setDisplayFps(debug);
        setDisplayStatView(debug);
        setShowSettings(debug);
    }
}
