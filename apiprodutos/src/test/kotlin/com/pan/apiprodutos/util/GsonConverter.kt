package com.pan.apiprodutos.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pan.apiprodutos.entity.Produto
import java.lang.reflect.Type

class GsonConverter {

    companion object{
        fun <T> getList(jsonArray: String?, clazz: Class<T>?): List<T>? {
            val typeOfT: Type = TypeToken.getParameterized(MutableList::class.java, clazz).getType()
            return Gson().fromJson(jsonArray, typeOfT)
        }

        fun <T> getObject(json: String, clazz: Class<T>): T {
            val typeOfT = object : TypeToken<Produto?>() {}.type
            return Gson().fromJson(json, typeOfT)
        }
    }
}