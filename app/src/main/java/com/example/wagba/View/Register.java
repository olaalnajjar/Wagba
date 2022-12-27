package com.example.wagba.View;

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

import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDao;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.example.wagba.View.MainActivity;
import com.example.wagba.ViewModel.RegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button registerBtn;
    EditText name, email, password, number;
    String name_text ,email_text,number_text,password_text;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        number = findViewById(R.id.phone);
        registerBtn = findViewById(R.id.RegisterBtn);


        set_actionbar();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read_text();
                if(RegisterViewModel.validate_fields(email_text,password_text,name_text, number_text,getApplicationContext())){
                    register_firebase();}

            }
        });


    }

    private void read_text() {
        name_text = name.getText().toString();
        email_text = email.getText().toString();
        number_text = number.getText().toString();
        password_text = password.getText().toString();
    }


    private void set_actionbar() {
        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
    }


    private void register_firebase(){

        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email_text,password_text).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    RegisterViewModel.room_db(name_text,email_text,number_text,password_text,getApplicationContext());
                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            startActivity(new Intent(Register.this, MainActivity.class));
                            finish();
                        }
                        }, 500);   //0.5 seconds
                    } else{
                        Toast.makeText(getApplicationContext(), "Registeration Failed: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

}