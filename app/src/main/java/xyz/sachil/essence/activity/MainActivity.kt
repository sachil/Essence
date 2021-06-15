package xyz.sachil.essence.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.sachil.essence.R
import xyz.sachil.essence.databinding.ActivityMainBinding
import xyz.sachil.essence.vm.MainViewModel
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var isReadyToExit = false
    private val viewModel: MainViewModel by viewModels()
    private lateinit var viewBinding: ActivityMainBinding
    private val fragments = setOf(
        R.id.menu_navigation_essence,
        R.id.menu_navigation_article,
        R.id.menu_navigation_weekly_popular,
        R.id.menu_navigation_girl,
        R.id.menu_navigation_settings
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    //再按一次退出
    override fun onBackPressed() {
        if (isReadyToExit) {
            super.onBackPressed()
        } else {
            isReadyToExit = true
            lifecycleScope.launch(Dispatchers.IO) {
                delay(3500)
                isReadyToExit = false
            }
            Toast.makeText(this, R.string.exit_app_help_toast, Toast.LENGTH_LONG).show()
        }
    }

    private fun initViews() {
        //使用viewBinding代替kotlin-android-extensions插件和findViewById方法
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

//        val navigator = FixedFragmentNavigator(this,supportFragmentManager,navHostFragment.id)
//        navController.navigatorProvider.addNavigator(navigator)
//        navController.setGraph(R.navigation.navigation_main)

        val appBarConfiguration = AppBarConfiguration.Builder(fragments).build()
        viewBinding.toolBar.setupWithNavController(
            navController,
            appBarConfiguration
        )
        viewBinding.bottomNavigation.setupWithNavController(navController)
    }

}