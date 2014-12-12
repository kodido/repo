package org.ideasforchange.imenno.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NameDaysDataHelper extends SQLiteOpenHelper {

	private static final String NAME_DAYS_DB_NAME = "nameDaysDB";	
	private static final String NAME_DAYS_TABLE = "NAME_DAYS";
    private static final String NAME_DAYS_TABLE_CREATE = "CREATE TABLE NAME_DAYS (NAME TEXT, MONTH TEXT, DAY TEXT);";
	

	public NameDaysDataHelper(Context context) {
		super(context, NAME_DAYS_DB_NAME, null, 1);
		context.deleteDatabase(NAME_DAYS_DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(NAME_DAYS_TABLE_CREATE);
		
		/* TODO Dia has name day every day */		
		Calendar cal = Calendar.getInstance();
		
		addNameDay(db, new NameDay(new String[]{"Dia"}, new DayOfYear(cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH))));
		
		String[] namesAlexandrovden = new String[]{"Александър", "Alexandar", "Александра", "Alexandra", "Сашо", 
				"Sasho", "Саша", "Sasha"};
		
		
		addNameDay(db, new NameDay(namesAlexandrovden, new DayOfYear(Calendar.NOVEMBER, 23)));
	}

	private void addNameDay(SQLiteDatabase db, NameDay nameDay) {		
		ContentValues cv = new ContentValues();
		
		List<String> names = nameDay.getNames();
		for (String name : names) {
			cv.put("NAME", name);
			cv.put("MONTH", String.valueOf(nameDay.getDay().getMonth()));
			cv.put("DAY", String.valueOf(nameDay.getDay().getDayOfMonth()));
			
		}		
		
		if (db == null) {
			db = getWritableDatabase();
		} 
		
		db.insert(NAME_DAYS_TABLE, null, cv);		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + NAME_DAYS_TABLE);
		db.execSQL(NAME_DAYS_TABLE_CREATE);
	}

	private List<NameDay> getFixedNameDaysToday() {
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = new String[] {"NAME", "MONTH", "DAY"};
		String whereClause = "MONTH = ? and DAY = ?";
		Calendar today = Calendar.getInstance();
		String[] whereArgs = new String[]{
			String.valueOf(today.get(Calendar.MONTH)), 
			String.valueOf(today.get(Calendar.DAY_OF_MONTH))
		};
		Cursor c = db.query(NAME_DAYS_TABLE, columns, whereClause, whereArgs, null, null, null);
		
		List<NameDay> result = new ArrayList<NameDay>();
		
		List<String> names = new ArrayList<String>();
		while (c.moveToNext()) {
			names.add(c.getString(0));
		}
		result.add(new NameDay(names, 
				new DayOfYear(today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))));

		
		return result;
	}

	public List<NameDay> getNameDaysToday() {
		List<NameDay> result = new ArrayList<NameDay>();
		result.addAll(getFixedNameDaysToday());
		/* TODO: add support for "floating" name days, like
		 *   - Тодорова събота, Конски великден, Тудорица -­ съботата от ТОДОРОВСКАТА НЕДЕЛЯ (съботата след СИРНИ ЗАГОВЕЗНИ). 
		 *   - ВРЪБНИЦА (Цветница, Цветна неделя, Вая, Куклинден -­ неделята преди ВЕЛИКДЕН)
         *      Именници: Върбан, Цветан, Цветанка и всички, носещи имена на цветя и дървета
		 */
		return result;
	}
	
}
