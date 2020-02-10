/*--------------------------------------------------------------------------------------------------
 -   Created by Jessé Manarim on 9/10/19 2:57 PM
 -   Copyright (c) 2019 . All rights reserved.
 -   Last modified 9/10/19 2:57 PM
 -------------------------------------------------------------------------------------------------*/
package com.example.testew2o.interfaces

import com.example.testew2o.model.Item

/**
 * Interface para click no menu de contexto nos itens do adapter
 **/
interface ItemClickListener {
    fun onItemShare(item : Item)
    fun onItemCreateEvent(item : Item)
    fun onItemExclude(item : Item)
}