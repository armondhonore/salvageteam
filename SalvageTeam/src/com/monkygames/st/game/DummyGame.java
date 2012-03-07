/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

import com.jme3.math.Vector3f;

/**
 * Methods used for game functions like ending the game.
 * @version 1.0
 */
public class DummyGame implements IGame{

// ============= Public Methods ============== //
    /**
     * Signals the game should end.
     **/
    public void endGame(){

    }
    public void reinit(){

    }
    public void setPaused(boolean on){

    }
    public boolean getPaused(){
	return false;
    }
    public void activateCollectionSound(){

    }
    public void startCollectEffect(Vector3f location){

    }
    public void saveScore(String name){

    }
    public void initMap(){

    }
    public void abortGame(){

    }
    public void setCursorVisible(boolean visible){
    }
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
