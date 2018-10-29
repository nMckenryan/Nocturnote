package android.dms.aut.ac.nz.nocturnote;

import android.widget.TextView;

import java.io.Serializable;

//
// Class stores Note Data.
// Contains the Note index number, the String containing the text
//


public class Note implements Serializable {
    private int noteIndex;
    private String noteText;

    public Note(int index, String text) {
        this.noteIndex = index;
        this.noteText = text;
    }

    protected int findHighestIndex(Note[] noteCollection) {
        int highest = noteCollection.length;
        return highest;
    }

    //GETTERS AND SETTERS
    @Override
    public String toString() {
        return noteText;
    }

    public int getNoteIndex() {
        return noteIndex;
    }

    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
    }

}
