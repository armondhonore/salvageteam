/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

// === java imports === //
import java.io.Serializable;

/**
 * Player information.
 * @version 1.0
 */
public class Player implements Serializable{

// ============= Class variables ============== //
    /**
     * The name of the player.
     */
    private String name;
// ============= Constructors ============== //
    public Player(String name){
	this.name = name;
    }
// ============= Public Methods ============== //
    public String getName(){
	return name;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public String toString(){
	return name;
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
