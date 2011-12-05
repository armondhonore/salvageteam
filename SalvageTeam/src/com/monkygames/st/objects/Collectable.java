/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.objects;

// === jme imports === //
import com.jme3.scene.Node;

/**
 * An interface for a model that can be collectable.
 * @version 1.0
 */
public interface Collectable {

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Returns the value of this collectable.
     * @return the value of this collectable.
     **/
    public int getValue();
    /**
     * Returns the node associated with this collectable.
     * @return the node that contains the 3d information of this collectable.
     **/
    public Node getNode();
    /**
     * Detaches any controls of this collectable.
     **/
    public void detach();
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
