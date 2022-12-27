package com.example.wagba.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users where id=(:id)")
    UserEntity getCurrentUserByID(Integer id);

    @Query("SELECT * from users where email=(:email) and password=(:password)")
    UserEntity login(String email, String password);

    @Query("SELECT * from users where email=(:email)")
    UserEntity getCurrentUser(String email);

    @Update
    void update_user(UserEntity userEntity);


}