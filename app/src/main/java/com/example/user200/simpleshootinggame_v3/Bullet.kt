package com.example.user200.simpleshootinggame_v3

import android.widget.ImageView

public class Bullet(imageView: ImageView,height:Int) {

    val image = imageView
    val screenHeight = height
    var status = "stop"

    public fun move(y:Int){

        image.y = image.y - y

        if(image.y <= 0){  //画面の上端になったら
            //image.visibility = View.INVISIBLE  // 非表示にする。
            this.status = "stop"
            image.x = 0F  // 位置を左下にする
            image.y = screenHeight.toFloat() * 0.7F  // 位置を左下にする
        }

    }
}