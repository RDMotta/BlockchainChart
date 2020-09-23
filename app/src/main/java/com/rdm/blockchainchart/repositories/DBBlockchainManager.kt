package com.rdm.blockchainchart.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rdm.blockchainchart.R

class DBBlockchainManager {

    private val dbName = "blockchainchartdb"
    private val dbTable = "transaction_coin"
    private val dbVersion = 1
    private var db: SQLiteDatabase? = null
    companion object Column {
       val id: String = "Id"
       val period: String = "period"
       val value_coin: String = "value_coin"
    }

    constructor(context: Context) {
        var dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(values: ContentValues): Long {

        val ID = db!!.insert(dbTable, "", values)
        return ID
    }

    fun getLast(): Cursor {
        return db!!.rawQuery("select * from " + dbTable + " ORDER BY Id DESC LIMIT 1", null)
    }

    inner class DatabaseHelper : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            for (item in context?.resources?.getStringArray(R.array.db_create_table)!!) {
                db!!.execSQL(item)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            for (item in context?.resources?.getStringArray(R.array.db_drop_table)!!) {
                db!!.execSQL(item)
            }
        }
    }}