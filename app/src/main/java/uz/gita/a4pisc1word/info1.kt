package uz.gita.a4pisc1word

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class info1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info1)
        val chiqish = findViewById<ImageView>(R.id.chqish)
        chiqish.setOnClickListener { v: View? -> finish() }
        loadViews()
    }

    private fun loadViews() {
        findViewById<View>(R.id.chqish).setOnClickListener { v: View? -> finish() }
    }
}