/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.objects;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.JmeSystem;

/**
 * The base class that loads models.
 * @version 1.0
 */
public class Model{

// ============= Class variables ============== //
    protected static AssetManager assetManager;
    /**
     * Used for physics related objects.
     **/
    protected CollisionShape collisionShape;
    /**
     * Used for controlling the physics on this model.
     **/
    //protected RigidBodyControl physicsControl;
    protected PhysicsControl physicsControl;
    /**
     * The main node for this model.
     **/
    protected Node node;
    /**
     * The application statemanager.
     **/
    private AppStateManager stateManager;
    /**
     * Contains the 3D information on this model.
     **/
    protected Spatial geometry;
    /**
     * Node used for detecting collisions of collectable objects.
     **/
    protected Node collectionNode;
// ============= Constructors ============== //
    public Model(AppStateManager stateManager){
	this.stateManager = stateManager;
	if(Model.assetManager == null){
	    Model.assetManager = JmeSystem.newAssetManager(Thread.currentThread().getContextClassLoader().getResource("com/jme3/asset/Desktop.cfg"));
	}
	node = new Node();
    }
// ============= Public Methods ============== //
    /**
     * Returns the model's node.
     **/
    public Node getNode(){
	return node;
    }
    /**
     * Sets the starting location of the model.
     **/
    public void setStartingPosition(float x, float y, float z){
	if(physicsControl instanceof RigidBodyControl){
	    ((RigidBodyControl)physicsControl).setPhysicsLocation( new Vector3f(x,y,z) );
	}
    }
    /**
     * Sets the starting location of the model with non physics.
     **/
    public void setStartingPositionNonPhysics(float x, float y, float z){
	node.setLocalTranslation(x,y,z);
    }
    /**
     * Detaches any controls of this collectable.
     **/
    public void detach(){
	stateManager.getState(BulletAppState.class).getPhysicsSpace().remove(physicsControl);
    }
// ============= Protected Methods ============== //
   /**
     * Handles loading a single node from file and attaches to the root node of this model.
     * @param obj the path to the object.
     **/
    protected void loadNode(String obj){
        try{
            Spatial geometry = assetManager.loadModel(obj);
            node.attachChild(geometry);

            //node.setModelBound(new BoundingSphere());
            node.updateModelBound();

        } catch (Exception e) {
            node = null;
        }
    }
    protected void setNode(Node node){
	this.node = node;
    }
    /**
     * Sets the collision shape to a sphere with the specified radius.
     * @param radius the radius of the sphere.
     * @param the mass of the model.
     **/
    protected void setCollisionShapeSphere(float radius, float mass){
	collisionShape = new SphereCollisionShape(radius);
	physicsControl = new RigidBodyControl( collisionShape, mass);
	node.addControl(physicsControl);
	// attach to physics state in order for physics to take effect.
	stateManager.getState(BulletAppState.class).getPhysicsSpace().add(physicsControl);
    }
    /**
     * Sets the node for detecting collisions between the model and collectables.
     * @param radius the size of the collision sphere.
     **/
    protected void setCollectableShapeSphere(float radius){
	SphereCollisionShape collectableShape = new SphereCollisionShape(radius);
	physicsControl = new GhostControl(collectableShape);
	//collectionNode = new Node();
	node.addControl(physicsControl);
	stateManager.getState(BulletAppState.class).getPhysicsSpace().add(physicsControl);
    }

    protected void setBoundingSphere(float radius){
	node.setModelBound(new BoundingSphere());
    }
/*
    public void setCollisionListener(PhysicsCollisionListener collisionListener){
	stateManager.getState(BulletAppState.class).getPhysicsSpace().addCollisionListener(collisionListener);
    }
*/
// ============= Private Methods ============== //
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
