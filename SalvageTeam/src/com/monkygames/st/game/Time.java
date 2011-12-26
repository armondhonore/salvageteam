/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

import java.io.Serializable;

/**
 * Holds the game time.
 * @version 1.0
 */
public class Time implements Serializable {
    static final long serialVersionUID = -9017496812497237883L;

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
     * Used for calculating the amount of time passed.
     **/
    private long previousTime;
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
    /**
     * The amount of time left before no bonus is awareded.
     **/
    private long timeRemaining;
    /**
     * True if timer is counting down and false if counting up.
     **/
    private boolean isCountingDown;

// ============= Constructors ============== //
    public Time(){
	this(0);
    }
    /**
     * Creates a new time class with the specified time remaining.
     * If the timeRemaining is set to 0, than this timer counts up, otherwise, counts down.
     * @param timeRemaining the amount of time remaining before no bonus is awarded.
     **/
    public Time(long timeRemaining){
	this.timeRemaining = timeRemaining;
	totalGameTime = 0;
	startGameTime = 0;
	previousTime = 0;
	endGameTime = 0;
	pauseTime = 0;
	totalPausedTime = 0;
	if(timeRemaining == 0){
	    isCountingDown = false;
	}else{
	    isCountingDown = true;
	}
    }
// ============= Public Methods ============== //
    /**
     * Sets the start game time to the current time in milliseconds from the year of 1970.
     **/
    public void setStartGameTime(){
	startGameTime = System.currentTimeMillis();
	previousTime = startGameTime;
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
	if(pauseTime > 0){
	    long currentTime = System.currentTimeMillis();
	    // calculate the amount of time that was paused.
	    long timeDifference = currentTime - pauseTime;
	    // add to the total amount of time paused.
	    totalPausedTime += timeDifference;
	    // keep previous time instep 
	    previousTime -= timeDifference;
	}
    }
    /**
     * Updates the current time and returns the string representation.
     * @return the current amount of time passed.
     **/
    public String updateTime(){
        long time = System.currentTimeMillis() - previousTime;
	String timeStr = "";
	if(isCountingDown){
	    time = timeRemaining - time;
	    if(time < 0){
		time = 0;
	    }
	}
	timeStr += time;
	int ind = timeStr.length() - 3;
	if (ind >= 0) {
	    timeStr = timeStr.substring(0, ind) + "." + timeStr.substring(ind);
	}
	return timeStr;
    }
    /**
     * Returns the total game time played in milliseconds.
     * @return the total amount of game time played.
     **/
    public long getTotalGameTime(){
	return totalGameTime;
    }
    /**
     * Returns the start time.
     **/
    public long getStartGameTime(){
	return startGameTime;
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
