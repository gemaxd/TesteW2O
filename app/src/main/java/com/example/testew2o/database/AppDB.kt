/*--------------------------------------------------------------------------------------------------
 -   Created by Jessé Manarim on 9/12/19 11:02 AM
 -   Copyright (c) 2019 . All rights reserved.
 -   Last modified 9/12/19 11:02 AM
 -------------------------------------------------------------------------------------------------*/

package com.example.testew2o.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testew2o.model.Item
import com.example.testew2o.model.ItemDao

/**
 * Classe reposavel pela base de dados e por instanciar o DAO pertinente ao ITEM
 *
 * o banco utilizado é o ROOM.
 * */
@Database(entities = arrayOf( Item::class), version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        @JvmStatic
        fun getAppDatabase(context: Context): AppDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase::class.java, "BASE_MSCORE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}