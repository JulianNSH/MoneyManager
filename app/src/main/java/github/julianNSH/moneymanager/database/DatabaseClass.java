package github.julianNSH.moneymanager.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.overview.OverviewModelClass;
import github.julianNSH.moneymanager.savings.SavingsModelClass;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class DatabaseClass extends SQLiteOpenHelper{
    //Logcat
    private static final String LOG = "DatabaseLog";
    //Database Version
    private static final int DATABASE_VERSION = 2;
    //Database Name
    private static final String DATABASE_NAME = "moneyManagerDatabase";
    //Table Names
    private static final String TABLE_INCOME = "income";
    private static final String TABLE_OUTGOING = "outgoing";
    private static final String TABLE_SCOPES = "scopes";
    private static final String TABLE_SCOPES_LIST = "scopes_list";
    //Common column Names
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_REPEAT = "repeat";

    //INCOME column names
    private static final String KEY_INCOME_SOURCE = "source";
    //OUTGOING column names
    private static final String KEY_OUTGOING_SOURCE = "outgoing";
    private static final String KEY_OUTGOING_ICON = "icon";
    //SCOPES column names
    private static final String KEY_SCOPE_SOURCE = "scope";
    //SCOPES_LIST column names
    private static final String KEY_SCOPE_LIST_SOURCE = "scope_list";
    private static final String KEY_START_DATETIME = "start_date_time";
    private static final String KEY_END_DATETIME = "end_date_time";
    private static final String KEY_CURRENT_AMOUNT = "current_amount";
    private static final String KEY_NEEDED_AMOUNT = "needed_amount";

    //CREATE TABLE STATEMENTS

    //CREATE INCOME TABLE
    private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME +
            "("+ KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_INCOME_SOURCE +" TEXT,"+ KEY_AMOUNT +
            " REAL,"+KEY_TIME +" DATETIME,"+ KEY_DATE +" DATETIME,"+ KEY_COMMENT +" TEXT,"+ KEY_REPEAT +" INTEGER"+")";
    //CREATE OUTGOING TABLE
    private static final String CREATE_TABLE_OUTGOING = "CREATE TABLE " + TABLE_OUTGOING +
            "("+ KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_OUTGOING_ICON +" INTEGER,"+ KEY_OUTGOING_SOURCE +
            " TEXT,"+ KEY_AMOUNT + " REAL,"+ KEY_TIME +" DATETIME,"+KEY_DATE +" DATETIME,"+ KEY_COMMENT +" TEXT,"+ KEY_REPEAT +" INTEGER"+")";
    //CREATE SCOPE TABLE
    private static final String CREATE_TABLE_SCOPE = "CREATE TABLE " + TABLE_SCOPES+
            "("+ KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_SCOPE_SOURCE +" TEXT,"+ KEY_AMOUNT +
            " REAL,"+ KEY_TIME +" DATETIME,"+KEY_DATE +" DATETIME,"+ KEY_COMMENT +" TEXT,"+ KEY_REPEAT +" INTEGER"+")";
    //CREATE SCOPE_LIST TABLE
    private static final String CREATE_TABLE_SCOPE_LIST ="CREATE TABLE " + TABLE_SCOPES_LIST+
            "("+ KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_SCOPE_LIST_SOURCE +" TEXT,"+ KEY_CURRENT_AMOUNT +
            " REAL,"+ KEY_NEEDED_AMOUNT + " REAL,"+ KEY_START_DATETIME +" DATETIME,"+
            KEY_END_DATETIME +" DATETIME,"+ KEY_COMMENT +" TEXT"+")";


    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**********************************************************************************************
     OVERVIEW QUERY METHODS
     **********************************************************************************************/
    //Read data for overview
    public ArrayList<OverviewModelClass> getOverviewData(String date){
        ArrayList<OverviewModelClass> overview = new ArrayList<OverviewModelClass>();
        String queryIncome = "SELECT * FROM "+TABLE_INCOME+" WHERE "+KEY_DATE+" LIKE "+
                "'%"+date+"' ORDER BY "+KEY_TIME+" ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryIncome, null);

        if(c.moveToFirst()){
            do{
                OverviewModelClass temp = new OverviewModelClass();
                temp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                temp.setTvDomain("income");
                temp.setTvType(c.getString(c.getColumnIndex(KEY_INCOME_SOURCE)));
                temp.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
                temp.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                temp.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                temp.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
                overview.add(temp);
            } while (c.moveToNext());
        }
        
        String queryOutgoing = "SELECT * FROM "+TABLE_OUTGOING+" WHERE "+KEY_DATE+" LIKE "+
                "'%"+date+"' ORDER BY "+KEY_TIME+" ASC";

        db = this.getReadableDatabase();
        c = db.rawQuery(queryOutgoing, null);

        if(c.moveToFirst()){
            do{
                OverviewModelClass temp = new OverviewModelClass();
                temp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                temp.setTvDomain("outgoing");
                temp.setTvType(c.getString(c.getColumnIndex(KEY_OUTGOING_SOURCE)));
                temp.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
                temp.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                temp.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                temp.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
                overview.add(temp);
            } while (c.moveToNext());
        }
        return overview;
    }
    //Delete data
    public void deleteFromOverview(int id, String strId) {
        SQLiteDatabase db = this.getWritableDatabase();

        if(strId.equals("Venit")) {
            db.delete(TABLE_INCOME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        }
        if(strId.equals("Cheltuieli")) {
            db.delete(TABLE_OUTGOING, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        }
    }
    /**********************************************************************************************
        INCOME METHODS
     **********************************************************************************************/
    //CREATE ELEMENT
    public long addIncome(StatisticsModelClass incoming){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_INCOME_SOURCE, incoming.getTvType());
        values.put(KEY_AMOUNT, incoming.getTvAmount());
        values.put(KEY_TIME, incoming.getTime());
        values.put(KEY_DATE, incoming.getDate());
        values.put(KEY_COMMENT, incoming.getComment());
        values.put(KEY_REPEAT, incoming.getRepeat());

        long incoming_id = db.insert(TABLE_INCOME, null, values);

        return incoming_id;
    }
    //READ TABLE
    public float getTotalIncome(String date){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM("+KEY_AMOUNT+") AS "+ KEY_AMOUNT+" FROM "+TABLE_INCOME+
                " WHERE "+KEY_DATE+" LIKE "+ "'%"+date+"'";

        Log.e(LOG, query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getFloat(cursor.getColumnIndex(KEY_AMOUNT));



    }
    //UPDATE TABLE
    //DELETE ELEMENT

    /**********************************************************************************************
        OUTGOING METHODS
     **********************************************************************************************/

    //CREATE ELEMENT
    public long addOutgoing(StatisticsModelClass outgoing){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_OUTGOING_ICON, outgoing.getIvIcon());
        values.put(KEY_OUTGOING_SOURCE, outgoing.getTvType());
        values.put(KEY_AMOUNT, outgoing.getTvAmount());
        values.put(KEY_TIME, outgoing.getTime());
        values.put(KEY_DATE, outgoing.getDate());
        values.put(KEY_COMMENT, outgoing.getComment());
        values.put(KEY_REPEAT, outgoing.getRepeat());

        long outgoing_id = db.insert(TABLE_OUTGOING, null, values);

        return outgoing_id;
    }

    //READ TABLE
    public float getTotalOutgoing(String date){
        String query = "SELECT SUM("+KEY_AMOUNT+") AS "+ KEY_AMOUNT+" FROM "+TABLE_OUTGOING+
                " WHERE "+KEY_DATE+" LIKE "+ "'%"+date+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getFloat(cursor.getColumnIndex(KEY_AMOUNT));
    }

    public StatisticsModelClass getOutgoingById(long outgoing_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+ TABLE_OUTGOING+" WHERE"+KEY_ID+" = "+ outgoing_id;
        Log.e(LOG, selectQuery);

        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
        if(c != null) c.moveToFirst();

        StatisticsModelClass outgoing = new StatisticsModelClass();
        outgoing.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        outgoing.setIvIcon(c.getInt(c.getColumnIndex(KEY_OUTGOING_ICON)));
        outgoing.setTvType(c.getString(c.getColumnIndex(KEY_OUTGOING_SOURCE)));
        outgoing.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
        outgoing.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
        outgoing.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        outgoing.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
        outgoing.setRepeat(c.getInt(c.getColumnIndex(KEY_REPEAT)));

        return outgoing;
    }
    public ArrayList<StatisticsModelClass> getAllOutgoingData(){
        ArrayList<StatisticsModelClass> outgoings = new ArrayList<StatisticsModelClass>();
        String query = "SELECT * FROM "+TABLE_OUTGOING+" ORDER BY "+KEY_DATE+" DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                StatisticsModelClass temp = new StatisticsModelClass();
                temp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                temp.setIvIcon(c.getInt(c.getColumnIndex(KEY_OUTGOING_ICON)));
                temp.setTvType(c.getString(c.getColumnIndex(KEY_OUTGOING_SOURCE)));
                temp.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
                temp.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                temp.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                temp.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
                temp.setRepeat(c.getInt(c.getColumnIndex(KEY_REPEAT)));
                outgoings.add(temp);
            } while (c.moveToNext());
        }
        return outgoings;
    }
    //SELECT * FROM outgoing WHERE date_time LIKE "%3/2021"
    public ArrayList<StatisticsModelClass> getOutgoingDataByMonthYear(String date){
        ArrayList<StatisticsModelClass> outgoings = new ArrayList<StatisticsModelClass>();
        String query = "SELECT * FROM "+TABLE_OUTGOING+" WHERE "+KEY_DATE+" LIKE "+
                "'%"+date+"' ORDER BY "+KEY_TIME+" ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                StatisticsModelClass temp = new StatisticsModelClass();
                temp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                temp.setIvIcon(c.getInt(c.getColumnIndex(KEY_OUTGOING_ICON)));
                temp.setTvType(c.getString(c.getColumnIndex(KEY_OUTGOING_SOURCE)));
                temp.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
                temp.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                temp.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                temp.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
                temp.setRepeat(c.getInt(c.getColumnIndex(KEY_REPEAT)));
                outgoings.add(temp);
            } while (c.moveToNext());
        }
        return outgoings;
    }
    //UPDATE TABLE
    public int updateOutgoing (StatisticsModelClass upd){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues val = new ContentValues();
        val.put(KEY_OUTGOING_ICON, upd.getIvIcon());
        val.put(KEY_OUTGOING_SOURCE, upd.getTvType());
        val.put(KEY_AMOUNT, upd.getTvAmount());
        val.put(KEY_TIME, upd.getTime());
        val.put(KEY_DATE, upd.getDate());
        val.put(KEY_COMMENT, upd.getComment());
        val.put(KEY_REPEAT, upd.getRepeat());

        return db.update(TABLE_OUTGOING, val, KEY_ID + " = ?",
                new String[]{String.valueOf(upd.getId())});
    }
    //DELETE ELEMENT
    public void deleteOutgoing(int outgoing_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OUTGOING, KEY_ID + " = ?", new String[]{String.valueOf(outgoing_id)});
    }

    //TODO get data for savings
    /**********************************************************************************************
     SAVINGS QUERY METHODS
     **********************************************************************************************/
    public ArrayList<SavingsModelClass> getSavingsUniqueDates(){
        ArrayList<SavingsModelClass> dates = new ArrayList<>();
        String query = "SELECT DISTINCT "+KEY_DATE+" FROM "+TABLE_OUTGOING;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                SavingsModelClass temp = new SavingsModelClass();
                temp.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            } while (c.moveToNext());
        }
        return dates;
    }
    /**********************************************************************************************
        SCOPE METHODS
     **********************************************************************************************/
    //CREATE ELEMENT
    //READ TABLE
    //UPDATE TABLE
    //DELETE ELEMENT

    /**********************************************************************************************
        SCOPE TABLE METHODS
     **********************************************************************************************/
    //CREATE ELEMENT
    //READ TABLE
    //UPDATE TABLE
    //DELETE ELEMENT


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_OUTGOING);
        db.execSQL(CREATE_TABLE_SCOPE);
        db.execSQL(CREATE_TABLE_SCOPE_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_OUTGOING);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SCOPES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SCOPES_LIST);
        onCreate(db);
    }

}
