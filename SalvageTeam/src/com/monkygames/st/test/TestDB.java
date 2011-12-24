/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.test;

// === java imports === //
import java.util.Vector;
// === st imports === //
import com.monkygames.st.io.*;
import com.monkygames.st.game.Score;

/**
 * Tests the database.
 * @version 1.0
 */
public class TestDB{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public TestDB(){
	ScoreManager scoreManager = new ScoreManager();
	Vector<Score> scoresV = scoreManager.getScores();
	if(scoresV.size() == 0){
	    System.out.println("Creating scores and saving them");
	    System.out.println("Note: Run TestDB again to test for retreival of scores");
	    scoreManager.addScore(new Score(5));
	    scoreManager.addScore(new Score(10));
	    scoreManager.addScore(new Score(1));
	    scoreManager.addScore(new Score(0));
	    scoreManager.addScore(new Score(7));
	}else{
	    System.out.println("=== Unsorted Scores === ");
	    System.out.println(scoresV);
	    System.out.println("=== Sorted Scores === ");
	    System.out.println(scoreManager.getOrderedScores());
	}
	scoreManager.close();
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
    public static void main(String []args){
	TestDB testDB = new TestDB();
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
