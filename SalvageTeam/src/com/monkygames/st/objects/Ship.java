/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.objects;

// === jme imports === //
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.monkygames.st.control.ShipControl;
import com.monkygames.st.control.ZLockControl;

/**
 * A ship model.
 * @version 1.0
 */
public class Ship extends Model{

// ============= Class variables ============== //
    /**
     * The default speed for the ship.
     */
    private static float speedForce = 4f;
    /**
     * This is the turbo force which is added ontop of the normal force.
     */
    private static float turboForce = 3f;
    /**
     * Controls the ship behaviour except for phys.
     **/
    public ShipControl shipControl;
    /**
     * Controls the physics of the ship.
     **/
    private RigidBodyControl rigidBodyControl;
    /**
     * Controls the exhaust.
     **/
    private ParticleEmitter exhaust;
    /**
     * Holds the audio for making a thrust sound.
     **/
    private AudioNode thrustAudioNode;
    /**
     * This is the thrust forward force when pressing the UP key.
     **/
    private float forwardForce = speedForce;
// ============= Constructors ============== //
    public Ship(AppStateManager stateManager){
	super(stateManager);
	// load 3d model
        loadNode("Models/ships/3/spaceship.j3o");
	setupPhysics();
	setupControllers();
	setupEffects();
	setupAudio();
    }
// ============= Public Methods ============== //
    /**
     * Rotates the object clockwise.
     **/
    public void rotateRight(){
	rigidBodyControl.setAngularVelocity( new Vector3f(0f,0f,-4f));
    }
    /**
     * Rotates the object counter-clockwise.
     **/
    public void rotateLeft(){
	rigidBodyControl.setAngularVelocity( new Vector3f(0f,0f,4f));
    }
    /**
     * Stops rotating regardless the direction of rotation.
     **/
    public void rotateStop(){
	rigidBodyControl.setAngularVelocity( new Vector3f(0f,0f,0.0f));
    }
    /**
     * Applie a thrust force to the ship.
     * @param isThrusting true if thrusting and false otherwise.
     * @param isTurbo true if turbo is to be applied and false otherwise.
     * @param tpf times per frame
     */
    public void thrust(boolean isThrusting, boolean isTurbo, float tpf){
	if(isThrusting){
	    Vector3f forwardDir = node.getLocalRotation().getRotationColumn(1);
	    forwardDir.normalize();
	    forwardDir.mult( new Vector3f(-1f,-1f,-1f) );
	    if(isTurbo){
		forwardForce = speedForce + turboForce;
	    }else{
		forwardForce = speedForce;
	    }
	    forwardDir.mult(forwardForce,forwardDir);
	    rigidBodyControl.applyCentralForce(forwardDir);
	}
        Vector3f trans = node.getLocalTranslation();
        if (trans.z != 0) {
            trans.z = 0;
            this.setLocation(trans);
        }
    }
    public void stopThrust(){
	//rigidBodyControl.clearForces();
    }
    /**
     * Starts the thrust effect.
     **/
    public void startThrustEffect(){
	exhaust.setParticlesPerSec(20f);
	thrustAudioNode.play();
    }
    /**
     * Stops the thrust effect.
     **/
    public void stopThrustEffect(){
	exhaust.setParticlesPerSec(0f);
	thrustAudioNode.stop();
    }
    /**
     * Starts the turbo effect.
     **/
    public void startTurboEffect(){
	exhaust.setStartSize(1.5f);
    }
    /**
     * Stops the turbo effect.
     **/
    public void stopTurboEffect(){
	exhaust.setStartSize(1.0f);
    }
    public void stopAllForces(){
        rigidBodyControl.clearForces();
        ZLockControl zlock = node.getControl(ZLockControl.class);
        zlock.setLinearVelocity(Vector3f.ZERO);        
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Sets up the physics for this ship.
     **/ 
    private void setupPhysics(){
	setCollisionShapeSphere(1.50f*0.5f,1.0f);
	rigidBodyControl = (RigidBodyControl)physicsControl;
	rigidBodyControl.setFriction(0f);
	rigidBodyControl.setKinematic(false);
    }
    /**
     * Sets up the controllers that control this ship.
     **/
    private void setupControllers(){
	shipControl = new ShipControl(this);
	node.addControl(shipControl);
    }
    private void setupEffects(){
	exhaust = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
	Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
	mat_red.setTexture("m_Texture", assetManager.loadTexture("Effects/flame.png"));
	exhaust.setMaterial(mat_red);
	exhaust.setImagesX(2); exhaust.setImagesY(2); // 2x2 texture animation
	exhaust.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
	exhaust.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
	exhaust.getParticleInfluencer().setInitialVelocity(new Vector3f(0,-4,0));
	exhaust.setStartSize(1.0f);
	exhaust.setEndSize(0.1f);
	exhaust.setGravity(Vector3f.ZERO);
	exhaust.setLowLife(0.5f);
	exhaust.setHighLife(1.0f);
	exhaust.getParticleInfluencer().setVelocityVariation(0.1f);
	exhaust.setLocalTranslation(0, -1.26f, 0);
	exhaust.setParticlesPerSec(0f);
	node.attachChild(exhaust);
    }
    /**
     * Sets up the sounds for this ship.
     **/
    private void setupAudio(){
	thrustAudioNode = new AudioNode(assetManager, "Sound/Action/RocketThrusters.wav", false);
	thrustAudioNode.setLooping(true);  // activate continuous playing
	thrustAudioNode.setVolume(2);
	node.attachChild(thrustAudioNode);
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    /**
     * reset the ship to not move and rotate back to normal position
     * note: this does not set its position!
     */
    public void reset() {
        stopAllForces();
        rigidBodyControl.setPhysicsRotation(Matrix3f.IDENTITY);
    }

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
