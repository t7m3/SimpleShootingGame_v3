package com.example.user200.simpleshootinggame_v3

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*

    // 新しいブランチの確認のためのコミット

class MainActivity : AppCompatActivity() {

    private var screenWidth = 0  //スクリーンの幅を格納する変数の宣言
    private var screenHeight = 0   //スクリーンの高さ格納する変数の宣言

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var extendEnemy : ExtendimageView  // <-まずこれを宣言して、エラーをAlt+エンターで直してクラスを作成した
                                           // その時、別ファイルでクラスを作ることを選択した
        extendEnemy = imageViewEnemy as ExtendimageView
        //extendEnemy = findViewById(R.id.imageViewEnemy)

        // スクリーンの幅と高さを取得する
        val dMetrics = DisplayMetrics()  //DisplayMetrics のインスタンスを生成する
        windowManager.defaultDisplay.getMetrics(dMetrics)  //スクリーンサイズを取得しているらしい
        screenWidth = dMetrics.widthPixels  //スクリーンの幅を取得
        screenHeight = dMetrics.heightPixels  //スクリーンの高さを取得

        // imageViewEnemy の初期位置の設定
        //imageViewEnemy.x = 50F
        //imageViewEnemy.y = screenHeight.toFloat() * 0.2F
        extendEnemy.x = 50F
        //extendEnemy.y = screenHeight.toFloat() * 0.2F

        // imageViewPlayer の初期位置の設定
        imageViewPlayer.x = 50F
        imageViewPlayer.y = screenHeight.toFloat() * 0.6F

        // imageViewBullet の初期位置の設定
        imageViewBullet.x = 50F
        imageViewBullet.y = screenHeight.toFloat() * 0.4F

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
