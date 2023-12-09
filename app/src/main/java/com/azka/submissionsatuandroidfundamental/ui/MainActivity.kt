package com.azka.submissionsatuandroidfundamental.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.azka.submissionsatuandroidfundamental.R
import com.azka.submissionsatuandroidfundamental.databinding.ActivityMainBinding
import com.azka.submissionsatuandroidfundamental.ui.adapter.GithubUserAdapter
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.MainViewModel
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GithubUserAdapter

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        adapter = GithubUserAdapter{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN,it.login)
            startActivity(intent)
        }
        binding.rvGithub.adapter = adapter
        mainViewModel.listUser.observe(this){
            adapter.submitList(it)
        }
        mainViewModel.isEmpty.observe(this){
            binding.tvEmpty.isVisible = it
        }
        mainViewModel.isLoading.observe(this){
            binding.progressBar.isVisible = it
        }

        val settingsButton = findViewById<FloatingActionButton>(R.id.settings)
        val favoriteUserButton = findViewById<FloatingActionButton>(R.id.favorite_user)

        settingsButton.setOnClickListener {
            val intentSetting = Intent(this, SettingsActivity::class.java)
            startActivity(intentSetting)
        }

        favoriteUserButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    mainViewModel.getUser(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }
    }

}