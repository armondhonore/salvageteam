/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.Random;

/**
 * Handles the rotation for the trash.
 * @version 1.0
 */
public class TrashControl extends AbstractControl{


// ============= Class variables ============== //
    private float speed = 0.5f;
    private boolean isRotatingForward = true;
    public static Random speedGenerator;
// ============= Constructors ============== //
    public TrashControl(){}
    public TrashControl(Spatial spatial){
        setSpatial(spatial);
    }
// ============= Public Methods ============== //
    public void setSpeed(float speed){
	this.speed = speed;
    }
    public float getSpeed(){
	return speed;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    protected void controlUpdate(float tpf) {
	spatial.rotate(tpf*speed,0,tpf*speed);
    }
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    public Control cloneForSpatial(Spatial spatial) {
        if(TrashControl.speedGenerator == null){
            TrashControl.speedGenerator = new Random();
        }
        
	TrashControl control = new TrashControl();
	control.setSpeed(TrashControl.speedGenerator.nextFloat());
	control.setSpatial(spatial);
	return control;
    }
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
