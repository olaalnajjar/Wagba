package com.example.wagba.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    SignInButton signInButton;
    Button loginBtn;
    Intent RegisterIntent, loginIntent;
    TextView register;
    EditText email, password;
    public static final int RC_SIGN_IN = 1234;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;
    FirebaseAuth auth;
    boolean alreadyExecuted= false;
    String emailText ,passwordText;
    public static int ID;
    public static  String EMAIL;
    public static String Google_ID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        set_toolbar();

        //finding components by ID
        register = findViewById(R.id.Register);
        loginBtn = findViewById(R.id.LoginBtn);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        signInButton = findViewById(R.id.signup_with_google_btn);


        //adds testing data to room database
        if(!alreadyExecuted) {
            setUserData(getApplicationContext());
            alreadyExecuted = true;
        }

        //intents
        RegisterIntent = new Intent(this, Register.class);
        loginIntent = new Intent(this, Homepage.class);



        // on click listener calls the sign in with google
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                google_init();
                SignIn();
            }
        });


        //underlining the register text
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterIntent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read_data();
                if(validate_fields(emailText,passwordText,getApplicationContext())){
                    firebase_login(emailText,passwordText);
                }
            }
        });




    }

    private void read_data() {
        emailText = email.getText().toString();
        passwordText = password.getText().toString();
    }

    private void set_toolbar() {
        //setting customized action bar
        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);

    }


    private void google_init() {

        // sets sign in options to request email, token is the projects unique ID predefined in the project already
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // gives the sign in client the chosen options for the sign in so that the client has its values
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        auth = FirebaseAuth.getInstance();
        Google_ID= auth.getUid();


    }

    // function that gets the client google intent
    private void SignIn() {

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checks if the data returned is from the correct activity
        if (requestCode == RC_SIGN_IN) {

            // signs in with the account that got clicked on
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseLoginWithGoogle(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }

    // deals with what is going to happen after the account is chosen on both success account info and on failure account info
    void firebaseLoginWithGoogle(GoogleSignInAccount account) {
        //access token is related to the backend and when a token should be expired

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Homepage.class));
                    MainActivity.this.finish();

                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, " Failure in sign in with this account", Toast.LENGTH_SHORT).show();
                });

    }

    void firebase_login(String emailText,String passwordText) {
        Google_ID="";
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    EMAIL= emailText;
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(loginIntent);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void setUserData(Context context){
        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user").allowMainThreadQueries().build();

        UserEntity userEntity = new UserEntity();
        userEntity.setName("Ola Elnaggar");
        userEntity.setNumber("012345678");
        userEntity.setPassword("12345678");
        userEntity.setEmail("test@eng.asu.edu.eg");
        //ID=userEntity.getId();
        //EMAIL= userEntity.getEmail();
        db.userDao().registerUser(userEntity);
    }
    public static Boolean ValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean validate_fields(String emailText,String passwordText,Context context){
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(context, "Fill all fields please", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ValidEmail(emailText)) {
            Toast.makeText(context, "Email Entered is Incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }else {return true;}
    }

}