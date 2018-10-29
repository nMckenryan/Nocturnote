package android.dms.aut.ac.nz.nocturnote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NocturnoteGatekeep extends AppCompatActivity {
    private SecurityOptions secOps;
    private EditText enterPass;
    private TextView passwordStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nocturnote_gatekeep);

        secOps = new SecurityOptions(this);
        System.out.println("ISPASSWORDSET?" + secOps.checkIfPassSet(this));
        passwordStatus = (TextView)findViewById(R.id.passwordStatus);
        enterPass = (EditText)findViewById(R.id.enterPass);
        hasPassword(); //Checks if password has been set
    }

    private void hasPassword() { //If as password has not been set, starts password setup
        if(secOps.checkIfPassSet(this) == false) {
            startActivity(new Intent(this, Setup.class));
        }
    }

    //Purges notes. uses security options method. (duplicate from mainmethod.)
    public void purgeNotes(View v) {
        secOps.setContext(this);
        secOps.purgeAllNotes(v, this);
    }

    //Delete a singular note. Used in MainActivity (delete button) and in Note Edit.
    public void deleteSingleNote(View v) {

    }

    //Checks if Password is same as the one saved.
    public void passwordCheck(View v) {
        try {
            passwordStatus.setText("Processing...");
            String attemptedPass = enterPass.getText().toString();

            SharedPreferences setting = getSharedPreferences("SETTING_Infos", 0);

            if (attemptedPass.isEmpty()) {
                passwordStatus.setText("Enter a Password!");
            }
            else if(attemptedPass.equals(secOps.decryptPass(setting.getString("PWord", "")))) {
                // Send to next page after 2 seconds, show green text update.
                passwordStatus.setTextColor(Color.parseColor("#228B22"));
                passwordStatus.setText("Password Correct! Accessing now...");
                Thread.sleep(2000);
                startActivity(new Intent(this, MainActivity.class));
            } else {
                passwordStatus.setTextColor(Color.parseColor("#FF0000"));
                passwordStatus.setText("Password Incorrect");
            }
        } catch(Exception err) {
            passwordStatus.setText("Error Detected. Please try again.");
            System.out.println(err);
        }
    }
}
