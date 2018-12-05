package com.josh.billrssroom.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.model.FeedItemFtsEntity;
import com.josh.billrssroom.utilities.DateTypeConverter;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FeedItem.class, FeedItemFtsEntity.class}, version = 2)
@TypeConverters(DateTypeConverter.class)
public abstract class FeedDatabase extends RoomDatabase {

    private static volatile FeedDatabase INSTANCE = null;
    private static final String DB_NAME = "dummy.db";

    public abstract ItemDao feedDao();

    public synchronized static FeedDatabase getFeedDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = create(context);
        }
        return INSTANCE;
    }

//    private static FeedDatabase create(Context context) {
//        RoomDatabase.Builder<FeedDatabase> b =
//                Room.databaseBuilder(context.getApplicationContext(), FeedDatabase.class,
//                        DB_NAME);
//        return b.build();
//    }

    private static FeedDatabase create(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), FeedDatabase.class, DB_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);


                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `itemsFts` USING FTS4("
                    + "`title` TEXT, `link` TEXT, `description` TEXT, `pubDate` TEXT, `guid` TEXT, `isFav` BOOLEAN, content=`items`)");

            database.execSQL("INSERT INTO itemsFts (`title`, `link`, `description`, `pubDate`, `guid`, `isFav`) "
                    + "SELECT `title`, `link`, `description`, `pubDate`, `guid`, `isFav` FROM items");

        }
    };
}