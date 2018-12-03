package com.example.user200.simpleshootinggame_v3

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // タイマのインスタンスの生成
        val timer = MyCountDownTimer(5 * 60 * 1000, 100)
        timerText.text = "5:00"

        // タイマのスタート
        timer.start()  // <- これで十分。
        //timer.apply { start() }  // <- これでもできる。教科書はこれ。
    }

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000 % 60
            timerText.text = "%1d:%2$02d".format(minute, second)

        }

        override fun onFinish() {
            //timerText.text = "0:00"
            timerText.text = "--:--"                                                         //デバッグ用

        }

    }
}
