package github.julianNSH.moneymanager.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.overview.OverviewModelClass;
import github.julianNSH.moneymanager.savings.SavingsModelClass;
import github.julianNSH.moneymanager.scope.ScopeModelClass;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class DatabaseClass extends SQLiteOpenHelper{
    //Logcat
    private static final String LOG = "DatabaseLog";
    //Database Version
    private static final int DATABASE_VERSION = 6;
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
    private static final String KEY_SCOPE_ID = "id_list";
    //SCOPES_LIST column names
    private static final String KEY_SCOPE_LIST_TITLE = "scope_list";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_CURRENT_AMOUNT = "current_amount";
    private static final String KEY_NEEDED_AMOUNT = "needed_amount";
    private static final String KEY_IS_COMPLETED = "is_completed";

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
            "("+ KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_SCOPE_SOURCE +" TEXT,"+ KEY_SCOPE_ID +" INTEGER,"+ KEY_AMOUNT +
            " REAL,"+ KEY_TIME +" DATETIME,"+KEY_DATE +" DATETIME,"+ KEY_COMMENT +" TEXT,"+ KEY_REPEAT +" INTEGER"+")";
    //CREATE SCOPE_LIST TABLE
    private static final String CREATE_TABLE_SCOPE_LIST ="CREATE TABLE " + TABLE_SCOPES_LIST+
            "("+ KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_SCOPE_LIST_TITLE +" TEXT,"+ KEY_CURRENT_AMOUNT +
            " REAL,"+ KEY_NEEDED_AMOUNT + " REAL,"+ KEY_START_TIME +" DATETIME,"+
            KEY_START_DATE +" DATETIME,"+ KEY_END_TIME +" DATETIME,"+
            KEY_END_DATE +" DATETIME,"+ KEY_COMMENT +" TEXT, "+KEY_IS_COMPLETED+" INTEGER"+")";


    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**********************************************************************************************
     OVERVIEW QUERY METHODS
     **********************************************************************************************/
    //Read data for overview
    public ArrayList<OverviewModelClass> getOverviewData(String date){
        ArrayList<OverviewModelClass> overview = new ArrayList<OverviewModelClass>();
        String queryIncome = "SELECT * FROM "+TABLE_INCOME+" WHERE "+KEY_DATE+" LIKE '"
                +date+"%'";

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
        
        String queryOutgoing = "SELECT * FROM "+TABLE_OUTGOING+" WHERE "+KEY_DATE+" LIKE '"
                +date+"%'";

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
        String queryScope = "SELECT * FROM "+TABLE_SCOPES+" WHERE "+KEY_DATE+" LIKE '"
                +date+"%'";

        db = this.getReadableDatabase();
        c = db.rawQuery(queryScope, null);

        if(c.moveToFirst()){
            do{
                OverviewModelClass temp = new OverviewModelClass();
                temp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                temp.setTvDomain("other");
                temp.setTvType(c.getString(c.getColumnIndex(KEY_SCOPE_SOURCE)));
                temp.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
                temp.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                temp.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                temp.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
                overview.add(temp);
            } while (c.moveToNext());
        }
        Log.e(LOG, queryOutgoing);

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

        if(strId.equals("Scop")) {
            db.delete(TABLE_SCOPES, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        }
    }
    //Update

//    ContentValues val = new ContentValues();
//        val.put(KEY_OUTGOING_ICON, upd.getIvIcon());
//        val.put(KEY_OUTGOING_SOURCE, upd.getTvType());
//        val.put(KEY_AMOUNT, upd.getTvAmount());
//        val.put(KEY_TIME, upd.getTime());
//        val.put(KEY_DATE, upd.getDate());
//        val.put(KEY_COMMENT, upd.getComment());
//        val.put(KEY_REPEAT, upd.getRepeat());
//
//        return db.update(TABLE_OUTGOING, val, KEY_ID + " = ?",
//            new String[]{String.valueOf(upd.getId())});
    public long updateFromOverview(OverviewModelClass element) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues val = new ContentValues();
        val.put(KEY_AMOUNT, element.getTvAmount());
        val.put(KEY_TIME, element.getTime());
        val.put(KEY_DATE, element.getDate());
        val.put(KEY_COMMENT, element.getComment());
        val.put(KEY_REPEAT, element.getRepeat());
        String table = null;

        if(element.getTvDomain().equals("Venit")) {
            val.put(KEY_INCOME_SOURCE, element.getTvType());
            table = TABLE_INCOME;
        }
        if(element.getTvDomain().equals("Cheltuieli")) {
            val.put(KEY_OUTGOING_SOURCE, element.getTvType());
            table = TABLE_OUTGOING;
        }

        if(element.getTvDomain().equals("Scop")) {
            val.put(KEY_SCOPE_SOURCE, element.getTvType());
            table = TABLE_SCOPES;
        }

        return db.update(table, val, KEY_ID + " = ?",
            new String[]{String.valueOf(element.getId())});
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
                " WHERE "+KEY_DATE+" LIKE '"+date+"%'";

        Log.e(LOG, query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getFloat(cursor.getColumnIndex(KEY_AMOUNT));


    }
    public ArrayList<String> getDistinctIncome(){
        ArrayList<String> titles = new ArrayList<>();
        String query = "SELECT DISTINCT "+KEY_INCOME_SOURCE+" FROM "+TABLE_INCOME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                titles.add(c.getString(c.getColumnIndex(KEY_INCOME_SOURCE)));
            } while (c.moveToNext());
        }
        return titles;
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
                " WHERE "+KEY_DATE+" LIKE '"+date+"%'";
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
        String query = "SELECT * FROM "+TABLE_OUTGOING+" WHERE "+KEY_DATE+" LIKE '"
                +date+"%' ORDER BY "+KEY_TIME+" ASC";

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
    //SELECT outgoing, SUM(amount) AS amount FROM outgoing WHERE outgoing LIKE 'gg' AND date LIKE '2021-05%'
    public ArrayList<StatisticsModelClass> getDistinctOutgoingsAmountByDate(String date){
        ArrayList<String> distinctOutgoings = getDistinctOutgoings(date);
        ArrayList<StatisticsModelClass> result =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String query;
        for (int i=0; i<distinctOutgoings.size(); i++){
            query = "SELECT " + KEY_OUTGOING_SOURCE + ", SUM(" + KEY_AMOUNT + ") AS " + KEY_AMOUNT + "  FROM " +
                    TABLE_OUTGOING + " WHERE " + KEY_OUTGOING_SOURCE + " LIKE '" + distinctOutgoings.get(i) +
                    "' AND " + KEY_DATE + " LIKE'"+date+"%'";
            c = db.rawQuery(query, null);

            if(c.moveToFirst()){
                do{
                    StatisticsModelClass temp = new StatisticsModelClass();
                    temp.setTvType(c.getString(c.getColumnIndex(KEY_OUTGOING_SOURCE)));
                    temp.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
                    result.add(temp);
                } while (c.moveToNext());
            }
        }
        return result;
    }
    public ArrayList<String> getDistinctOutgoings(){
        ArrayList<String> titles = new ArrayList<>();
        String query = "SELECT DISTINCT "+KEY_OUTGOING_SOURCE+" FROM "+TABLE_OUTGOING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                titles.add(c.getString(c.getColumnIndex(KEY_OUTGOING_SOURCE)));
            } while (c.moveToNext());
        }
        return titles;
    }    public ArrayList<String> getDistinctOutgoings(String date){
        ArrayList<String> titles = new ArrayList<>();
        String query = "SELECT DISTINCT "+KEY_OUTGOING_SOURCE+" FROM "+TABLE_OUTGOING+" WHERE "+
                KEY_DATE+" LIKE '"+date+"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                titles.add(c.getString(c.getColumnIndex(KEY_OUTGOING_SOURCE)));
            } while (c.moveToNext());
        }
        return titles;
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
    public long addScopeValue(ScopeModelClass inputSourceVal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SCOPE_SOURCE, inputSourceVal.getTvTitle());
        values.put(KEY_SCOPE_ID, inputSourceVal.getGeneralId());
        values.put(KEY_AMOUNT, inputSourceVal.getTvInitialAmount());
        values.put(KEY_TIME, inputSourceVal.getTime());
        values.put(KEY_DATE, inputSourceVal.getDate());
        values.put(KEY_COMMENT, inputSourceVal.getComment());
        values.put(KEY_REPEAT, inputSourceVal.getRepeat());

        long scope_id = db.insert(TABLE_SCOPES, null, values);

        return scope_id;
    }
    //READ TABLE
    public float getTotalScopeById(int id){
        String query = "SELECT SUM("+KEY_AMOUNT+") AS "+ KEY_AMOUNT+" FROM "+TABLE_SCOPES+
                " WHERE "+KEY_SCOPE_ID+" = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Log.e(LOG, query);
        return cursor.getFloat(cursor.getColumnIndex(KEY_AMOUNT));
    }
    //UPDATE TABLE
    //DELETE ELEMENT

    /**********************************************************************************************
        SCOPE TABLE METHODS
     **********************************************************************************************/
    //CREATE ELEMENT
    public long addScope(ScopeModelClass scope){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SCOPE_LIST_TITLE, scope.getTvTitle());
        values.put(KEY_CURRENT_AMOUNT, scope.getTvInitialAmount());
        values.put(KEY_NEEDED_AMOUNT, scope.getTvFinalAmount());
        values.put(KEY_START_TIME, scope.getStartTime());
        values.put(KEY_START_DATE, scope.getStartDate());
        values.put(KEY_END_TIME, scope.getEndTime());
        values.put(KEY_END_DATE, scope.getEndDate());
        values.put(KEY_COMMENT, scope.getComment());
        values.put(KEY_IS_COMPLETED, scope.getIsCompleted());

        long scope_id = db.insert(TABLE_SCOPES_LIST, null, values);

        return scope_id;
    }
    //READ TABLE
    public ArrayList<ScopeModelClass> getAllScopes(){
        ArrayList<ScopeModelClass> scopeList = new ArrayList<ScopeModelClass>();
        String query = "SELECT * FROM "+TABLE_SCOPES_LIST+" ORDER BY "+KEY_ID+" DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                ScopeModelClass temp = new ScopeModelClass();
                temp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                temp.setTvTitle(c.getString(c.getColumnIndex(KEY_SCOPE_LIST_TITLE)));
                temp.setTvInitialAmount(c.getFloat(c.getColumnIndex(KEY_CURRENT_AMOUNT)));
                temp.setTvFinalAmount(c.getFloat(c.getColumnIndex(KEY_NEEDED_AMOUNT)));
                temp.setStartTime(c.getString(c.getColumnIndex(KEY_START_TIME)));
                temp.setStartDate(c.getString(c.getColumnIndex(KEY_START_DATE)));
                temp.setEndTime(c.getString(c.getColumnIndex(KEY_END_TIME)));
                temp.setEndDate(c.getString(c.getColumnIndex(KEY_END_DATE)));
                temp.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
                temp.setIsCompleted(c.getInt(c.getColumnIndex(KEY_IS_COMPLETED)));
                scopeList.add(temp);
            } while (c.moveToNext());
        }
        return scopeList;
    }
    public HashMap<Integer, String> getScopes(){
        HashMap<Integer, String> titles = new HashMap<Integer, String>();
        String query = "SELECT "+KEY_ID+", "+KEY_SCOPE_LIST_TITLE+" FROM "+TABLE_SCOPES_LIST+" ORDER BY "
                +KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                titles.put(c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getString(c.getColumnIndex(KEY_SCOPE_LIST_TITLE)));
            } while (c.moveToNext());
        }
        return titles;
    }
    //UPDATE TABLE
    public int updateScope(ScopeModelClass updScope){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues val = new ContentValues();
        val.put(KEY_SCOPE_LIST_TITLE, updScope.getTvTitle());
        val.put(KEY_CURRENT_AMOUNT, updScope.getTvInitialAmount());
        val.put(KEY_NEEDED_AMOUNT, updScope.getTvFinalAmount());
        val.put(KEY_START_TIME, updScope.getStartTime());
        val.put(KEY_START_DATE, updScope.getStartDate());
        val.put(KEY_END_TIME, updScope.getEndTime());
        val.put(KEY_END_DATE, updScope.getEndDate());
        val.put(KEY_COMMENT, updScope.getComment());
        val.put(KEY_IS_COMPLETED, updScope.getIsCompleted());

        return db.update(TABLE_SCOPES_LIST, val, KEY_ID + " = ?",
                new String[]{String.valueOf(updScope.getId())});
    }

    public void convertScopesToOutgoings(int targetId){
        ArrayList<StatisticsModelClass> scopesVal = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryScope = "SELECT * FROM "+TABLE_SCOPES+" WHERE "+KEY_SCOPE_ID+" = "+targetId;
        Cursor c = db.rawQuery(queryScope, null);

        if(c.moveToFirst()){
            do{
                StatisticsModelClass temp = new StatisticsModelClass();
                temp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                temp.setTvType(c.getString(c.getColumnIndex(KEY_SCOPE_SOURCE)));
                temp.setTvAmount(c.getFloat(c.getColumnIndex(KEY_AMOUNT)));
                temp.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                temp.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                temp.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
                temp.setRepeat(0);
                scopesVal.add(temp);
            } while (c.moveToNext());
        }
        for (StatisticsModelClass element: scopesVal) {
            addOutgoing(element);
            db.delete(TABLE_SCOPES, KEY_ID + " = ?", new String[]{String.valueOf(element.getId())});
        }

    }
    //DELETE ELEMENT
    public void deleteScope(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCOPES_LIST, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }


    /**********************************************************************************************
     SAVINGS METHODS
     **********************************************************************************************/
    //SELECT DISTINCT strftime('%m/%Y', date) as "date" FROM outgoing UNION SELECT DISTINCT strftime('%m/%Y', date) as "date" FROM income ORDER BY date DESC
    public ArrayList<String> getDistinctDates(){
        String query = "SELECT DISTINCT strftime('%Y-%m', "+KEY_DATE+") as '"+KEY_DATE+"' FROM "+TABLE_OUTGOING+
                " UNION SELECT DISTINCT strftime('%Y-%m', "+KEY_DATE+") as '"+KEY_DATE+"'  FROM "+TABLE_INCOME+"  ORDER BY date DESC";
        ArrayList<String> queryResult = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            do{
                queryResult.add(c.getString(c.getColumnIndex(KEY_DATE)));
            }while (c.moveToNext());
        }
        return queryResult;
    }
