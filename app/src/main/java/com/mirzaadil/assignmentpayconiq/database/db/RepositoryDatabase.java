package com.mirzaadil.assignmentpayconiq.database.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mirzaadil.assignmentpayconiq.database.entity.Model;

import static com.mirzaadil.assignmentpayconiq.utils.Constants.DB_NAME;

@Database(entities = {Model.RepositoryModel.class}, version = 6)
public abstract class RepositoryDatabase extends RoomDatabase {
    private static volatile RepositoryDatabase instance;

    public static synchronized RepositoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static RepositoryDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                RepositoryDatabase.class,
                DB_NAME
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public abstract RepositoriesDao getRepsositoriesDao();
}
