package android.dms.aut.ac.nz.nocturnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SecurityOptions mSecOps;

    List<Note> testArray;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSecOps = new SecurityOptions(this);
        createNote();
        testArray = mSecOps.loadNotes();


        CustomListAdapter cla = new CustomListAdapter(this, noteToArray(testArray));
        listView = (ListView) findViewById(R.id.noteList);
        listView.setAdapter(cla);
        //noteArray[0] = new Note(0, "Goon");
        //populateNotes();
    }

    //convert List of Notes to string[] for use with CustomListAdapter.
    public String[] noteToArray(List<Note> noteArr) {
        String[] sA = new String[noteArr.size()];
        int i = 0;
        for(Note note : noteArr) {
            sA[i] = note.toString();
            i++;
        }
        return sA;
    }

    //Purges notes in the mainactivity. uses security options method.
    public void purgeMainNotes(View v) {
        mSecOps.setContext(this);
        mSecOps.purgeAllNotes(v, this);
    }

//    public Note[] retrieveNotes() {
//
//    }

    //Starts Note Entry Activity.
    public void toNoteEntry(View v) {
        //startActivity(new Intent(this, NoteEntry.class));

    }

    public void createNote() {
        //startActivity(new Intent(this, NoteEntry.class));
        mSecOps.saveNote(new Note(1,"ExampleNote"));
    }


    //On Resume, the user is returned to the login screen.
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onPause() {
        super.onPause();

    }


}
