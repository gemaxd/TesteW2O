package com.example.testew2o.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Entidade de ITEM
 */
@Entity
class Item {

    @PrimaryKey
    @ColumnInfo(name = "item_hash")
    var itemId: String = ""

    @NotNull
    @ColumnInfo(name = "nome_item")
    var nomItem: String = ""

    @ColumnInfo(name = "desc_item")
    var descItem: String = ""

    @ColumnInfo(name = "valor")
    var valorItem: String = ""

    @ColumnInfo(name = "data")
    var datItem: Long = 0L

    @ColumnInfo(name = "categoria")
    var catItem: String = ""

    override fun toString(): String {
        return "Item selecionado\n\nNOME:$nomItem \nVALOR:$valorItem \nDATA:$datItem \nCATEGORIA:$catItem \nDESCRIÇÃO:$descItem"
    }
}