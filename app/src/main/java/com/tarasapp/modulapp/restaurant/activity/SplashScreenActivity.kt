package com.tarasapp.modulapp.restaurant.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tarasapp.modulapp.restaurant.R
import gr.net.maroulis.library.EasySplashScreen

class SplashScreenActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = EasySplashScreen(this)
            .withFullScreen()
            .withTargetActivity(CuisinesActivity::class.java)
            .withSplashTimeOut(2000)
            .withBackgroundColor(Color.parseColor("#E12929"))
            .withLogo(R.mipmap.ic_launcher)
            .withHeaderText("Hello")
            .withFooterText("Footer")
            .withBeforeLogoText("Before logo")
            .withAfterLogoText("After logo")

        splash.headerTextView.setTextColor(Color.WHITE)
        splash.footerTextView.setTextColor(Color.WHITE)
        splash.beforeLogoTextView.setTextColor(Color.WHITE)
        splash.afterLogoTextView.setTextColor(Color.WHITE)

        val view = splash.create()
        setContentView(view)
    }

}
