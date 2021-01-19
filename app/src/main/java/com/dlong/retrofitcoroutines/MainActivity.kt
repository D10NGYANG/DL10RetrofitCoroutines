package com.dlong.retrofitcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HLHttpManager.instance().initManager(true)

        GlobalScope.launch {
            val result = HLHttpManager.instance().mainApi.searchUser("13106673302").body
            Log.e("测试", result.toString())
        }
    }
}