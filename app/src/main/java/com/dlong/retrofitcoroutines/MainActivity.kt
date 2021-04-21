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

        HLHttpManager.instance().initManager(true, "https://eim-test-api.bds100.com/")

        GlobalScope.launch {
            val result = HLHttpManager.instance().mainApiWorker
                .appLogin(this@MainActivity, "11012345", "111111", "111111")
            Log.e("测试1", result)
        }
    }
}