/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

/**
 * Holds the game time.
 * @version 1.0
 */
public class Time {
    //static final long serialVersionUID = -9017496812497237883L;

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
     * The total amount of time the game was paused.
     **/
    private long totalPausedTime;
    /**
     * The time the game was paused.
     **/
    private long pauseTime;
    /**
     * The amount of time left before no bonus is awareded.
     **/
    private long timeRemaining;
    /**
     * True if timer is counting down and false if counting up.
     **/
    private boolean isCountingDown;
    /**
     * Used for ending the game when the time expires.
     **/
    private IGame game;
    /**
     * True if the game has started and false otherwise.
     **/
    private boolean isStarted = false;

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
	this(timeRemaining,null);
    }
    public Time(long timeRemaining, IGame game){
	this.timeRemaining = timeRemaining;
	this.game = game;
	totalGameTime = 0;
	startGameTime = 0;
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
	//previousTime = startGameTime;
	isStarted = true;
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
	if(pauseTime > 0L){
	    long currentTime = System.currentTimeMillis();
	    // calculate the amount of time that was paused.
	    long timeDifference = currentTime - pauseTime;
	    // add to the total amount of time paused.
	    totalPausedTime += timeDifference;
	    // keep previous time instep 
        }
    }
    /**
     * Updates the current time and returns the string representation.
     * @return the current amount of time passed.
     **/
    public String updateTime(){
        if (game.getPaused()) {
            return ""; // if paused, do nothing
        }
        long currentTime = System.currentTimeMillis();
        long time = currentTime - startGameTime - totalPausedTime;
        if (totalPausedTime > 0L) {
            System.out.println("TOTALS = "+ currentTime+" - "+startGameTime+" - "+totalPausedTime);
        }
	String timeStr = "";
	if(isCountingDown){
	    time = timeRemaining - time;
	    if(time < 0L){
		time = 0L;
		// end game
		if(game != null && isStarted){
                    isStarted = false;
		    game.endGame();                    
		}
	    }
	}
	timeStr += time;
	int ind = timeStr.length() - 3;
	if (ind >= 0) {
	    timeStr = timeStr.substring(0, ind) + "." + timeStr.substring(ind);
	}
        totalGameTime = time;
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
    public void stop() {
        isStarted = false;
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
