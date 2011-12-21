/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.objects;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.monkygames.st.control.ShipControl;

/**
 * A ship model.
 * @version 1.0
 */
public class Ship extends Model{

// ============= Class variables ============== //
/**
 * Controls the ship behaviour except for phys.
 **/
public ShipControl shipControl;
private RigidBodyControl rigidBodyControl;
/**
 * Controls the exhaust.
 **/
private ParticleEmitter exhaust;
// ============= Constructors ============== //
    public Ship(AppStateManager stateManager){
	super(stateManager);
	//loadNode("Models/ships/default.j3o");
        loadNode("Models/ships/3/spaceship.j3o");
        //node.rotate(0, -FastMath.PI/2f, 0f);
        //geometry.rotate(FastMath.PI/2f, FastMath.PI/2f, 0f);
	setCollisionShapeSphere(1.50f*0.5f,1.0f);
	rigidBodyControl = (RigidBodyControl)physicsControl;
	//setCollectableShapeSphere(0.96f);
	rigidBodyControl.setFriction(0f);
	rigidBodyControl.setKinematic(false);
	
	shipControl = new ShipControl(this);
	node.addControl(shipControl);
	//physics = new Physics();

	setupEffects();
    }
// ============= Public Methods ============== //
    /**
     * Rotates the object clockwise.
     **/
    public void rotateRight(){
	rigidBodyControl.setAngularVelocity( new Vector3f(0f,0f,-4f));
	//rigidBodyControl.setAngularVelocity( new Vector3f(-4f,-4f,0f));
    }
    /**
     * Rotates the object counter-clockwise.
     **/
    public void rotateLeft(){
	rigidBodyControl.setAngularVelocity( new Vector3f(0f,0f,4f));
	//rigidBodyControl.setAngularVelocity( new Vector3f(4f,4f,0f));
    }
    /**
     * Stops rotating regardless the direction of rotation.
     **/
    public void rotateStop(){
	rigidBodyControl.setAngularVelocity( new Vector3f(0f,0f,0.0f));
    }
    public void thrust(boolean isThrusting, float tpf){
	Vector3f forwardDir = node.getLocalRotation().getRotationColumn(1);
	forwardDir.normalize();
	forwardDir.mult( new Vector3f(-1f,-1f,-1f) );
	//float force = physics.calcThrustForce(isThrusting,tpf);
	float force = 3f;
	forwardDir.mult(force,forwardDir);
	if(isThrusting){
	    rigidBodyControl.applyCentralForce(forwardDir);
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
    }
    /**
     * Stops the thrust effect.
     **/
    public void stopThrustEffect(){
	exhaust.setParticlesPerSec(0f);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
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
    //exhaust.setLocalTranslation(0, -1.66f, 0);
    exhaust.setLocalTranslation(0, -1.26f, 0);
    exhaust.setParticlesPerSec(0f);
    node.attachChild(exhaust);

}
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
