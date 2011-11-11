/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.actions;

import com.monkygames.st.objects.Ship;

/**
 * Contains base information like isPaused for handling user input.
 * @version 1.0
 */
public class BaseInput {

// ============= Class variables ============== //
    /**
     * True if puased and false otherwise.
     **/
    protected boolean isPaused = false;
    /**
     * The ship to be controlled.
     **/
    protected Ship ship;
// ============= Constructors ============== //
    public BaseInput(Ship ship){
	this.ship = ship;
    }
// ============= Public Methods ============== //
    /**
     * Sets the state of the input, if paused, then input should be limited.
     * @param isPaused true for paused and false otherwise.
     **/
    public void setPaused (boolean isPaused) {
	this.isPaused = isPaused;
    }
    public void togglePaused (){
	this.isPaused = !this.isPaused;
    }
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
