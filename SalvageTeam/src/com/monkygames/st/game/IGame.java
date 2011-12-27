/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

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
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
