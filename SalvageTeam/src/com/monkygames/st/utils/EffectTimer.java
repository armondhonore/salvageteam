/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.utils;

/**
 * Used for tracking the amount of time an effect has left.
 * @version 1.0
 */
public class EffectTimer{

// ============= Class variables ============== //
    /**
     * The amount of time for this effect.
     */
    private long time;
    /**
     * The time left of this effect.
     */
    private long timeLeft;
    /**
     * The previous time.
     */
    private long prevTime;
    /**
     * The current time.
     */
    private long curTime;
    /**
     * The recharge rate for the time.
     */
    private float rechargeRate;
    /**
     * True if timer is countdown and false for recharging.
     */
    private boolean isCountdowning;
    /**
     * The interface for this timer.
     */
    private IEffectTimer iEffectTimer;
// ============= Constructors ============== //
    /**
     * Creates a new effect timer.
     * @param time the max amount time for this timer.
     * @param rechargeRate the rate at which the time recharges.
     */
    public EffectTimer(int time, float rechargeRate, IEffectTimer iEffectTimer){
	this.time = time;
	this.timeLeft = time;
	this.rechargeRate = rechargeRate;
	this.iEffectTimer = iEffectTimer;
	this.isCountdowning = false;
    }
// ============= Public Methods ============== //
    /**
     * Sets the timer to active and starts to count down.
     **/
    public void activate(){
	isCountdowning = true;
    }
    /**
     * The timer stops counting down and counts up until max.
     */
    public void deactivate(){
	isCountdowning = false;
    }
    /**
     * The percentage of time left.
     * @return the percentage of time left.
     */
    public float timeLeft(){
	return (float)timeLeft/(float)time;
    }
    public void updateTime(){
	curTime = System.currentTimeMillis();
	if(isCountdowning){
	    timeLeft -= (curTime - prevTime);
	    if(timeLeft <= 0){
		// update interface
		iEffectTimer.timeOut();
	    }
	}else{
	    timeLeft += (curTime - prevTime)*rechargeRate;
	    if(timeLeft >= time){
		timeLeft = time;
	    }
	}
	prevTime = curTime;
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
