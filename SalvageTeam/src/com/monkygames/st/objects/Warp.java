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
 * A warp point model.
 * TODO sparly effect.
 * @version 1.0
 */
public class Warp extends Model{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public Warp(AppStateManager stateManager, Node node){
	super(stateManager);
	setNode(node);
	//loadNode("Models/warp/Billboard05.j3o");
	setBoundingSphere(5.9f/2f);
	//setCollisionShapeSphere(5.9f/2f,0);
	//setCollisionShapeSphere(0.95f,1.0f);
	//physicsControl.setFriction(0f);
	//physicsControl.setKinematic(false);
    }
// ============= Public Methods ============== //

// ============= Protected Methods ============== //
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
