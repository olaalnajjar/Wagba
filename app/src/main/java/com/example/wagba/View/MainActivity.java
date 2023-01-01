package com.example.wagba.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDao;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.example.wagba.ViewModel.MainViewModel;
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

    public static String EMAIL;
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
            MainViewModel.setUserData(getApplicationContext());
            alreadyExecuted = true;
        }

        //intents
        RegisterIntent = new Intent(this, Register.class);
        loginIntent = new Intent(this, Homepage.class);

        google_init();

        // on click listener calls the sign in with google
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if(MainViewModel.validate_fields(emailText,passwordText,getApplicationContext())){
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

    }

    // function that gets the client google intent
    private void SignIn() {

        Log.d("signin","before result start");
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d("signin","after result start");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("signin","in result start");

        // checks if the data returned is from the correct activity
        if (requestCode == RC_SIGN_IN) {

            // signs in with the account that got clicked on
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("signin","calling firebase google login");

                firebaseLoginWithGoogle(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }

    // deals with what is going to happen after the account is chosen on both success account info and on failure account info
    void firebaseLoginWithGoogle(GoogleSignInAccount account) {
        //access token is related to the backend and when a token should be expired
        Log.d("signin","in fire base login google");

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Homepage.class));
                    Log.d("signin","success sign in");
                    MainActivity.this.finish();

                })
                .addOnFailureListener(this, e -> {
                    Log.d("signin","failure sign in");

                    Toast.makeText(this, " Failure in sign in with this account", Toast.LENGTH_SHORT).show();
                });

    }

    void firebase_login(String emailText,String passwordText) {

        auth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    EMAIL = emailText;
                    startActivity(loginIntent);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}