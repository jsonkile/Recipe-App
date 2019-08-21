package com.golde.androidtest.Util

import java.lang.StringBuilder

class Stringer {

    fun list2String(list: List<String>) : String{
        val result = StringBuilder()
        if (list.size != 1) {
            list.forEachIndexed { index, s ->
                when (index) {
                    0 -> result.append("$s,")
                    list.size-1 -> result.append(" $s.")
                    else -> result.append(" $s,")
                }
            }
            return result.toString()
        }
        return list[0]
    }
}