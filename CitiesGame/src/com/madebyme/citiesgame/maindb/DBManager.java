package com.madebyme.citiesgame.maindb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.madebyme.citiesgame.App;
import com.madebyme.citiesgame.City;
import com.madebyme.citiesgame.Constants;
import com.madebyme.citiesgame.supportingdb.UsedCitiesManager;

import java.util.Random;

public class DBManager {

	SQLiteDatabase database;

	public DBManager(Context context) {
		DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
		database = dbOpenHelper.getWritableDatabase();
	}
	
	public void inputDBFeed(City model) {
		ContentValues cv = new ContentValues();
		cv.put(Constants.COLUMN_NAME, model.getName());
		cv.put(Constants.COLUMN_FIRST_LETTER, model.getFirstLetter());
		database.insert(Constants.MAIN_DB_NAME, null, cv);
	}

	public boolean checkCityExistance(City city) {
		Cursor c = database.query(Constants.MAIN_DB_NAME, null, "Name = ?",
				new String[] { city.getName() }, null, null, null);
		return c.moveToFirst();
	}

	public boolean initCursor() {
        Cursor cursor = database.query(Constants.MAIN_DB_NAME, null, null, null, null,
                null, null);
        return cursor.moveToFirst();
    }

    public City findCityByFirstLetter(String letter) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + Constants.MAIN_DB_NAME + " WHERE " + Constants.COLUMN_FIRST_LETTER + " = ?",
                new String[] { letter });
        Random r = new Random();
        cursor.moveToPosition(r.nextInt(cursor.getCount()));
        String cityName = null;
        try{
            cityName = cursor.getString(cursor
                    .getColumnIndex(Constants.COLUMN_NAME));
        }catch(CursorIndexOutOfBoundsException e){
            Log.e("error" ,e.getMessage());
        }
        return new City(cityName, letter);
    }

    public boolean compereTablesOfUsedAndGeneral(String letter){
        Cursor used = database.rawQuery(
                "SELECT * FROM " + Constants.MAIN_DB_NAME + " WHERE " + Constants.COLUMN_FIRST_LETTER + " = ?",
                new String[] { letter });
        UsedCitiesManager usedCitiesManager = App.getUsedCitiesManager();
        Cursor general = usedCitiesManager.getDatabase().rawQuery(
                "SELECT * FROM " + Constants.SUPPORTING_DB_NAME + " WHERE " + Constants.COLUMN_FIRST_LETTER + " = ?",
                new String[] { letter });
        return used.getCount() == general.getCount();//TODO ask if this is really good way to
    }
}
