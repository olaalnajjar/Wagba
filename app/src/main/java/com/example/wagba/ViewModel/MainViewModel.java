package com.example.wagba.ViewModel;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;

public class MainViewModel extends ViewModel {

    public static void setUserData(Context context){
        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user").allowMainThreadQueries().build();

        UserEntity userEntity = new UserEntity();
        userEntity.setName("Ola Elnaggar");
        userEntity.setNumber("012345678");
        userEntity.setPassword("12345678");
        userEntity.setEmail("test@eng.asu.edu.eg");


        db.userDao().registerUser(userEntity);
    }
    public static Boolean ValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean validate_fields(String emailText,String passwordText,Context context){
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(context, "Fill all fields please", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MainViewModel.ValidEmail(emailText)) {
            Toast.makeText(context, "Email Entered is Incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }else {return true;}
    }
}
