/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.effects;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * An effect when trash is collected.
 * @version 1.0
 */
public class TrashCollectionEffect{

// ============= Class variables ============== //
    private ParticleEmitter shockwave;
    private Node node;
    private AssetManager assetManager;
// ============= Constructors ============== //
    public TrashCollectionEffect(AssetManager assetManager, Vector3f loc){
        this.assetManager = assetManager;
	node = new Node();
        node.setLocalTranslation(loc);
	setupEffect();
    }
// ============= Public Methods ============== //
    public void startEffect(){
        shockwave.emitAllParticles();
    }
    public Node getNode(){
        return node;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void setupEffect(){
        shockwave = new ParticleEmitter("Shockwave", ParticleMesh.Type.Triangle, 10);
//        shockwave.setRandomAngle(true);
        //shockwave.setFaceNormal(Vector3f.UNIT_Y);
        shockwave.setStartColor(new ColorRGBA(.48f, 0.17f, 0.01f, (float) (.8f / 10f)));
        shockwave.setEndColor(new ColorRGBA(.48f, 0.17f, 0.01f, 0f));

        shockwave.setStartSize(0f);
        shockwave.setEndSize(7f);

        shockwave.setParticlesPerSec(0);
        shockwave.setGravity(0, 0, 0);
        shockwave.setLowLife(0.5f);
        shockwave.setHighLife(0.5f);
        shockwave.setImagesX(1);
        shockwave.setImagesY(1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/shockwave.png"));
        shockwave.setMaterial(mat);
        node.attachChild(shockwave);
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
