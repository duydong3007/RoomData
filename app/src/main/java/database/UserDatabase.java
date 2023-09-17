package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import model.User;

@Database(entities = {User.class},version = 1)
public abstract class UserDatabase extends RoomDatabase {


    private static final String database="database_user";
    private static UserDatabase instance;

    public static synchronized UserDatabase getinstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,database)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract UserDAO mUserDAO();

}
