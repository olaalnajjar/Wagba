package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wagba.RoomDatabase.UserDao;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button registerBtn;
    EditText name, email, password, number;
    Intent intent;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        number = findViewById(R.id.phone);

        auth = FirebaseAuth.getInstance();

        registerBtn = findViewById(R.id.RegisterBtn);
        intent = new Intent(this, MainActivity.class);

        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
        //View view = getSupportActionBar().getCustomView();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register_firebase();
                room_db();
            }
        });


    }

    private Boolean validateInput(UserEntity userEntity){
        if(userEntity.getName().isEmpty() ||
                userEntity.getPassword().isEmpty() ||
                userEntity.getEmail().isEmpty() ||
                userEntity.getNumber().isEmpty()){
            return false;
        }
        return true;
    }


    private void room_db(){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name.getText().toString());
        userEntity.setEmail(email.getText().toString());
        userEntity.setNumber(number.getText().toString());
        userEntity.setPassword(password.getText().toString());
        if(validateInput(userEntity)){
            //insert data
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
            UserDao userDao = userDatabase.userDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Registering user
                    userDao.registerUser(userEntity);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getApplicationContext(),"User Registered!",Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    startActivity(intent);
                                }
                            }, 1000);   //1 seconds

                        }
                    });

                }
            }).start();
        }else {
            Toast.makeText(getApplicationContext(), "Fill all fields please", Toast.LENGTH_SHORT).show();
        }


    }

    private void register_firebase(){

        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Number = number.getText().toString();
        String Password = password.getText().toString();

        if(Name.isEmpty() || Email.isEmpty() || Number.isEmpty() || Password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Fill all fields please", Toast.LENGTH_SHORT).show();
            return;
        }else if (!ValidEmail(Email)){
            Toast.makeText(getApplicationContext(), "Email Entered is Incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            startActivity(intent);
                            finish();
                        }
                        }, 1000);   //1 seconds
                    } else{

                        Toast.makeText(getApplicationContext(), "Registeration Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });



    }

   private Boolean ValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}