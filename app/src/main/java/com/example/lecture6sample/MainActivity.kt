package com.example.lecture6sample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun save(isLogged:Boolean, userName:String){
        val sharedPref = this
            .getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putBoolean("isUserLoggedIn", isLogged)
        editor?.putString("username", userName)
        editor?.apply()
    }
    fun getUser(){
        val sharedPref = this
            .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isUserLoggedIn = sharedPref?.getBoolean("isUserLoggedIn", false) ?: false
        val username = sharedPref?.getString("username", "")
    }

    fun writeToFile(){
        val filename = "myfile.txt"
        val fileContents = "Hello world!"

        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it?.write(fileContents.toByteArray())
        }
    }

    fun readFromFile(){
        val filename = "myfile.txt"
        this.openFileInput(filename).use {
            val fileContents = it?.bufferedReader().use {
                it?.readText()
            }
            Log.d("MainActivity", "File contents: $fileContents")
        }
    }

    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    private fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
    }

    fun writeToExternalFile(){
        val filename = "myfile.txt"
        val fileContents = "Hello world!"

        if (isExternalStorageWritable()) {
            val file = File(getExternalFilesDir(null), filename)
            FileOutputStream(file).use {
                it.write(fileContents.toByteArray())
            }
        }

    }

    fun readFromExternalFile(){
        val filename = "myfile.txt"

        if (isExternalStorageReadable()) {
            val file = File(getExternalFilesDir(null), filename)
            FileInputStream(file).use {
                val fileContents = it.bufferedReader().use {
                    it.readText()
                }
                Log.d("Main Activity", "File contents: $fileContents")
            }
        }

    }
}



