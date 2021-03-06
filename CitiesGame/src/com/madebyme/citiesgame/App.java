package com.madebyme.citiesgame;

import android.app.Application;
import android.graphics.Typeface;
import com.madebyme.citiesgame.db.DBManager;
import com.madebyme.citiesgame.db.DBOpenHelper;

public class App extends Application {
	
	private static DBManager dbManager;
    private static DBOpenHelper dbOpenHelper;
    private static Typeface typeface;
    private static Typeface fbTypeface;
	
	@Override
	public void onCreate() {
		super.onCreate();
        dbOpenHelper = new DBOpenHelper(this);
		dbManager = new DBManager();
        typeface = Typeface.createFromAsset(getAssets(), Constants.FONT_FILE_MAME);
        fbTypeface = Typeface.createFromAsset(getAssets(), Constants.FONT_FB);

	}
	
	public static DBManager getDBManager() {
		return dbManager;
	}

    public static Typeface getTypeface(){
        return typeface;
    }

    public static Typeface getFacebookTypeface(){
        return fbTypeface;
    }

    public static DBOpenHelper getDBOpenHelper(){
        return dbOpenHelper;
    }
}
