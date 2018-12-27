package com.example.user200.simpleshootinggame_v3

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget



    // 前のブランチの確認のためのコミット

public class MainActivity : AppCompatActivity() {

    private var screenWidth = 0  //スクリーンの幅を格納する変数の宣言
    private var screenHeight = 0   //スクリーンの高さ格納する変数の宣言
    private lateinit var imageBullet :Bullet  //クラスBulletの実験

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var target = GlideDrawableImageViewTarget(imageViewGif)  // gifアニメーション の実験
        Glide.with(this).load(R.raw.daibakuhatsu).into(target);  // gifアニメーション の実験
        imageViewGif.visibility = View.INVISIBLE  // gifアニメーション の実験

        //スコアの初期設定。０にする。
        textViewScore.text = "0"

        textViewScore.bringToFront()
        textView.bringToFront()
        timerText.bringToFront()

        // スクリーンの幅と高さを取得する
        val dMetrics = DisplayMetrics()  //DisplayMetrics のインスタンスを生成する
        windowManager.defaultDisplay.getMetrics(dMetrics)  //スクリーンサイズを取得しているらしい
        screenWidth = dMetrics.widthPixels  //スクリーンの幅を取得
        screenHeight = dMetrics.heightPixels  //スクリーンの高さを取得

        // imageViewEnemy の初期位置の設定
        imageViewEnemy.x = 50F
        imageViewEnemy.y = screenHeight.toFloat() * 0.2F
        imageViewEnemy.tag = "move"

        // imageViewPlayer の初期位置の設定
        imageViewPlayer.x = 50F
        imageViewPlayer.y = screenHeight.toFloat() * 0.6F

        imageBullet = Bullet(imageViewBullet, screenHeight)  //クラスBulletの実験

        // imageViewBullet の初期位置の設定と visibilityの設定
        imageBullet.imageView.x = 50F
        imageBullet.imageView.y = screenHeight.toFloat() * 0.4F
        //imageViewBullet.visibility = View.INVISIBLE
        //imageViewBullet.tag = "stop"

        //var imageArray: Array<ImageView?> = arrayOfNulls(3)
        //val imageArray: Array<ImageView?> = arrayOfNulls(100)
/*        val imageArray = arrayOfNulls<ImageView?>(5)  //配列の宣言
        //imageArray[0] = ImageView(this)
        //imageArray[0]!!.setImageResource(R.drawable.arw03up)
        for (i in imageArray.indices){
            imageArray[i] = ImageView(this)  //インスタンスの生成
            imageArray[i]!!.setImageResource(R.drawable.arw02up)  //画像を設定する
            imageArray[i]!!.x = i * 50F
            imageArray[i]!!.y = screenHeight.toFloat() * 0.8F
            layout.addView(imageArray[i])  // 画面（layout）に追加する
        }
*/

        // Bulletクラス配列化の実験
        val imageArray = arrayOfNulls<Bullet?>(5)  //配列の宣言
        var image : ImageView
        for (i in imageArray.indices){
            image = ImageView(this)  //インスタンスの生成
            image.setImageResource(R.drawable.arw02up)  //画像を設定する
            image.x = i * 50F
            image.y = screenHeight.toFloat() * 0.8F
            imageArray[i] = Bullet(image, screenHeight)
            layout.addView(imageArray[i]!!.imageView)  // 画面（layout）に追加する
        }

        //val imageView = ImageView(this)  // ImageViewのインスタンス生成
        //imageView.setImageResource(R.drawable.arw03up)  // 生成したインスタンスに画像を指定する
        //layout.addView(imageView)  // 生成したインスタンスをlayoutに追加する

        //こんなこともできる・・・画面で作ったインスタンスを配列に格納して使う
        val imageArrayA = arrayOfNulls<ImageView?>(5)
        imageArrayA[0] = imageViewBullet
        imageArrayA[0]!!.x = 0F
        imageArrayA[0]!!.y = screenHeight.toFloat() * 0.7F

        // タイマのインスタンスの生成
        val timer = MyCountDownTimer(150 * 60 * 1000, 10)
        timerText.text = "150:00"

