package uz.gita.a4pisc1word

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.TextView

class GameManager(var model: GameContract.Model, var view: GameContract.View) :
    GameContract.Presenter {

    override var l = 0
    override var m = 0
    private var GameContract=GameContract()
    private var gameData: GameData? = null
    private lateinit var userAnswer: Array<String?>
    var moneyt: TextView? = null
    var sharedPreferences: SharedPreferences? = null
    override fun init() {
        if (l > model.QuustionsSize() - 1) {
            view.dialogWin()
        } else {
            view.loadLevel(l + 1)
            gameData = model.get(l)
            view.loadImage(gameData!!.question1, gameData!!.question2, gameData!!.question3, gameData!!.question4)
            val size: Int = gameData!!.answer.length
            for (i in 0 until GameContract.MAX_ANSWER) {
                if (i < size) {
                    view.showAnswer(i)
                    view.clearAnswer(i)
                } else {
                    view.hideAnswer(i)
                }
            }
            for (i in 0 until GameContract.VARIANT_COUNT) {
                val text = getVariant(i)
                view.writeVariant(i, text)
                view.showVariant(i)
            }
            userAnswer = arrayOfNulls(gameData!!.answer.length)
        }
    }

    override fun help() {
        if (m >= 60) {
            m -= 60
            view.loadMoney(m)
            val ans: String = model.get(l)!!.answer
            var index = 0
            for (i in userAnswer.indices) {
                if (userAnswer[i] == null) {
                    index = i
                    break
                }
            }
            for (i in 0 until model.get(l)!!.variant.length) {
                if (model.get(l)!!.variant[i] === ans[index]) {
                    index = i
                    break
                }
            }
            onClickVariant(index)
        }
    }

    private fun getVariant(index: Int): String {
        val variants: String = gameData!!.variant
        return variants[index].toString()
    }

  override  fun onClickAnswer(index: Int) {
        val text = userAnswer[index]
        if (text != null) {
            for (i in 0 until GameContract.VARIANT_COUNT) {
                val textVariant = getVariant(i)
                if (text == textVariant && !view.isShownVariant(i)) {
                    view.showVariant(i)
                    view.clearAnswer(index)
                    userAnswer[index] = null
                    return
                }
            }
        }
    }

    override fun onClickVariant(index: Int) {
        val answerIndex = findFirstEmptyIndex()
        if (answerIndex != -1) {
            view.hideVariant(index)
            val text = getVariant(index)
            view.writeAnswer(answerIndex, text)
            userAnswer[answerIndex] = text
            Log.d("t", userAnswer.toString())
            var textt: String? = ""
            if (findFirstEmptyIndex() == -1) {
                for (str in userAnswer) {
                    textt += str
                }
                if (textt == model.get(l)!!.answer) {
                    Log.d("t", userAnswer.toString())
                    view.dialog(l)
                    m += 10
                    l++
                    view.loadMoney(m)
                    if (l == model.QuustionsSize()) {
                        Log.d("T", "" + model.QuustionsSize())
                        view.dialogWin()
                    }
                    if (l > model.QuustionsSize() - 1) {
                        view.dialogWin()
                    }
                    init()
                }
            }
        }
    }

  override  fun search(context: Context?) {
        if (m >= 60) {
            view.dialoghelp()
        } else {
            view.dialogno()
        }
    }

    private fun findFirstEmptyIndex(): Int {
        for (i in userAnswer.indices) {
            if (userAnswer[i] == null) {
                return i
            }
        }
        return -1
    }

    init {
        this.model = model
        this.view = view
    }
}
