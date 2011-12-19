/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.control;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 * Locks the z-axis when moving.
 * @version 1.0
 */
public class ZLockControl extends RigidBodyControl implements PhysicsTickListener {
 

// ============= Class variables ============== //
    Vector3f linearVelocityVector;
// ============= Constructors ============== //
    public ZLockControl(float mass) {
        super(mass);
    }
    public ZLockControl(CollisionShape shape, float mass){
	super(shape,mass);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        space.addTickListener(this);
    }
 
    @Override
    public void prePhysicsTick(PhysicsSpace space, float f) {
        linearVelocityVector = getLinearVelocity();
        linearVelocityVector.z = 0;
        setLinearVelocity(linearVelocityVector);
    }
 
    @Override
    public void physicsTick(PhysicsSpace space, float f) {
 
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
