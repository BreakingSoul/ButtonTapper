package com.breaksol.buttontapper

import android.content.Context
import android.content.SharedPreferences


class PreferencesUtils {

    companion object {
        fun saveRows(ctx: Context, rows: Int) {
            val sharedPref: SharedPreferences = ctx.getSharedPreferences("settings", 0)
            val editor = sharedPref.edit()
            editor.putInt("rows", rows)
            editor.apply()
        }

        fun getRows(ctx: Context): Int {
            val sharedPref: SharedPreferences = ctx.getSharedPreferences("settings", 0)
            return sharedPref.getInt("rows", 5)
        }

        fun saveColumns(ctx: Context, columns: Int) {
            val sharedPref: SharedPreferences = ctx.getSharedPreferences("settings", 0)
            val editor = sharedPref.edit()
            editor.putInt("columns", columns)
            editor.apply()
        }

        fun getColumns(ctx: Context): Int {
            val sharedPref: SharedPreferences = ctx.getSharedPreferences("settings", 0)
            return sharedPref.getInt("columns", 3)
        }
    }

}