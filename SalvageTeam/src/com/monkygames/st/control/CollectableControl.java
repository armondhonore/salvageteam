/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.control;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.monkygames.st.objects.Collectable;

/**
 * Handles Collisions between a ship and a collectable.
 * @version 1.0
 */
public class CollectableControl extends RigidBodyControl implements PhysicsCollisionListener{

// ============= Class variables ============== //
    private Collectable collectable;
// ============= Constructors ============== //
    public CollectableControl (Collectable collectable){
	this.collectable = collectable;
	//bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    public void collision(PhysicsCollisionEvent event) {
System.out.println("NodeA = "+event.getNodeA()+" NodeB = "+event.getNodeB());
	// the only object that can collide with this is the ship
	//TODO update score
	//System.out.println("Score updated by "+collectable.getValue());
	//TODO upate effects
	//TODO remove 
	//collectables.
	/*
	if ( event.getNodeA().getName().equals("player") ) {
	    final Node node = event.getNodeA();
	} else if ( event.getNodeB().getName().equals("player") ) {
	    final Node node = event.getNodeB();
	}
	*/
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
