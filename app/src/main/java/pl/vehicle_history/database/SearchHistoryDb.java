package pl.vehicle_history.database;

import static pl.vehicle_history.database.DbConstants.COLUMN_LABEL;
import static pl.vehicle_history.database.DbConstants.COLUMN_REGISTRATION_DATE;
import static pl.vehicle_history.database.DbConstants.COLUMN_REGISTRATION_NUMBER;
import static pl.vehicle_history.database.DbConstants.COLUMN_VIN;
import static pl.vehicle_history.database.DbConstants.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import pl.vehicle_history.Search;

public class SearchHistoryDb {

    private HistoryDbOpenHelper historyDbHelper;

    public SearchHistoryDb(Context context) {
        this.historyDbHelper = new HistoryDbOpenHelper(context);
    }

    public void saveSearch(Search search) {
        SQLiteDatabase db = historyDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LABEL, search.getLabel());
        values.put(COLUMN_REGISTRATION_NUMBER, search.getPlate());
        values.put(COLUMN_VIN, search.getVin());
        values.put(COLUMN_REGISTRATION_DATE, search.getRegistrationDate());

        db.insert(TABLE_NAME, null, values);
    }

    public Search getSearchAt(int position) {
        SQLiteDatabase db = historyDbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_LABEL, COLUMN_REGISTRATION_NUMBER, COLUMN_VIN, COLUMN_REGISTRATION_DATE
        };

        String sortOrder = COLUMN_LABEL + " DESC";

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

        String label = cursor.getString(0);
        String registrationNumber = cursor.getString(1);
        String vin = cursor.getString(2);
        String registrationDate = cursor.getString(3);

        return new Search(label, registrationNumber, vin, registrationDate);
    }

    public int getCount() {
        SQLiteDatabase db =  historyDbHelper.getReadableDatabase();

        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME, null, null);
    }

}
