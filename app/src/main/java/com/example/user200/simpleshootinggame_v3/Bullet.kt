package com.example.user200.simpleshootinggame_v3

import android.widget.ImageView

public class Bullet(image:ImageView, height:Int) {

    public val imageView = image
    private val screenHeight = height

    public var state = "stop"

    public fun move(y:Int){

        imageView.y = imageView.y - y

        if(imageView.y <= 0){  //画面の上端になったら
            //image.visibility = View.INVISIBLE  // 非表示にする。
            state = "stop"
            imageView.x = 0F  // 位置を左下にする
            imageView.y = screenHeight.toFloat() * 0.7F  // 位置を左下にする
        }

    }
}