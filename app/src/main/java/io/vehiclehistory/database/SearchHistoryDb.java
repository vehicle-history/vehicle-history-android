package io.vehiclehistory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

import io.vehiclehistory.Search;

import static io.vehiclehistory.database.DbConstants.COLUMN_LABEL;
import static io.vehiclehistory.database.DbConstants.COLUMN_REGISTRATION_DATE;
import static io.vehiclehistory.database.DbConstants.COLUMN_REGISTRATION_NUMBER;
import static io.vehiclehistory.database.DbConstants.COLUMN_TIMESTAMP;
import static io.vehiclehistory.database.DbConstants.COLUMN_VIN;
import static io.vehiclehistory.database.DbConstants.TABLE_NAME;

public class SearchHistoryDb {

    private HistoryDbOpenHelper historyDbHelper;

    public SearchHistoryDb(Context context) {
        this.historyDbHelper = new HistoryDbOpenHelper(context);
    }

    public void saveOrUpdateSearch(Search search) {
        SQLiteDatabase db = historyDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMESTAMP, getTimestamp());

        int updatedRows = db.update(TABLE_NAME, values, COLUMN_REGISTRATION_NUMBER
                + " = ? AND " + COLUMN_REGISTRATION_DATE
                + " = ? AND " + COLUMN_VIN + " = ?", new String[]{
                search.getPlate(), search.getRegistrationDate(), search.getVin()
        });

        if (updatedRows == 0) {
            saveSearch(search);
        }

        db.close();
    }

    public Search getSearchAt(int position) {
        SQLiteDatabase db = historyDbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_TIMESTAMP, COLUMN_LABEL, COLUMN_REGISTRATION_NUMBER, COLUMN_VIN, COLUMN_REGISTRATION_DATE
        };

        String sortOrder = COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        cursor.moveToPosition(position);

        Search searchFromCursor = getSearchFromCursor(cursor);
        cursor.close();
        db.close();

        return searchFromCursor;
    }

    public int getCount() {
        SQLiteDatabase db = historyDbHelper.getReadableDatabase();

        int count = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME, null, null);
        db.close();
        return count;
    }

    public void clearHistory() {
        SQLiteDatabase db = historyDbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    private Search getSearchFromCursor(Cursor cursor) {
        String label = cursor.getString(1);
        String registrationNumber = cursor.getString(2);
        String vin = cursor.getString(3);
        String registrationDate = cursor.getString(4);

        return new Search(label, registrationNumber, vin, registrationDate);
    }

    private void saveSearch(Search search) {
        SQLiteDatabase db = historyDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMESTAMP, getTimestamp());
        values.put(COLUMN_LABEL, search.getLabel());
        values.put(COLUMN_REGISTRATION_NUMBER, search.getPlate());
        values.put(COLUMN_VIN, search.getVin());
        values.put(COLUMN_REGISTRATION_DATE, search.getRegistrationDate());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    private long getTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

}
