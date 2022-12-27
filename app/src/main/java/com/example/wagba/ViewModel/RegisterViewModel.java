package com.example.wagba.ViewModel;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.wagba.RoomDatabase.UserDao;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;

public class RegisterViewModel extends ViewModel {


    private static Boolean validateInput(UserEntity userEntity){
        if(userEntity.getName().isEmpty() ||
                userEntity.getPassword().isEmpty() ||
                userEntity.getEmail().isEmpty() ||
                userEntity.getNumber().isEmpty()){
            return false;
        }
        return true;
    }

    public static void room_db(String name , String email, String number, String password, Context context){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setNumber(number);
        userEntity.setPassword(password);
        if(validateInput(userEntity)){
            //insert data
            UserDatabase userDatabase = UserDatabase.getUserDatabase(context);
            UserDao userDao = userDatabase.userDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Registering user
                    userDao.registerUser(userEntity);
                }
            }).start();
        }


    }

    public static Boolean ValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static boolean validate_fields(String emailText,String passwordText, String numberText,String nameText,Context context){
        if(nameText.isEmpty() || emailText.isEmpty() || numberText.isEmpty() || passwordText.isEmpty()){
            Toast.makeText(context, "Fill all fields please", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!ValidEmail(emailText)){
            Toast.makeText(context, "Email Entered is Incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }else {return true;}
    }
}

