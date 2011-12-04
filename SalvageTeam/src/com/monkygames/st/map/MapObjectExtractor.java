/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.map;

// === java imports === //
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import java.util.Vector;
// === jme imports === //
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.monkygames.st.objects.Trash;
import com.monkygames.st.objects.Warp;

/**
 * Handles instantiating objects based on the model found in the scene.
 * Note, this relies on the user data presented in the graphics file.
 * For now, the following holds true for the user data:<br>
 * TrashBin - a Trash Object will be created
 * Bounds - a static physics object will be added to the node for collisions only.
 * @version 1.0
 */
public class MapObjectExtractor{

// ============= Class variables ============== //
    private Node scene;
    private AppStateManager appStateManager;
    private BulletAppState bulletAppState;
    private Node rootNode;
    public Vector<Trash> trashV;
    public Warp warp;
// ============= Constructors ============== //
    /**
     * Generates the necessary objects based on the user data.
     **/
    public MapObjectExtractor(Node scene, AppStateManager appStateManager, BulletAppState bulletAppState, Node rootNode){
	this.scene = scene;
	this.appStateManager = appStateManager;
	this.bulletAppState = bulletAppState;
	this.rootNode = rootNode;
	trashV = new Vector<Trash>();	

	checkObjects();
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void checkObjects(){
	for(int i = 0; i < scene.getQuantity(); i++){
	    Spatial s = scene.getChild(i);
	    if(s instanceof Node){
		String type = s.getUserData("Type");
		if(type == null){
		    continue;
		}
		//if(type.equals("TrashBin")){
		    //createTrashObject((Node)s);
		if(type.equals("Collectables")){
		    createCollectableObjects((Node)s);
		}else if(type.equals("Bounds")){
		    createBoundsObject((Node)s);
		}else if(type.equals("Warp")){
		    createWarpObject((Node)s);
		}
	    }
	}
    }
    private void createCollectableObjects(Node node){
	for(int i = 0; i < node.getQuantity(); i++){
	    Spatial s = node.getChild(i);
	    if(s instanceof Node){
		Trash trash = new Trash(appStateManager,(Node)s);
		trashV.add(trash);
	    }
	}
    }
    private void createTrashObject(Node s){
	Trash trash = new Trash(appStateManager,s); 
	trashV.add(trash);
    }
    private void createBoundsObject(Node node){
	CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(node);
	RigidBodyControl landscape = new RigidBodyControl(sceneShape, 0);
	scene.addControl(landscape);
	bulletAppState.getPhysicsSpace().add(landscape);
	rootNode.attachChild(scene);
    }
    private void createWarpObject(Node node){
	warp = new Warp(appStateManager,node);
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
