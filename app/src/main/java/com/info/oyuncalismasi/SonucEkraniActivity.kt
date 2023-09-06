package com.info.oyuncalismasi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sonuc_ekrani.*

class SonucEkraniActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sonuc_ekrani)

        val skor = intent.getIntExtra("skor",0)
        textViewToplamSkor.text = skor.toString()

        val sp = getSharedPreferences("Sonuc", Context.MODE_PRIVATE)
        val enYuksekSkor = sp.getInt("enYuksekSkor", 0)

        if (skor > enYuksekSkor){

            val editor = sp.edit()
            editor.putInt("enYuksekSkor", skor)
            editor.commit()

            textViewEnYuksekSkor.text = skor.toString()
        }else{
            textViewEnYuksekSkor.text = enYuksekSkor.toString()
        }

        buttonTekrarDene.setOnClickListener {
            startActivity(Intent(this@SonucEkraniActivity,MainActivity::class.java))
            finish()
        }
    }
}

