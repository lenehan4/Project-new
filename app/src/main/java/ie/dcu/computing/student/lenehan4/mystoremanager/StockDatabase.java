package ie.dcu.computing.student.lenehan4.mystoremanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StockDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "items.db";
    public static final String TABLE_NAME = "items_table";
    public static final String COL_1 = "UPC";
    public static final String COL_2 = "Brand";
    public static final String COL_3 = "Description";
    public static final String COL_4 = "Quantity";
    public static final String COL_5 = "Price";


    public StockDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (UPC TEXT, BRAND TEXT, DESCRIPTION TEXT, QUANTITY TEXT, PRICE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String UPC, String Brand, String Description, String Quantity, String Price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, UPC);
        contentValues.put(COL_2, Brand);
        contentValues.put(COL_3, Description);
        contentValues.put(COL_4, Quantity);
        contentValues.put(COL_5, Price);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result== -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,  null);
        return res;
    }
}
