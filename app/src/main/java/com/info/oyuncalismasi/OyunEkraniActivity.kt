package com.info.oyuncalismasi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_oyun_ekrani.*
import java.util.*

import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunEkraniActivity : AppCompatActivity() {
    //Pozisyonlar
    private var anakarakterX = 0.0f
    private var anakarakterY = 0.0f
    private var siyahkareX = 0.0f
    private var siyahkareY = 0.0f
    private var saridaireX = 0.0f
    private var saridaireY = 0.0f
    private var kirmiziucgenX = 0.0f
    private var kirmiziucgenY = 0.0f

    //Boyutlar
    private var ekranGenisligi = 0
    private var ekranYukseligi = 0
    private var anakarakterGenisligi = 0
    private var anakarakterYuksekligi = 0

    //Kontroller
    private var dokunmaKontrol = false
    private var baslangicKontrol = false

    private val timer = Timer()

    private var skor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyun_ekrani)

        //Cisimleri ekranın dışına çıkarma
        siyahkare.x = -80.0f
        siyahkare.y = -80.0f
        saridaire.x = -80.0f
        saridaire.y = -80.0f
        kirmiziucgen.x = -80.0f
        kirmiziucgen.y = -80.0f

        cl.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if(baslangicKontrol){
                    if(event?.action == MotionEvent.ACTION_DOWN){
                        Log.e("MotionEvent", "ACTION_DOWN : Ekrana dokundu")
                        dokunmaKontrol = true
                    }
                    if(event?.action == MotionEvent.ACTION_UP){
                        Log.e("MotionEvent", "ACTION_UP : Ekranı bıraktı")
                        dokunmaKontrol = false
                    }
                }else{
                    baslangicKontrol = true

                    //Kullanıcı başlama uyarısını görünmez yapar.
                    textViewOyunaBasla.visibility = View.INVISIBLE

                    //Anakarakterin ekrandaki başlama konumuna göre x ve y konumu alındı
                    anakarakterX = anakarakter.x
                    anakarakterY = anakarakter.y

                    anakarakterGenisligi = anakarakter.width
                    anakarakterYuksekligi = anakarakter.height
                    ekranGenisligi = cl.width
                    ekranYukseligi = cl.height

                    timer.schedule(0,20){//0ms geçikmeli 20ms aralıkla tekrarlı çalışır.
                        Handler(Looper.getMainLooper()).post {
                            anakarakterHareketEttirme()
                            cisimlerinHareketEttir()
                            carpismaKontrol()
                        }
                    }
                }
                return true
            }
        })
    }

    fun anakarakterHareketEttirme(){

        val anakarakterHiz = ekranYukseligi/60.0f//1280 / 60.0f  = 20.0f

        if(dokunmaKontrol){
            anakarakterY-=anakarakterHiz
        }else{
            anakarakterY+=anakarakterHiz
        }

        if(anakarakterY <= 0.0f){
            anakarakterY = 0.0f
        }

        if(anakarakterY >= ekranYukseligi - anakarakterYuksekligi){
            anakarakterY = (ekranYukseligi - anakarakterYuksekligi).toFloat()
        }

        anakarakter.y = anakarakterY
    }

    fun cisimlerinHareketEttir(){

        when(skor){
            in 0..250 -> {
                siyahkareX-= ekranGenisligi/54.0f//1080 / 44.0f  = 25.0f
                saridaireX-= ekranGenisligi/54.0f//1080 / 54.0f  = 20.0f
                kirmiziucgenX-= ekranGenisligi/36.0f//1080 / 36.0f  = 30.0f
            }

            in 250..500 -> {
                siyahkareX-= ekranGenisligi/45.0f//1080 / 44.0f  = 25.0f
                saridaireX-= ekranGenisligi/50.0f//1080 / 54.0f  = 20.0f
                kirmiziucgenX-= ekranGenisligi/30.0f//1080 / 36.0f  = 30.0f
            }

            in 500..1000000 -> {
                siyahkareX-= ekranGenisligi/37.0f//1080 / 44.0f  = 25.0f
                saridaireX-= ekranGenisligi/47.0f//1080 / 54.0f  = 20.0f
                kirmiziucgenX-= ekranGenisligi/24.0f//1080 / 36.0f  = 30.0f
            }
        }

        if (siyahkareX < 0.0f ){
            siyahkareX = ekranGenisligi + 20.0f
            siyahkareY = floor(Math.random() * ekranYukseligi).toFloat()
        }
        siyahkare.x = siyahkareX
        siyahkare.y = siyahkareY

        if (saridaireX < 0.0f ){
            saridaireX = ekranGenisligi + 20.0f
            saridaireY = floor(Math.random() * ekranYukseligi).toFloat()
        }
        saridaire.x = saridaireX
        saridaire.y = saridaireY

        if (kirmiziucgenX < 0.0f ){
            kirmiziucgenX = ekranGenisligi + 20.0f
            kirmiziucgenY = floor(Math.random() * ekranYukseligi).toFloat()
        }
        kirmiziucgen.x = kirmiziucgenX
        kirmiziucgen.y = kirmiziucgenY
    }

    fun carpismaKontrol(){

        val saridaireMerkezX = saridaireX + saridaire.width/2.0f
        val saridaireMerkezY = saridaireY + saridaire.height/2.0f

        if (0.0f <= saridaireMerkezX && saridaireMerkezX <= anakarakterGenisligi
                && anakarakterY <= saridaireMerkezY && saridaireMerkezY <= anakarakterY+anakarakterYuksekligi){
            skor+=20
            saridaireX = -10.0f
        }

        val kirmziucgenMerkezX = kirmiziucgenX + kirmiziucgen.width/2.0f
        val kirmziucgenMerkezY = kirmiziucgenY + kirmiziucgen.height/2.0f

        if (0.0f <= kirmziucgenMerkezX && kirmziucgenMerkezX <= anakarakterGenisligi
            && anakarakterY <= kirmziucgenMerkezY && kirmziucgenMerkezY <= anakarakterY + anakarakterYuksekligi) {
            skor += 50
            kirmiziucgenX = -10.0f
        }

        val siyahkareMerkezX = siyahkareX + siyahkare.width/2.0f
        val siyahkareMerkezY = siyahkareY + siyahkare.height/2.0f

        if (0.0f <= siyahkareMerkezX && siyahkareMerkezX <= anakarakterGenisligi
            && anakarakterY <= siyahkareMerkezY && siyahkareMerkezY <= anakarakterY + anakarakterYuksekligi) {
            siyahkareX = -10.0f

            timer.cancel()//Timer durdur.

            val intent = Intent(this@OyunEkraniActivity, SonucEkraniActivity::class.java)
            intent.putExtra("skor", skor)
            startActivity(intent)
        }

        textViewSkor.text = skor.toString()
    }
}

