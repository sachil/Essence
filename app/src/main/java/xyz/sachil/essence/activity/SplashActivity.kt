package xyz.sachil.essence.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.sachil.essence.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMain(1 * 1000L)
    }

    private fun navigateToMain(delay: Long) = lifecycleScope.launch(Dispatchers.Default) {
        delay(delay)
        withContext(Dispatchers.Main) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

}