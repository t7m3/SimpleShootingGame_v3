
import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.Log

class ExtendImageView : AppCompatImageView {

    //アクティビティ廃棄時には必ずinitが実行させる
    init {
        Log.v("nullpo","initMyImageView")
    }

    //イベントが発生したときに処理して欲しい内容がlに入っている
    override fun setOnClickListener(l: OnClickListener?) {
                super.setOnClickListener {
                        l?.onClick(it)
                        Log.v("nullpo","onClick")
        }
    }

    //コンストラクタ
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

}