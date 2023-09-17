package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.User;


@Dao
public interface  UserDAO  {

    @Insert
    void inserttuser(User user);


    @Query("SELECT * FROM user_db")
    List<User> getuserlist();

    @Query("SELECT * FROM USER_DB WHERE name= :name")
    List<User> checkuser(String name);

    @Update
    void updateuser(User user);

    @Delete
    void deleteuser(User user);

    @Query("DELETE  FROM USER_DB ")
    void deleteAll();

    @Query("SELECT * FROM USER_DB WHERE name like '%' || :name||'%'")
    List<User> searchuser(String name);


}
