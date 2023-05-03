package com.example.auditorapp.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.auditorapp.entity.DraftStatus;
import com.example.auditorapp.entity.drafts.Drafts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class Database {
    private final SQLiteDatabase database;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());

    public Database(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public Completable insertReview(Drafts drafts) {
        return Completable.create(emitter -> {
            database.execSQL("INSERT INTO Drafts(title, review, image, location, date, status)" +
                    " VALUES ('"
                    + drafts.getTitle() + "','" + drafts.getTextReview() + "','"
                    + drafts.getImage() + "','" + drafts.getLocation() + "','"
                    + dateFormat.format(drafts.getDate()) + "','" + drafts.getStatus().name() + "')");
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    private List<Drafts> getListDrafts() {
        List<Drafts> draftsList = new ArrayList<>();
        try (Cursor cursor = database.rawQuery("SELECT * FROM Drafts", null)) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) return draftsList;

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int reviewIndex = cursor.getColumnIndex("review");
            int imageIndex = cursor.getColumnIndex("image");
            int locationIndex = cursor.getColumnIndex("location");
            int dateIndex = cursor.getColumnIndex("date");
            int draftStatusIndex = cursor.getColumnIndex("status");

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());

            do {
                long id = cursor.getLong(idIndex);
                String title = cursor.getString(titleIndex);
                String textReview = cursor.getString(reviewIndex);
                String image = cursor.getString(imageIndex);
                String location = cursor.getString(locationIndex);
                String currentDate = cursor.getString(dateIndex);
                DraftStatus draftStatus = DraftStatus.byStatus(cursor.getString(draftStatusIndex));
                Date date = null;
                try {
                    date = dateFormat.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Drafts drafts = new Drafts();
                drafts.setId(id);
                drafts.setTitle(title);
                drafts.setTextReview(textReview);
                drafts.setImage(image);
                drafts.setLocation(location);
                drafts.setDate(date);
                drafts.setStatus(draftStatus);

                draftsList.add(drafts);
            } while (cursor.moveToNext());
        }
        return draftsList;
    }

    public Single<List<Drafts>> getListDraftsAsync() {
        return Single.create(emitter -> {
            List<Drafts> draftsList = getListDrafts();
            emitter.onSuccess(draftsList);

        });
    }

    private Drafts getDetailDrafts(String title) {
        Drafts drafts = new Drafts();
        String str = String.format("SELECT * FROM Drafts WHERE title='%s'", title);
        try (Cursor cursor = database.rawQuery(str, null)) {
            cursor.moveToFirst();

            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int reviewIndex = cursor.getColumnIndex("review");
            int imageIndex = cursor.getColumnIndex("image");
            int locationIndex = cursor.getColumnIndex("location");
            int dateIndex = cursor.getColumnIndex("date");
            int draftStatusIndex = cursor.getColumnIndex("status");

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());

            do {
                long id = cursor.getLong(idIndex);
                title = cursor.getString(titleIndex);
                String textReview = cursor.getString(reviewIndex);
                String image = cursor.getString(imageIndex);
                String location = cursor.getString(locationIndex);
                String currentDate = cursor.getString(dateIndex);
                DraftStatus draftStatus = DraftStatus.byStatus(cursor.getString(draftStatusIndex));
                Date date = null;
                try {
                    date = dateFormat.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                drafts.setId(id);
                drafts.setTitle(title);
                drafts.setTextReview(textReview);
                drafts.setImage(image);
                drafts.setLocation(location);
                drafts.setDate(date);
                drafts.setStatus(draftStatus);
            } while (cursor.moveToNext());
        }
        return drafts;
    }

    public Single<Drafts> getDetailReviewAsync(String title) {
        return Single.create(emitter -> {
            Drafts drafts = getDetailDrafts(title);
            emitter.onSuccess(drafts);
        });
    }

    private void updateStatus(Drafts drafts, DraftStatus draftStatus) {
        String str = String.format(Locale.getDefault(),
                "UPDATE Drafts SET status='%s' WHERE id=%d",
                draftStatus.name(), drafts.getId());
        database.execSQL(str);
    }

    public Single<Drafts> updateStatusAsync(Drafts drafts, DraftStatus draftStatus) {
        return Single.create(emitter -> {
            Database.this.updateStatus(drafts, draftStatus);
            if (!emitter.isDisposed()) {
                emitter.onSuccess(drafts);
            }
        });
    }

}
