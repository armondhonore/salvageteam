/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.game;

import java.io.Serializable;

/**
 * Holds the score for the game.
 * @version 1.0
 */
public class Score implements Serializable, Comparable<Score> {

// ============= Class variables ============== //
    static final long serialVersionUID = -5841840990492331554L;
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
    private transient Time time;
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
    public Score(int total, long timeRemaining){
	this(total,timeRemaining,null);
    }
    /**
     * Creates a score with the total score and amount of time remaining.
     * @param total the total score.
     * @param timeRemaining the amount of time remaining in milliseconds - 0 for count up otherwise count down.
     **/
    public Score(int total, long timeRemaining, IGame game){
	this.total = total;
	recordedTime = 0;
	time = new Time(timeRemaining,game);
    }
// ============= Public Methods ============== //
    /**
     * Increases the score by the specified amount.
     * @param add the amount to increase.
     **/
    public void increaseScore(int add){
        //if (time.getTotalGameTime() > 500) { // TODO: make this less of a hack, reason for this is that score gets increased from last collistion in last game
            total += add;
        //}
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

    public int compareTo(Score t) {
        return t.getTotal() - this.getTotal();
    }

    public void reset() {
        this.total = 0;
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
