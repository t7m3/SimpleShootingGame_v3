package com.example.user200.simpleshootinggame_v3

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.Log

class ExtendimageView : AppCompatImageView {
    //アクティビティ廃棄時には必ずinitが実行させる
    init {
        //アクティビティ側にコールバックしない場合は、ここでクリックイベントに関する処理を記述する
        //super.setOnClickListener {
        //}
        Log.v("nullpo","initMyImageView")
    }

    //イベントが発生したときに処理して欲しい内容がlに入っている
    override fun setOnClickListener(l: OnClickListener?) {
        //イベント発生時にこれをやってねと伝える
        super.setOnClickListener {
            //アクティビティに記述したボタンを押されたときにやって欲しい処理
            l?.onClick(it)
            //ここで処理したいことを追加でここに記述しておく　他のメソッド呼び出しでもいい
            Log.v("nullpo","onClick")
        }
    }

    //コンストラクタ
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}
