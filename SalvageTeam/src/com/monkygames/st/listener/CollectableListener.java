/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.listener;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Node;
import com.monkygames.st.objects.Collectable;
import java.util.Vector;

/**
 * Handles Collisions between a ship and a collectable.
 * @version 1.0
 */
public class CollectableListener implements PhysicsCollisionListener{

// ============= Class variables ============== //
    private Vector<Collectable> collectablesV;
    private Node collectablesNode;
// ============= Constructors ============== //
    public CollectableListener(Vector collectablesV, Node collectablesNode){
	this.collectablesV = (Vector<Collectable>)collectablesV;
	this.collectablesNode = collectablesNode;
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void collectableCollected(Node node){
	// find collectable
	for(int i = 0; i < collectablesV.size(); i++){
	    Collectable collectable = collectablesV.elementAt(i);
	    if(node == collectable.getNode()){
		// found node
System.out.println("Collectable found "+collectable.getValue());
		//TODO update score
		//System.out.println("Score updated by "+collectable.getValue());
		//TODO upate effects
		//TODO remove 
		collectablesNode.detachChild(node);
		collectable.detach();
		collectablesV.removeElementAt(i);
		break;
	    }
	}
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    public void collision(PhysicsCollisionEvent event) {
	// TODO note, this is a hack and should be changed later but I have limited time so its here for now...
	if(event.getNodeA().getName() != null && event.getNodeA().getName().equals("Models/trash/TrashBin.j3o")){
	    collectableCollected((Node)event.getNodeA());
	}else if(event.getNodeB().getName() != null && event.getNodeB().getName().equals("Models/trash/TrashBin.j3o")){
	    collectableCollected((Node)event.getNodeB());
	}
//System.out.println("NodeA = "+event.getNodeA()+" NodeB = "+event.getNodeB());
	// the only object that can collide with this is the ship
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