        // タイマのスタート
        timer.start()  // <- これで十分。
        //timer.apply { start() }  // <- これでもできる。教科書はこれ。
    }

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
                                                CountDownTimer(millisInFuture, countDownInterval) {

        private var dirEnemy = 1  //imageViewEnemy の 方向を保存する変数。＋１で右。－１で左。
        private var gameState = 0  // 時間管理のための変数
        private var explosion_millisUntilFinished = 0L // 爆発したときの時刻（のようなもの）を保存する変数
        private var auto = 1F  //オートプレイ用の変数

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000 % 60
            timerText.text = "%1d:%2$02d".format(minute, second)

            when(gameState++ % 3){
                0 -> {
                    // imageViewEnemy を左右に移動する
                    if (imageViewEnemy.tag == "move")
                        dirEnemy = moveEnemy(5, dirEnemy)
                }
                1 ->{
                    if (explosion_millisUntilFinished - millisUntilFinished >= 3000) {  // 爆発の時間が経過したら
                        gameState = 0  // gameState をシューティングの状態にする

                        // imageViewEnemyの画像をロケットの画像に変える
                        //imageViewEnemy.setImageResource(R.drawable.rocket)
                        imageViewEnemy.visibility = View.VISIBLE
                        imageViewGif.visibility = View.INVISIBLE

                        imageViewEnemy.tag = "move"  // imageViewEnemy が移動する
                    }
                }
                2 ->{}
            }

            // imageViewBullet を上に移動する
            //if(imageViewBullet.tag  == "move"){
            if(imageBullet.state  == "move"){

                //moveBullet(5)
                imageBullet.move(5)  //クラスBulletの実験

                //当たり判定
                if (hit(imageViewEnemy, imageBullet.imageView) == true ){  //当たった

                    imageViewEnemy.tag = "stop"  // 当たったら移動を止める

                    // imageViewEnemyの画像を爆発の画像に変える
                    //imageViewEnemy.setImageResource(R.drawable.s5z8k0g6)
                    imageViewEnemy.visibility = View.INVISIBLE
                    imageViewGif.x = imageViewEnemy.x  // gifアニメーション の実験
                    imageViewGif.y = imageViewEnemy.y  // gifアニメーション の実験
                    imageViewGif.visibility = View.VISIBLE  // gifアニメーション の実験

                    gameState = 1  // gameState を爆発の状態にする
                    explosion_millisUntilFinished = millisUntilFinished// 爆発したときの時刻（のようなもの）を保存しておく

                    //imageViewBullet.tag = "stop"
                    imageBullet.state = "stop" //クラスBulletの実験
                    imageBullet.imageView.x = 0F  // 位置を左下にする
                    imageBullet.imageView.y = screenHeight.toFloat() * 0.7F  // 位置を左下にする

                    //スコアを＋１０する
                    textViewScore.text = (Integer.parseInt(textViewScore.text.toString())+10).toString()
                }
             }
            //オートプレイ
            if ( millisUntilFinished / 1000 % 5 == 0L && imageBullet.state == "stop"){  //一定時間ごとに
                imageBullet.imageView.visibility = View.VISIBLE
                imageBullet.state = "move" //クラスBulletの実験  //弾を発射する
                imageBullet.imageView.x =imageViewPlayer.x + imageViewPlayer.width/2 -imageBullet.imageView.width/2
                imageBullet.imageView.y = imageViewPlayer.y
            }
            imageViewPlayer.x = imageViewPlayer.x + auto
            if (500 < imageViewPlayer.x){
                auto = -1.5F
            }
            else if (imageViewPlayer.x < 100){
                auto = 3F
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
    //fun moveBullet(y:Int){

        //imageViewBullet.y = imageViewBullet.y - y

        //if(imageViewBullet.y <= 0){  //画面の上端になったら
        //    //imageViewBullet.visibility = View.INVISIBLE  // 非表示にする。
        //    imageViewBullet.tag = "stop"
        //    imageViewBullet.x = 0F  // 位置を左下にする
        //    imageViewBullet.y = screenHeight.toFloat() * 0.7F  // 位置を左下にする
        //}
    //}

    //当たり判定のメソッド　当たったら、trueを返す、当たっていなければFalseを返す
    fun hit(enemy: ImageView, bullet: ImageView): Boolean {
        if (enemy.y <= bullet.y && bullet.y <= enemy.y + enemy.height) {
            if (enemy.x <= bullet.x + bullet.width / 2 && bullet.x + bullet.width / 2 <= enemy.x + enemy.width) {
                return true
            }
        }
        return false
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
                imageBullet.imageView.visibility = View.VISIBLE
                imageBullet.state = "move" //クラスBulletの実験
                imageBullet.imageView.x = ex + imageViewPlayer.width/2 -imageBullet.imageView.width/2
                imageBullet.imageView.y = imageViewPlayer.y
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
