/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui

import com.github.tehras.restapi.tmdb.Discover

/**
 * @author tkoshkin created on 8/27/18
 */

fun convertToList(map: MutableMap<Int, MutableList<Discover>>): MutableList<Any> {
    val list = mutableListOf<Any>()

    map.forEach {
        if (it.key == 0) {
            return@forEach
        }
        list.add(it.key)
        if (it.value.size > 3) {
            list.addAll(it.value.subList(0, 3))
        } else {
            list.addAll(it.value)
        }
    }

    return list
}