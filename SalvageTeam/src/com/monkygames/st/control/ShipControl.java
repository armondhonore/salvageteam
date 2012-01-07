/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.monkygames.st.objects.Ship;

/**
 * Controls the ship's behaviour.
 * @version 1.0
 */
public class ShipControl extends AbstractControl{

// ============= Class variables ============== //
/**
 * The ship to be controlled.
 **/
private Ship ship;
/**
 * True if the ship is thrusting and false otherwise.
 **/
private boolean isThrusting;
/**
 * True if the ship is in turbo mode and false otherwise.
 **/
private boolean isTurboing;

// ============= Constructors ============== //
/**
 * Creates a new Ship Control for controlling ship behaviour.
 * @param ship the ship to be controlled.
 **/
public ShipControl(Ship ship){
    this.ship = ship;
}
// ============= Public Methods ============== //
/**
 * Starts the thruster on the ship.
 **/
public void startThrust(){
    isThrusting = true;
    ship.startThrustEffect();
}
/**
 * Stops the thruster on the ship.
 **/
public void stopThrust(){
    isThrusting = false;
    ship.stopThrustEffect();
}
/**
 * Starts the turbo on the ship.
 **/
public void startTurbo(){
    isTurboing = true;
    ship.startTurboEffect();
}
/**
 * Stops the turbo on the ship.
 **/
public void stopTurbo(){
    isTurboing = false;
    ship.stopTurboEffect();
}
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    protected void controlUpdate(float tpf) {
	// do the work here
	//PhysicsControl = spatial.getControl(CharacterControl.class);
	// TODO add acceleration
	ship.thrust(isThrusting, isTurboing, tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }
    public Control cloneForSpatial(Spatial spatial) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
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
