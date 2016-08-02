package david.chequeme;

import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CheqDB.db";
    public static final String TRANSACTION_TABLE_NAME = "Transactions";
    public static final String TRANSACTION_COLUMN_ID = "id";
    public static final String TRANSACTION_COLUMN_NAME = "Name";
    public static final String TRANSACTION_COLUMN_TYPE = "Type";
    public static final String TRANSACTION_COLUMN_DATE = "Date";
    public static final String TRANSACTION_COLUMN_PRICE = "Price";
    public static final String TRANSACTION_COLUMN_DBCR = "DbCr";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE Transactions " + "(id integer primary key, Name text, Type text, Date text, Price decimal, DbCr text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        onCreate(db);
    }

    public void dropTransactions()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        onCreate(db);
    }


    public boolean insertTransaction  (String name, String type, String date, BigDecimal price,String dbcr)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Type", type);
        contentValues.put("Date", date);
        contentValues.put("Price", price.toString());
        contentValues.put("DbCr", dbcr);
        db.insert("Transactions", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Transactions where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TRANSACTION_TABLE_NAME);
        return numRows;
    }

    public boolean updateTransaction (Integer id, String name, String type, String date, BigDecimal price, String dbcr)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Type", type);
        contentValues.put("Date", date);
        contentValues.put("Price", price.toString());
        contentValues.put("DbCr", dbcr);
        db.update("Transactions", contentValues, "id = ? ", new String[] { Integer.toString(id) } );


        return true;
    }

    public String getBalance()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(Price) FROM Transactions", null);
        cursor.moveToFirst();

        if(cursor != null ) {
            return cursor.getString(0);
        }
        else
        {
            Log.v("NOTHING FOUND", "312321321321321321313");
        }
        return "Nothing";
    }


    // This method selects everything in the DB and puts into ArrayList
    public ArrayList<String> getAllTransactions()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM Transactions", null );

        if (res.moveToFirst()) {
            do {
                array_list.add(
                        "Name:  " + res.getString(1) + "\n" +
                        "Type:    " + res.getString(2) + "\n" +
                        "Date:    " + res.getString(3) + "\n" +
                        "Price:   €" + res.getString(4));
            } while (res.moveToNext());
        }
        return array_list;
    }

    // Method that gets Price from Database and allows Line Chat to use
    public BigDecimal[] getLineChartTransactions()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        BigDecimal[] temp = new BigDecimal[10];

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM (SELECT * FROM Transactions ORDER BY id DESC LIMIT 10) sub ORDER BY id ASC", null );
        int n = 0;
        if (res.moveToFirst()) {
            do {
                temp[n] = new BigDecimal(res.getString(4));
                n++;
            } while (res.moveToNext());
        }
        return temp;
    }
}