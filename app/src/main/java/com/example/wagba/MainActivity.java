package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.RoomDatabase.UserDao;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.Register);
        loginBtn = findViewById(R.id.LoginBtn);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        // sets sign in options to request email, token is the projects unique ID predefined in the project already
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // gives the sign in client the chosen options for the sign in so that the client has its values
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        auth = FirebaseAuth.getInstance();

        // on click listener calls the sign in function we created
        signInButton = findViewById(R.id.signup_with_google_btn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn();

            }
        });

        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        RegisterIntent = new Intent(this, Register.class);
        loginIntent = new Intent(this, Homepage.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterIntent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {

                    firebase_login();
                    //room_db_check(emailText,passwordText);

                }


            }
        });

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
    private void firebaseLoginWithGoogle(GoogleSignInAccount account) {


        //access token is related to the backend and when a token should be expired
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Homepage.class));


                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, " Failure in sign in with this account", Toast.LENGTH_SHORT).show();
                });

    }

    private void room_db_check(String email, String password) {
        //perform Query
        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        UserDao userDao = userDatabase.userDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserEntity userEntity = userDao.login(email, password);
                if (userEntity == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    String name = userEntity.getName();
                    startActivity(loginIntent.putExtra("name", name));
                }
            }
        }).start();
    }


    private void firebase_login() {

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill all fields please", Toast.LENGTH_SHORT).show();
            return;
        } else if (!ValidEmail(emailText)) {
            Toast.makeText(getApplicationContext(), "Email Entered is Incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            startActivity(loginIntent.putExtra("email",emailText));
                            finish();
                        }
                    }, 1000);   //1 seconds
                } else{

                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    private Boolean ValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}