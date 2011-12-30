/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.objects;

import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * A trash model.
 * TODO dust effect.
 * @version 1.0
 */
public class Trash extends Model implements Collectable{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Trash(AppStateManager stateManager,Node node){
	super(stateManager);
	setNode(node);
	setCollectableShapeSphere(0.298f*1.5f);

    }
// ============= Public Methods ============== //
    public Vector3f getLocation(){
        return node.getLocalTranslation();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
        /*
        debris = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
        Material debris_mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debris_mat.setTexture("Texture", assetManager.loadTexture("Effects/Shockwave.png"));
        debris.setMaterial(debris_mat);
        debris.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        debris.setStartColor(ColorRGBA.White);
        debris.setGravity(0, 0, 0);
        debris.getParticleInfluencer().setVelocityVariation(.60f);
        node.attachChild(debris);
        //debris.emitAllParticles();
        */
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
