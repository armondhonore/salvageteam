/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.objects;

import com.jme3.app.state.AppStateManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * A trash model.
 * TODO dust effect.
 * @version 1.0
 */
public class Trash extends Model implements Collectable{

// ============= Class variables ============== //
/**
 * Controls the exhaust.
 **/
private ParticleEmitter exhaust;
// ============= Constructors ============== //
    public Trash(AppStateManager stateManager,Node node){
	super(stateManager);
	setNode(node);
	//loadNode("Models/trash/TrashBin.j3o");
	setBoundingSphere(0.298f/2f);	
	//setCollisionShapeSphere(0.298f/2f,0);
	//physicsControl.setFriction(0f);
	//physicsControl.setKinematic(false);
    }
// ============= Public Methods ============== //

// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    /**
     * Returns the value of this collectable.
     * @return the value of this collectable.
     **/
    public int getValue(){
	//TODO hard coded for now, but later change to dynamic.
	return 10;
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
