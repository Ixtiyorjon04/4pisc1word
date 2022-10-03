package uz.gita.a4pisc1word

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), GameContract.View {
    private val answerButtons: MutableList<Button> = ArrayList()
    private val variantButtons: MutableList<Button> = ArrayList()
    private var imageQuestion1: ImageView? = null
    private var imageQuestion2: ImageView? = null
    private var imageQuestion3: ImageView? = null
    private var imageQuestion4: ImageView? = null
    private var level: TextView? = null
    private var money: TextView? = null
    var imag4_res = 0
    var imag1_res = 0
    var imag2_res = 0
    var imag3_res = 0
    private var presenter: GameContract.Presenter? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        presenter = GameManager(GameRepository(), this)
        presenter?.m= sharedPreferences!!.getInt("money", 100)
        presenter?.l=sharedPreferences!!.getInt("level", 0)
        val chiqish = findViewById<ImageView>(R.id.back_btn)
        chiqish.setOnClickListener { v: View? ->
            val builder =
                AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
            val customLayout =
                layoutInflater.inflate(R.layout.menyu_chiqish, null)
            val yes1 = customLayout.findViewById<TextView>(R.id.btnPositive)
            yes1.setOnClickListener { b: View? -> finish() }
            val no1 = customLayout.findViewById<TextView>(R.id.btnNegative)
            no1.setOnClickListener { b: View? -> builder.hide() }
            builder.setView(customLayout)
            builder.setCancelable(false)
            builder.show()
        }
        level = findViewById(R.id.level)
        money = findViewById(R.id.coin)
        imageQuestion1 = findViewById(R.id.img_question1)
        imageQuestion2 = findViewById(R.id.img_question2)
        imageQuestion3 = findViewById(R.id.img_question3)
        imageQuestion4 = findViewById(R.id.img_question4)
        loadButtons2(answerButtons, R.id.layout_answer)
        loadButtons(variantButtons, R.id.layout_variant1)
        loadButtons(variantButtons, R.id.layout_variant2)
        presenter!!.init()
        setImageZoom()
    }

    fun setImageZoom() {
        val zoomImage = findViewById<ImageView>(R.id.zoom_img)
        imageQuestion1!!.setOnClickListener { v: View? ->
            findViewById<View>(R.id.zoom_view).visibility = View.VISIBLE
            Log.d("QWE", "dsnds")
            zoomImage.setImageResource(imag1_res)
            findViewById<View>(R.id.clicked_img).visibility = View.GONE
        }
        imageQuestion2!!.setOnClickListener { v: View? ->
            findViewById<View>(R.id.zoom_view).visibility = View.VISIBLE
            zoomImage.setImageResource(imag2_res)
            findViewById<View>(R.id.clicked_img).visibility = View.GONE
        }
        imageQuestion3!!.setOnClickListener { v: View? ->
            findViewById<View>(R.id.zoom_view).visibility = View.VISIBLE
            zoomImage.setImageResource(imag3_res)
            findViewById<View>(R.id.clicked_img).visibility = View.GONE
        }
        imageQuestion4!!.setOnClickListener { v: View? ->
            findViewById<View>(R.id.zoom_view).visibility = View.VISIBLE
            zoomImage.setImageResource(imag4_res)
            findViewById<View>(R.id.clicked_img).visibility = View.GONE
        }
        findViewById<View>(R.id.zoom_view).setOnClickListener { v: View? ->
            findViewById<View>(R.id.zoom_view).visibility = View.GONE
            findViewById<View>(R.id.clicked_img).visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        loadLevel(presenter!!.l)
        loadMoney(presenter!!.m)
    }

    override fun onPause() {
        sharedPreferences!!.edit().putInt("money", presenter!!.m).apply()
        sharedPreferences!!.edit().putInt("level", presenter!!.l).apply()
        super.onPause()
    }

    private fun loadButtons(buttons: MutableList<Button>, groupId: Int) {
        val group = findViewById<ViewGroup>(groupId)
        findViewById<View>(R.id.help).setOnClickListener { v: View? ->
            presenter?.search(
                this@MainActivity
            )
        }
        val oldSize = buttons.size
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is Button) {
                val button = view
                val index = oldSize + i
                button.setOnClickListener { v: View? ->
                   presenter?.onClickVariant(index)
                }
                buttons.add(button)
            }
        }
    }
    private fun loadButtons2(buttons: MutableList<Button>, groupId: Int) {
        val group = findViewById<ViewGroup>(groupId)
        findViewById<View>(R.id.help).setOnClickListener { v: View? ->
            presenter?.search(
                this@MainActivity
            )
        }
        val oldSize = buttons.size
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is Button) {
                val button = view
                val index = oldSize + i
                button.setOnClickListener { v: View? ->
                   presenter?.onClickAnswer(index)
                }
                buttons.add(button)
            }
        }
    }

    override fun loadImage(resId1: Int, resId2: Int, resId3: Int, resId4: Int) {
        imag1_res = resId1
        imag2_res = resId2
        imag3_res = resId3
        imag4_res = resId4
        imageQuestion1!!.setImageResource(resId1)
        imageQuestion2!!.setImageResource(resId2)
        imageQuestion3!!.setImageResource(resId3)
        imageQuestion4!!.setImageResource(resId4)
    }

    override fun loadLevel(levels: Int) {
        if (levels == 0) {
            level!!.text = "Level " + (levels + 1)
        } else {
            level!!.text = "Level $levels"
        }
    }

    override fun loadMoney(moneys: Int) {
        money!!.text = moneys.toString()
    }

    override fun dialog(count: Int) {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val customLayout = layoutInflater.inflate(R.layout.windi, null)
        val next = customLayout.findViewById<TextView>(R.id.nextl)
        next.setOnClickListener { v: View? -> builder.hide() }
        builder.setView(customLayout)
        builder.setCancelable(false)
        builder.show()
    }

    override fun dialogWin() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val customLayout = layoutInflater.inflate(R.layout.custom_dialog1, null)
        val menyu = customLayout.findViewById<TextView>(R.id.menyu)
        menyu.setOnClickListener { v: View? ->
            presenter!!.l=(0)
            presenter!!.m=(0)
            val a = Intent(this, MenuActivity::class.java)
            startActivity(a)
        }
        builder.setView(customLayout)
        builder.setCancelable(false)
        builder.show()
    }

    override fun dialoghelp() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val customLayout = layoutInflater.inflate(R.layout.windi1, null)
        val yes1 = customLayout.findViewById<TextView>(R.id.yes1)
        yes1.setOnClickListener { v: View? ->
            presenter!!.help()
            builder.hide()
        }
        val no1 = customLayout.findViewById<TextView>(R.id.no1)
        no1.setOnClickListener { v: View? -> builder.hide() }
        builder.setView(customLayout)
        builder.setCancelable(false)
        builder.show()
    }

    override fun dialogno() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val customLayout = layoutInflater.inflate(R.layout.pulno, null)
        val yes1 = customLayout.findViewById<TextView>(R.id.yoq)
        yes1.setOnClickListener { v: View? -> builder.hide() }
        builder.setView(customLayout)
        builder.setCancelable(false)
        builder.show()
    }

    override fun hideAnswer(index: Int) {
        answerButtons[index].visibility = View.GONE
    }

    override fun showAnswer(index: Int) {
        answerButtons[index].visibility = View.VISIBLE
    }

    override fun clearAnswer(index: Int) {
        answerButtons[index].text = ""
    }

    override fun writeAnswer(index: Int, text: String?) {
        answerButtons[index].text = text
    }

    override fun writeVariant(index: Int, text: String?) {
        variantButtons[index].text = text
    }

    override fun showVariant(index: Int) {
        variantButtons[index].visibility = View.VISIBLE
    }

    override fun hideVariant(index: Int) {
        variantButtons[index].visibility = View.INVISIBLE
    }

    override fun isShownVariant(index: Int): Boolean {
        return variantButtons[index].visibility == View.VISIBLE
    }

    internal interface OnClickListener {
        fun onClick(index: Int)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val customLayout = layoutInflater.inflate(R.layout.menyu_chiqish, null)
        val yes1 = customLayout.findViewById<TextView>(R.id.btnPositive)
        yes1.setOnClickListener { b: View? -> finish() }
        val no1 = customLayout.findViewById<TextView>(R.id.btnNegative)
        no1.setOnClickListener { b: View? -> builder.hide() }
        builder.setView(customLayout)
        builder.setCancelable(false)
        builder.show()
    }
}