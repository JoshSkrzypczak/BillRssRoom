package com.josh.billrssroom.db;

import android.content.Context;

import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.utilities.DateTypeConverter;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FeedItem.class, FeedItemFtsEntity.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
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

    private static FeedDatabase create(Context context) {
        // TODO: 2/3/2019 Remove allowMainThreadQueries
        RoomDatabase.Builder<FeedDatabase> b =
                Room.databaseBuilder(context.getApplicationContext(), FeedDatabase.class,
                        DB_NAME).allowMainThreadQueries();
        return b.addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `itemsFts` USING FTS4("
                        + "`title` TEXT, `description` TEXT, `pubDate` TEXT, content=`items`)");

                db.execSQL("INSERT INTO itemsFts (`title`, `description`, `pubDate`) "
                        + "SELECT `title`, `description`, `pubDate` FROM items");
            }
        }).build();
    }
}