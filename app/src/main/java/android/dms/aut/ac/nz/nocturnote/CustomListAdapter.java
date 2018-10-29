package android.dms.aut.ac.nz.nocturnote;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the Note text
    private String[] noteArray;


    //CONSTRUCTOR
    public CustomListAdapter(Activity context, String[] notes) {
        super(context,R.layout.noteview_row, notes);
        this.context = context;
        this.noteArray = notes;
    }

    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.noteview_row, null, true);
        rowView.setPadding(0, 100,0,0);

        //Get references to objects in noteview_row
        TextView noteTextField = (TextView) rowView.findViewById(R.id.noteView);

        //Sets values of objects to values from the arrays.
        noteTextField.setText(noteArray[position]);

        return rowView;
    }

    //CONTINUE AT: ADD THE ACTUAL DATA


}
