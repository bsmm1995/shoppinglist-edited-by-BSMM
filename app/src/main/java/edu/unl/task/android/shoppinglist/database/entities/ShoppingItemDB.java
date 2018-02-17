package edu.unl.task.android.shoppinglist.database.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import java.util.ArrayList;
import edu.unl.task.android.shoppinglist.database.helper.ShoppingElementHelper;
import edu.unl.task.android.shoppinglist.database.model.ShoppingItem;

public class ShoppingItemDB {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static ShoppingElementHelper conn;

    public ShoppingItemDB(Context context) {
        conn = new ShoppingElementHelper(context);
    }

    public static abstract class ShoppingElementEntry implements BaseColumns {
        static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = " title";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + COLUMN_NAME_TITLE + TEXT_TYPE + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public void insertElement(String productName) {
        //TODO: add all the needed code to insert one item in database
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(ShoppingElementEntry.COLUMN_NAME_TITLE, productName);
            db.insert(ShoppingElementEntry.TABLE_NAME, null, values);
            db.close();
        }
    }

    @SuppressLint("Recycle")
    public ArrayList<ShoppingItem> getAllItems() {
        //TODO: add all the needed code to get all the database items
        ArrayList<ShoppingItem> lista = new ArrayList<>();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(ShoppingElementEntry.TABLE_NAME, new String[]{ShoppingElementEntry._ID, ShoppingElementEntry.COLUMN_NAME_TITLE}, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                lista.add(new ShoppingItem(id, title));
            } while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }

    public void clearAllItems() {
        //TODO: add all the needed code to clear all the database items
        SQLiteDatabase db = conn.getWritableDatabase();
        db.delete(ShoppingElementEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void updateItem(ShoppingItem shoppingItem) {
        //TODO: add the needed code to update a database item
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(ShoppingElementEntry.COLUMN_NAME_TITLE, shoppingItem.getTitle());
            db.update(ShoppingElementEntry.TABLE_NAME, values, ShoppingElementEntry._ID + "=?",
                    new String[]{"" + shoppingItem.getId()});
        }
        db.close();
    }

    public void deleteItem(ShoppingItem shoppingItem) {
        //TODO: add all the needed code to delete a database item
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            db.delete(ShoppingElementEntry.TABLE_NAME, ShoppingElementEntry._ID + "=?", new String[]{"" + shoppingItem.getId()});
        }
        db.close();
    }
}
