package uz.gita.a4pisc1word

import android.content.Context

open class  GameContract {
 open   var MAX_ANSWER = 7
    var VARIANT_COUNT = 12

    interface Model {
        operator fun get(level: Int): GameData?
        fun QuustionsSize(): Int
    }

    interface View {
        fun loadImage(resId1: Int, resId2: Int, resId3: Int, resId4: Int)
        fun loadLevel(level: Int)
        fun loadMoney(money: Int)
        fun dialog(count: Int)
        fun dialogWin()
        fun dialoghelp()
        fun dialogno()
        fun hideAnswer(index: Int)
        fun showAnswer(index: Int)
        fun clearAnswer(index: Int)
        fun writeAnswer(index: Int, text: String?)
        fun writeVariant(index: Int, text: String?)
        fun showVariant(index: Int)
        fun hideVariant(index: Int)
        fun isShownVariant(index: Int): Boolean
    }

    internal interface Presenter {
        fun init()
       open var m: Int
       open var l: Int

        fun help()
        fun onClickAnswer(index: Int)
        fun onClickVariant(index: Int)
       fun search(context: Context?)
    }
}
