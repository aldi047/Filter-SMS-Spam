package com.example.chaquopy
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_HOME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                SENDER + " TEXT," +
                MESSAGE + " TEXT" + ")")

        val query2 = ("CREATE TABLE " + TABLE_SPAM + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                SENDER + " TEXT," +
                MESSAGE + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
        db.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOME)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPAM)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addData(table: String, sender : String, message : String ): Int{

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(SENDER, sender)
        values.put(MESSAGE, message)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        var id = db.insert(table, null, values)

        // at last we are
        // closing our database
        db.close()
        return id.toInt()
    }

    // below method is to get
    // all data from our database
    fun getData(tableName: String): ArrayList<Message> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + tableName, null)
        val messageArrayList: ArrayList<Message> = ArrayList()
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                messageArrayList.add(Message(
                    cursor.getString(0).toInt(),
                    cursor.getString(1),
                    cursor.getString(2))
                );
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursor.close();
        return messageArrayList;
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "aldiazmi"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_HOME = "sms_home"
        val TABLE_SPAM = "sms_spam"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for sender column
        val SENDER = "sender"

        // below is the variable for message column
        val MESSAGE = "message"
    }
}