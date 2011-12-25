/*
 * This class implements a possible score store to persist scores to disc
 * using java's native file io
 */
package com.monkygames.st.io;

import java.util.ArrayList;
import com.monkygames.st.game.Score;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author geekdenz
 */
public class ScoreStore {
    private List<Score> scores;
    private File file;
    private FileInputStream fileInput;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput = null;
    public ScoreStore() {
        this("scores.jio"); // using standard java io
    }
    public ScoreStore(String fileName) {
        file = new File(fileName);
        try {
            fileInput = new FileInputStream(file);
            objectInput = new ObjectInputStream(fileInput);
            scores = (ArrayList<Score>) objectInput.readObject();
            objectInput.close();
        } catch (Exception e) {
            System.out.println("scores file not found, therefore creating new scores store");
            
            scores = new ArrayList<Score>();
        }
    }
    public void add(Score score) {
        this.scores.add(score);
    }
    public void persist() {
        try {
            if (objectOutput == null) { // if we don't already have the stream
                FileOutputStream fout;
                    fout = new FileOutputStream(file);
                    objectOutput = new ObjectOutputStream(fout);
            } else {
                objectOutput.reset(); // need to reset to not write 2 lists
            }
            Collections.sort(scores); // only need to sort when saved
            // only store top 100
            scores = scores.subList(0, 99);
            objectOutput.writeObject(scores);
            objectOutput.flush();
            objectOutput.close();
        } catch (Exception ex) {
            System.err.println("error writing to game store file");
            System.err.println(ex);
        }
    }
}
