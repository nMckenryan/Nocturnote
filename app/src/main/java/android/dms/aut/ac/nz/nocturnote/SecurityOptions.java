package android.dms.aut.ac.nz.nocturnote;

/* @author Nigel Mckenzie-Ryan
// This class holds the security information for the app and associated functions
*/

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityOptions {
    protected boolean passwordSet;
    private Cipher cipher;
    private byte[] cipheredPass;
    private static final String BASIC_KEY = "ADF6s3xD5SgdsEfC";
    private static final String CRYPTOALGO = "AES/ECB/PKCS5Padding";

    private Context context;

    protected SharedPreferences sharePrefs;
    protected SharedPreferences.Editor prefsEditor;
    protected Gson gson;
    protected String json;

    protected ObjectOutputStream objectOut;
    protected String filePath;

    protected final String NOTELIST = "NOTELIST";

    public SecurityOptions(Context cont) { //Sets initial, default password for first use.
        passwordSet = false;
        context = cont;
        filePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/NOCTURNOTE_NOTES";
        //SharedPreferences sharePrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences sharePrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);

        prefsEditor = sharePrefs.edit();
        gson = new Gson();
    }


    private void toastMaker(Activity acti, String tMsg) { //Simplifies the ToastMaker function
        Toast.makeText(acti, tMsg, Toast.LENGTH_SHORT).show();
    }

    //Sets context.
    public void setContext(Context cont) {
        context = cont;
    }

    public void returnToLock(View v){
        context.startActivity(new Intent(context, NocturnoteGatekeep.class));
    }

    /*
    // PASSWORD SETTING FUNCTIONS
    */

    //Generates cryptographic key to encrypt password using the Advanced Encryption Algorithm
    public static Key genKey() throws Exception {
        Key key = new SecretKeySpec(BASIC_KEY.getBytes(), "AES");
        return key;
    }

    //Encrypts password using Secret Key
    public String encryptPass(String password) throws Exception {
        cipher = Cipher.getInstance(CRYPTOALGO);
        Key sKey = genKey();
        cipher.init(Cipher.ENCRYPT_MODE, sKey);
        cipheredPass = cipher.doFinal(password.getBytes("UTF-8"));
        String encPass = Base64.encodeToString(cipheredPass, Base64.DEFAULT);
        return encPass;
    }

    //Decrypts password for verification purposes.
    public String decryptPass(String pass)throws Exception {
        Key key = genKey();
        cipher = Cipher.getInstance(CRYPTOALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedByteVal64 = Base64.decode(pass, Base64.DEFAULT);
        byte[] decryptedByteVal= cipher.doFinal(decryptedByteVal64);
        return new String(decryptedByteVal, "utf-8");
    }


    public boolean checkIfPassSet(Activity activ) {
        SharedPreferences setting = activ.getSharedPreferences("SETTING_Infos", 0);
        String pass = setting.getString("PWord", "");
        if(pass.equals(null) || pass.equals("")) {
            return false;
        } else {
            return true;
        }
    }


    //Deletes Password from SharedPreferences
    public void deletePassword(Activity activ) {
        SharedPreferences setting = activ.getSharedPreferences("SETTING_Infos", 0);
        SharedPreferences.Editor editor = setting.edit();
        try {
            editor.remove("PWord");
            editor.commit();
            System.out.println("Password Deleted!");
        } catch(Exception err) {
            System.err.println("COULD NOT DELETE PASSWORD: "+ err);
        }
    }

    /*
    // NOTE SETTING FUNCTIONS
    */

    //TODO: check gson function. does it save to list?
    protected void saveNote(Note note) {
        try {
            json = gson.toJson(note);
            prefsEditor.putString(NOTELIST, json);
            SharedPreferences sharePrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
            prefsEditor.apply();
            System.out.println(sharePrefs.getString(NOTELIST, ""));
        } catch (Exception e) {
            System.err.println("Note could not be saved.");
            e.printStackTrace();
        }
    }

    //Retrieve all notes. Save as a List
//    public List<Note> loadNotes() {
    public ArrayList<Note> loadNotes() {
        ArrayList<Note> noteCollection = new ArrayList<>();
        //List<Note> noteCollection = new ArrayList<>();
        try {
            SharedPreferences sharePrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
            json = sharePrefs.getString(NOTELIST, null);
            System.out.println("JSONGET" + json +":::");
            //TODO: Retrieve list of obj, not just obj
            Type type = new TypeToken<Note[]>(){}.getType();
            Note memeNote = gson.fromJson(json, Note.class);
            noteCollection.add(memeNote);
//            noteCollection = gson.fromJson(json, type);
           // noteCollection = gson.fromJson(json, type);
            //Transfer from GSON to note
            System.out.println(noteCollection);
        } catch (JsonSyntaxException e) {
            System.err.println("Notes could not be loaded. ");
            e.printStackTrace();
        }
        return noteCollection;
    }

//    public void saveNoteToArray(Note note) {
//        try {
//            FileOutputStream fileOut = new FileOutputStream(filePath);
//            objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(note.getNoteIndex() + "ß" + note + "§");
//            fileOut.getFD().sync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (objectOut != null) {
//                try {
//                    objectOut.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public Note[] retrieveNotesFromArray() {
//        Note[] notesArray = null;
//        try {
//            FileInputStream fileIn = new FileInputStream(filePath);
//            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
//            String rawNote = (String)objectIn.readObject();
//
//            notesArray.add(new Note(in, txt));
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (objectIn != null) {
//                try {
//                    objectIn.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return contact;
//    }



    //Deletes all notes and password.
    public void purgeAllNotes(View v, final Activity act){
        //POPUP Note saying "Are you sure you wan delete?
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Are you sure you want to delete your notes? Your Password will be reset too.");

        //If Yes, Password and Notes are deleted. User sent to Password Setup Screen
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //delete password n notes.
                dialogInterface.dismiss();
                deletePassword(act);
                //TODO: Note Deletion.
                //return to password entry.
                context.startActivity(new Intent(context, Setup.class));
            }
        });
        //If No, dialog dismissed.
        builder.setNegativeButton("No" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



}
