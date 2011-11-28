/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.appstate;

// === java imports === //
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import java.util.Vector;
// === jme imports === //
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
// === st imports === //
import com.jme3.collision.CollisionResults;
import com.monkygames.st.objects.Collectable;
import com.monkygames.st.objects.Ship;

/**
 * For detecting and handling the collection of objects.
 * @version 1.0
 */
public class CollectAppState extends AbstractAppState{

// ============= Class variables ============== //
    private SimpleApplication app;
    /**
     * Contains the collectables that can be picked up by ship.
     **/
    private Vector<Collectable> collectablesV;
    /**
     * Used to detect if this ship has collidated with any collectable.
     **/
    private Ship ship;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setCollectableObjects(Vector<Collectable> collectablesV){
	this.collectablesV = collectablesV;
    }
    public void setShip(Ship ship){
	this.ship = ship;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
      super.initialize(stateManager, app); 
      this.app=(SimpleApplication)app;
      //app.doSomething();             // call some methods elsewhere
    }
    @Override
    public void update(float tpf) {
	// check that the ship has run into a collectable.
	CollisionResults results = new CollisionResults();
	for(int i = 0; i < collectablesV.size(); i++){
	    ship.getNode().collideWith(collectablesV.elementAt(i).getNode(),results);
	    if(results.size() > 0){
		//TODO update score
		//TODO upate effects
		//TODO remove 
	    }
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
