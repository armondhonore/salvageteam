/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.st.test;

import com.monkygames.st.game.Score;
import com.monkygames.st.io.ScoreStore;

/**
 *
 * @author geekdenz
 */
public class TestJavaIO {
    
    public static void main(String []args){
        ScoreStore ss = new ScoreStore();
        for (int i = 0; ++i < 201;) {
            Score score = new Score(i);
            ss.add(score);
        }
        ss.persist();
        System.out.println(ss); // the second time around it should print previous scores
    }
    
}
