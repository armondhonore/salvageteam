/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.io;

// === java imports === //
import java.util.List;
import java.util.Vector;
// === db4o imports === //
import com.db4o.*;
// === st imports === //
import com.monkygames.st.game.Score;

/**
 * Manages saving and loading scores.
 * @version 1.0
 */
public class ScoreManager{

// ============= Class variables ============== //
    private ObjectContainer db;
// ============= Constructors ============== //
    public ScoreManager(){
	db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "scores.db4o");
    }
// ============= Public Methods ============== //
    /**
     * Closes the database.
     **/
    public void close(){
	db.close();
    }
    /**
     * Adds a score to the database.
     * @param score the score to add.
     **/
    public void addScore(Score score){
	try{
	    db.store(score);
	} catch(Exception e) {
	    
	}
    }
    /**
     * Returns all the scores.
     **/
    public Vector<Score> getScores(){
	Vector<Score> scoresV = new Vector<Score>();
	try{
	    List <Score> scores = db.query(Score.class);
	    for(int i = 0; i < scores.size(); i++){
		scoresV.add(scores.get(i));
	    }
	}catch(Exception e){}
	return scoresV;
    }
    /**
     * Returns the scores that are ordered by highest score.
     * @return a sorted collection of scores.
     **/
    public Vector<Score> getOrderedScores(){
	Vector<Score> scoresV = getScores();
	Vector<Score> scoresSortedV = new Vector<Score>();
	for(int i = 0; i < scoresV.size(); i++){
	    Score current = scoresV.elementAt(i);
	    if(scoresSortedV.size() == 0){
		scoresSortedV.add(current);
	    }else{
		boolean isAdded = false;
		for(int j = 0; j < scoresSortedV.size(); j++){
		    if(current.getTotal() > scoresSortedV.elementAt(j).getTotal()){
			scoresSortedV.add(j,current);
			isAdded = true;
			break;
		    }
		}
		if(!isAdded){
		    scoresSortedV.add(current);
		}
	    }
	}
	return scoresSortedV;
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