//
//    incomeQuery = "SELECT SUM("+KEY_AMOUNT+") AS "+ KEY_AMOUNT+" FROM "+TABLE_INCOME+" WHERE "+KEY_DATE+" LIKE '"+distinctDates.get(i)+"%'";
//    outgoingQuery = "SELECT SUM("+KEY_AMOUNT+") AS "+ KEY_AMOUNT+" FROM "+TABLE_OUTGOING+" WHERE "+KEY_DATE+" LIKE '"+distinctDates.get(i)+"%'";

    public float getIncomeByDate(String date){
        String query = "SELECT SUM("+KEY_AMOUNT+") AS "+ KEY_AMOUNT+" FROM "+TABLE_INCOME+" WHERE "+KEY_DATE+" LIKE '"+date+"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Log.e(LOG, query);
        return cursor.getFloat(cursor.getColumnIndex(KEY_AMOUNT));
    }
    public float getOutgoingByDate(String date){
        String query = "SELECT SUM("+KEY_AMOUNT+") AS "+ KEY_AMOUNT+" FROM "+TABLE_OUTGOING+" WHERE "+KEY_DATE+" LIKE '"+date+"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Log.e(LOG, query);
        return cursor.getFloat(cursor.getColumnIndex(KEY_AMOUNT));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
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
