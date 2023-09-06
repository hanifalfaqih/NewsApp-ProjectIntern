package id.allana.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import id.allana.newsapp.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private var _binding: ActivityNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_news_container) as NavHostFragment
        navController = navHostFragment.navController

        //setupActionBarWithNavController(navController)

    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}