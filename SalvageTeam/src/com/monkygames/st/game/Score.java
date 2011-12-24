/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

/**
 * Holds the score for the game.
 * @version 1.0
 */
public class Score{

// ============= Class variables ============== //
    /**
     * The total amount scored.
     **/
    private int total;
    /**
     * The time that this score was recorded.
     **/
    private long recordedTime;
    /**
     * Contains the time for this game.
     **/
    private Time time;
// ============= Constructors ============== //
    public Score(){
	this(0);
    }
    public Score(int total){
	this(total,0);
    }
    /**
     * Creates a score with the total score and amount of time remaining.
     * @param total the total score.
     * @param timeRemaining the amount of time remaining in milliseconds - 0 for count up otherwise count down.
     **/
    public Score(int total, int timeRemaining){
	this.total = total;
	recordedTime = 0;
	time = new Time(timeRemaining);
    }
// ============= Public Methods ============== //
    /**
     * Increases the score by the specified amount.
     * @param add the amount to increase.
     **/
    public void increaseScore(int add){
	total += add;
    }
    /**
     * Decreases the score by the specified amount.
     **/
    public void decreaseScore(int sub){
	total -= sub;
    }
    /**
     * Returns the total score.
     * @return the total score.
     **/
    public int getTotal(){
	return total;
    }
    /**
     * Sets the score's recorded time to the current time.
     **/
    public void setRecordedTime(){
	recordedTime = System.currentTimeMillis();
    }
    /**
     * Returns the recorded time.
     * @return the recorded time in milliseconds from the year 1970.
     **/
    public long getRecordedTime(){
	return recordedTime;
    }
    /**
     * Returns the time object for this score.
     * @return the time object.
     **/
    public Time getTime(){
	return time;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    public String toString(){
	return "Score["+total+"]";
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
