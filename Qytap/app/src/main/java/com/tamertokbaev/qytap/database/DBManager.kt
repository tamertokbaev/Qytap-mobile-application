package com.tamertokbaev.qytap.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tamertokbaev.qytap.models.User

class DBManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Qytap"
        private val USERS_TABLE = "users"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS $USERS_TABLE (\n" +
                "\tid int PRIMARY KEY,\n" +
                "\tfullName VARCHAR NOT NULL,\n" +
                "\temail VARCHAR NOT NULL,\n" +
                "\tbirthdate DATE DEFAULT NULL,\n" +
                "\tpassword VARCHAR NOT NULL,\n" +
                "\tbalance DOUBLE DEFAULT 0,\t\n" +
                "\tlastLogin TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                "\tcreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                "\tdeletedAt TIMESTAMP DEFAULT NULL\n" +
                ");"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val upgrade = "DROP TABLE IF EXISTS $USERS_TABLE"
        db!!.execSQL(upgrade)
        onCreate(db)
    }

    // DataBase manipulation functions:

    // TODO 29.11.2021

    // Adding new User
    fun addNewUser(user: User):Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", user.id)
        contentValues.put("fullName", user.fullName)
        contentValues.put("email", user.email)
        contentValues.put("password", user.password)

        // Inserting row and preparing SQL query
        val success = db.insert(USERS_TABLE, null, contentValues)
        db.close()
        return success
    }

    // Authenticating User
    fun checkCredentialsUser(){

    }

    // Get User data
    fun getUserData(){

    }

    // Manipulating User Data
    fun manipulateUserData() {

    }
}