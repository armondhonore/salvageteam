/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.control;

// === jme imports === //
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
// === st imports === //
import com.monkygames.st.objects.Ship;
import com.monkygames.st.utils.EffectTimer;
import com.monkygames.st.utils.IEffectTimer;

/**
 * Controls the ship's behaviour.
 * @version 1.0
 */
public class ShipControl extends AbstractControl implements IEffectTimer{

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
/**
 * The timer information for the turbo effect.
 **/
public EffectTimer effectTimer;

// ============= Constructors ============== //
/**
 * Creates a new Ship Control for controlling ship behaviour.
 * @param ship the ship to be controlled.
 **/
public ShipControl(Ship ship){
    this.ship = ship;
    effectTimer = new EffectTimer(1*1000,0.5f,this);
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
    stopTurbo();
}
/**
 * Starts the turbo on the ship.
 **/
public void startTurbo(){
    isTurboing = true;
    ship.startTurboEffect();
    effectTimer.activate();
}
/**
 * Stops the turbo on the ship.
 **/
public void stopTurbo(){
    isTurboing = false;
    ship.stopTurboEffect();
    effectTimer.deactivate();
}
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    protected void controlUpdate(float tpf) {
	// TODO add acceleration
	effectTimer.updateTime();
	ship.thrust(isThrusting, isTurboing, tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }
    public Control cloneForSpatial(Spatial spatial) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    public void timeOut() {
	//TODO turn off turbo effect
	stopTurbo();
	System.out.println("ShipControl isTimeOut");
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
