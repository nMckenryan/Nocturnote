package android.dms.aut.ac.nz.nocturnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class NoteEntry extends AppCompatActivity {
    private EditText entryText;
    private Note newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        entryText = (EditText)findViewById(R.id.textEntryBlock);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_entry);
    }

    //Used for back button. returns to main menu.
    protected void backToMain(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    //TODO: LOOK AT https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc for tips
    //Saving Entry to file system.
    public void saveEntry() {

        //newNote = new Note(entryText.getText().toString());
        //TODO: Save text and pop it into file system with unique ID. ConcatID to file? surrounded by unique characters?
    }

    //Creates note, if it does not exist.
    public void createNote(String noteTxt){

        //TODO: Run through note array, check for highest num. return that +1;
    }

    //Change label on top of Note entry, (Edit Note or Create Note)
    public void textLabelChange() {
        //TODO: Simple if else loop deciding note text.
    }

}
