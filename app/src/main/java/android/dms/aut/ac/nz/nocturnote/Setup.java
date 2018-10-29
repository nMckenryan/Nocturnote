package android.dms.aut.ac.nz.nocturnote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Setup extends AppCompatActivity {
    protected SecurityOptions sO;
    private EditText passwordEnter;
    private EditText passwordConfirm;
    private TextView passCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sO = new SecurityOptions(this);
        passCheck = (TextView) findViewById(R.id.passCheck);
    }

    public void passwordSet(View v) {

        try {
            passwordEnter = (EditText) findViewById(R.id.passEntry);
            passwordConfirm = (EditText) findViewById(R.id.passEntry2);

            String entered = passwordEnter.getText().toString();
            String confirm = passwordConfirm.getText().toString();

            if (entered.isEmpty() || confirm.isEmpty()) {
                passCheck.setText("Enter a Password!");
            } else if (!entered.equals(confirm)) {
                passCheck.setText("Passwords do not match!");
            } else if (confirm.length() <= 3 && entered.length() <= 3){
                passCheck.setText("Passwords must be longer than 3 characters!");
            } else {

                //Saving password to shared preferences.
                SharedPreferences setting = getSharedPreferences("SETTING_Infos", 0);
                setting.edit().putString("PWord", sO.encryptPass(confirm)).apply();
                System.out.println("SETPASS: " + setting.getString("PWord", ""));
                //NORMAL SET: //setting.edit().putString("PWord", confirm).apply();
                entered = null;
                confirm = null;
                passCheck.setText("Password Set!");
                //TAKES TO GATEKEEP
                startActivity(new Intent(this, NocturnoteGatekeep.class));
            }
        } catch(Exception err) {
            System.err.println(err);
        }
    }
}
