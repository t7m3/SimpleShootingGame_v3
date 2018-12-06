package com.example.user200.simpleshootinggame_v3

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

    // 前のブランチの確認のためのコミット

class MainActivity : AppCompatActivity() {

    private var screenWidth = 0  //スクリーンの幅を格納する変数の宣言
    private var screenHeight = 0   //スクリーンの高さ格納する変数の宣言

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // スクリーンの幅と高さを取得する
        val dMetrics = DisplayMetrics()  //DisplayMetrics のインスタンスを生成する
        windowManager.defaultDisplay.getMetrics(dMetrics)  //スクリーンサイズを取得しているらしい
        screenWidth = dMetrics.widthPixels  //スクリーンの幅を取得
        screenHeight = dMetrics.heightPixels  //スクリーンの高さを取得

        // imageViewEnemy の初期位置の設定
        imageViewEnemy.x = 50F
        imageViewEnemy.y = screenHeight.toFloat() * 0.2F

        // imageViewPlayer の初期位置の設定
        imageViewPlayer.x = 50F
        imageViewPlayer.y = screenHeight.toFloat() * 0.6F

        // imageViewBullet の初期位置の設定と visibilityの設定
        imageViewBullet.x = 50F
        imageViewBullet.y = screenHeight.toFloat() * 0.4F
        imageViewBullet.visibility = View.INVISIBLE


        // タイマのインスタンスの生成
        val timer = MyCountDownTimer(5 * 60 * 1000, 10)
        timerText.text = "5:00"

        // タイマのスタート
        timer.start()  // <- これで十分。
        //timer.apply { start() }  // <- これでもできる。教科書はこれ。
    }

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
                                                CountDownTimer(millisInFuture, countDownInterval) {

        private var dirEnemy = 1  //imageViewEnemy の 方向を保存する変数。＋１で右。－１で左。

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000 % 60
            timerText.text = "%1d:%2$02d".format(minute, second)

            // imageViewEnemy を左右に移動する
            dirEnemy = moveEnemy(5, dirEnemy)

            // imageViewBullet を上に移動する
            if(imageViewBullet.visibility  == View.VISIBLE){
                moveBullet(5)
            }
        }

        override fun onFinish() {
            //timerText.text = "0:00"
            timerText.text = "--:--"                                                         //デバッグ用

        }

    }

    //imageViewEnemyが左右に移動するメソッド
    fun moveEnemy(x:Int , dir:Int ): Int{

        var ret = dir

        imageViewEnemy.x = imageViewEnemy.x + x * dir

        if(imageViewEnemy.x < 0 ||  screenWidth - imageViewEnemy.width < imageViewEnemy.x ){
            ret = dir * -1;  //移動の左右の向きを反転する
        }

        return ret

    }

    //imageViewBulletが上に移動するメソッド
    fun moveBullet(y:Int){

        imageViewBullet.y = imageViewBullet.y - y
    }

    //画面タッチのメソッドの定義
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val ex = event.x  //タッチした場所のＸ座標
        val ey = event.y  //タッチした場所のＹ座標

        textView.text = "X座標：$ex　Y座標：$ey"

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                textView.append("　ACTION_DOWN")
                imageViewPlayer.x = ex
            }

            MotionEvent.ACTION_UP -> {
                textView.append("　ACTION_UP")
                imageViewBullet.visibility = View.VISIBLE

            }

            MotionEvent.ACTION_MOVE -> {
                textView.append("　ACTION_MOVE")
                imageViewPlayer.x = ex
            }

            MotionEvent.ACTION_CANCEL -> {
                textView.append("　ACTION_CANCEL")
            }
        }

        return true

    }
}
