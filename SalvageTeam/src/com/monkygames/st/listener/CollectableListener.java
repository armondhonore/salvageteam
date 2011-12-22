/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.listener;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Node;
import com.monkygames.st.game.Score;
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
    /**
     * The score for this game.
     **/
    private Score score;
// ============= Constructors ============== //
    public CollectableListener(Vector collectablesV, Node collectablesNode, Score score){
	this.collectablesV = (Vector<Collectable>)collectablesV;
	this.collectablesNode = collectablesNode;
	this.score = score;
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void collectableCollected(Node node){
	// find collectable
	for(int i = 0; i < collectablesV.size(); i++){
	    Collectable collectable = collectablesV.elementAt(i);
	    // find collectable
	    if(node == collectable.getNode()){
		// update score
		score.increaseScore(collectable.getValue());
		// TODO update HUD?
		//TODO upate effects
		// remove 
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
