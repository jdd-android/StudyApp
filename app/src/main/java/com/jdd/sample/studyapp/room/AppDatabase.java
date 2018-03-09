package com.jdd.sample.studyapp.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jdd.sample.studyapp.room.dao.UserDao;
import com.jdd.sample.studyapp.room.entity.User;

/**
 * @author lc. 2018-03-09 16:48
 * @since 1.0.0
 */

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_FILE_NAME = "app_database";

    private static AppDatabase mAppDatabase;

    /**
     * 获取单例数据库对象
     *
     * @param context {@link Context}
     * @return 数据库对象
     */
    public static AppDatabase getInstance(Context context) {
        if (mAppDatabase == null) {
            synchronized (AppDatabase.class) {
                if (mAppDatabase == null) {
                    // 创建数据库对象
                    mAppDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_FILE_NAME)
                            .allowMainThreadQueries().build();
                }
            }
        }
        return mAppDatabase;
    }

    /**
     * 返回 {@link UserDao} 对象
     */
    public abstract UserDao userDao();

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("");
//        }
//    };
}
