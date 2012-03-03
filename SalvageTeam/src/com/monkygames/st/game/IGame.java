/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

import com.jme3.math.Vector3f;

/**
 * Methods used for game functions like ending the game.
 * @version 1.0
 */
public interface IGame{

// ============= Public Methods ============== //
    /**
     * Signals the game should end.
     **/
    public void endGame();
    public void reinit();
    public void setPaused(boolean on);
    public boolean getPaused();
    public void activateCollectionSound();
    /**
     * Starts a new collection effect.
     * @param location the location of the effect.
     **/
    public void startCollectEffect(Vector3f location);
    /**
     * Saves the current score with the specified player name.
     * @param name the name of the player.
     **/
    public void saveScore(String name);
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
