package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wagba.RoomDatabase.UserDao;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;

public class Register extends AppCompatActivity {

    Button registerBtn;
    EditText name, email, password, number;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        number = findViewById(R.id.phone);


        registerBtn = findViewById(R.id.RegisterBtn);
        intent = new Intent(this, MainActivity.class);

        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                    Toast.makeText(getApplicationContext(),"User Registered!",Toast.LENGTH_SHORT).show();

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
}