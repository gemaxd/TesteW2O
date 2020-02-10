package com.example.testew2o.util

import com.example.testew2o.model.Item
import java.text.SimpleDateFormat
import java.util.*

/**
 * Classe util para formatação de datas
 * */
class DateUtils {

    public fun formatDate(item : Item) : String {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(item.datItem)
    }

}