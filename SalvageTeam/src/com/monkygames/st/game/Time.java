/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

/**
 * Holds the game time.
 * @version 1.0
 */
public class Time{

// ============= Class variables ============== //
    /**
     * The total amount of time played.
     **/
    private long totalGameTime;
    /**
     * The time the game started.
     **/
    private long startGameTime;
    /**
     * The time the game ended.
     **/
    private long endGameTime;
    /**
     * The time the game was paused.
     **/
    private long pauseTime;
    /**
     * The total amount of time the game was paused.
     **/
    private long totalPausedTime;

// ============= Constructors ============== //
    public Time(){
	totalGameTime = 0;
	startGameTime = 0;
	endGameTime = 0;
	pauseTime = 0;
	totalPausedTime = 0;
    }
// ============= Public Methods ============== //
    /**
     * Sets the start game time to the current time in milliseconds from the year of 1970.
     **/
    public void setStartGameTime(){
	startGameTime = System.currentTimeMillis();
    }
    /**
     * Sets the end game time to the current time in milliseconds from the year of 1970 and calculates the total amount of time playing the game excluding paused time.
     **/
    public void setEndGameTime(){
	endGameTime = System.currentTimeMillis();
	totalGameTime = endGameTime - startGameTime - totalPausedTime;
    }
    /**
     * Sets the pauseTime to the current time in milliseconds.
     **/
    public void setPauseGameTime(){
	pauseTime = System.currentTimeMillis();
    }
    public void setUnpaused(){
	totalPausedTime += System.currentTimeMillis() - pauseTime;
    }
    /**
     * Returns the total game time played in milliseconds.
     * @return the total amount of game time played.
     **/
    public long getTotalGameTime(){
	return totalGameTime;
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
