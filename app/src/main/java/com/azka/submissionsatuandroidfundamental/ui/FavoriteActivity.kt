package com.azka.submissionsatuandroidfundamental.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.azka.submissionsatuandroidfundamental.databinding.ActivityFavoriteBinding
import com.azka.submissionsatuandroidfundamental.ui.adapter.FavoriteUserAdapter
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.FavoriteViewModel
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        adapter = FavoriteUserAdapter({
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN,it.username)
            startActivity(intent)
        },{
            favoriteViewModel.deleteFavorite(it.username.toString())
            favoriteViewModel.getAllFavorite().observe(this){result ->
                adapter.submitList(result)
            }
        })
        binding.rvGithub.adapter = adapter
        favoriteViewModel.getAllFavorite().observe(this){result ->
            binding.progressBar.isGone = true
            adapter.submitList(result)
            if(result.isEmpty()){
                binding.tvEmpty.isVisible = true
            }
        }
    }
}