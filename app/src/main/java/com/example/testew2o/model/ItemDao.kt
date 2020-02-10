package com.example.testew2o.model
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO de item
 * */
@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll(): List<Item>

    @Query("SELECT * FROM Item WHERE nome_item LIKE :first LIMIT 1")
    fun findByName(first: String): Item

    @Insert
    fun insertAll(vararg clientes: Item)

    @Delete
    fun delete(cliente: Item)
}