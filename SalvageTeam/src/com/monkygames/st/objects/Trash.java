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
import com.jme3.scene.Spatial;

/**
 * A trash model.
 * TODO dust effect.
 * @version 1.0
 */
public class Trash extends Model{

// ============= Class variables ============== //
/**
 * Controls the exhaust.
 **/
private ParticleEmitter exhaust;
// ============= Constructors ============== //
    public Trash(AppStateManager stateManager){
	super(stateManager);
	loadNode("Models/trash/TrashBin.j3o");
	physicsControl.setMass(0);
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
