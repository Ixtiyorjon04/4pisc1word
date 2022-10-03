package uz.gita.a4pisc1word

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        onResume()
        val tests = findViewById<TextView>(R.id.tests)
        val about = findViewById<TextView>(R.id.Abouit)
        val quit = findViewById<TextView>(R.id.qoit)
        tests.setOnClickListener { v: View? ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        about.setOnClickListener { v: View? ->
            val intent = Intent(this, info1::class.java)
            startActivity(intent)
        }
        quit.setOnClickListener { v: View? ->
            val builder =
                AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
            val customLayout =
                layoutInflater.inflate(R.layout.menyu_chiqish, null)
            val yes1 = customLayout.findViewById<TextView>(R.id.btnPositive)
            yes1.setOnClickListener { b: View? -> finishAffinity() }
            val no1 = customLayout.findViewById<TextView>(R.id.btnNegative)
            no1.setOnClickListener { b: View? -> builder.hide() }
            builder.setView(customLayout)
            builder.setCancelable(false)
            builder.show()
        }
    }

    override fun onResume() {
        val diamond = findViewById<TextView>(R.id.coin)
        diamond.text = "" + getSharedPreferences("shared", MODE_PRIVATE).getInt("money", 100)
        super.onResume()
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