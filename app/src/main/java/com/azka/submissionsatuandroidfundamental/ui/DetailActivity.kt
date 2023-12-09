package com.azka.submissionsatuandroidfundamental.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.azka.submissionsatuandroidfundamental.R
import com.azka.submissionsatuandroidfundamental.data.local.entity.UserEntity
import com.azka.submissionsatuandroidfundamental.databinding.ActivityDetailBinding
import com.azka.submissionsatuandroidfundamental.ui.adapter.SectionPagerAdapter
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.DetailViewModel
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private var user: UserEntity = UserEntity(0,null,null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail"
        val username = intent.getStringExtra(EXTRA_LOGIN)
        detailViewModel.getDetailUser(username.toString())
        detailViewModel.userEntity.observe(this){
            user = it
        }
        detailViewModel.isLoading.observe(this){
            binding.progressBar.isVisible = it
        }
        detailViewModel.detailUser.observe(this){
            with(binding){
                progressBar.isGone = true
                if(it!=null){
                    tvNumberFollowers.text = it.followers.toString()
                    tvNumberFollowings.text = it.following.toString()
                    tvFullName.text = it.name.toString()
                    tvUsername.text = it.login.toString()
                    Glide.with(binding.root)
                        .load(it.avatarUrl)
                        .into(binding.ivUser)
                        .clearOnDetach()
                }
            }
        }
        detailViewModel.isFavorite(username.toString()).observe(this){
            if (it) {
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(binding.btnFavorite.context, R.drawable.baseline_favorite_24))
                binding.btnFavorite.setOnClickListener{
                    detailViewModel.deleteFavorite(username.toString())
                }
            } else {
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(binding.btnFavorite.context, R.drawable.baseline_favorite_border_24))
                binding.btnFavorite.setOnClickListener{
                    detailViewModel.addFavorite(user)
                }
            }
        }
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_user -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intentSetting = Intent(this, SettingsActivity::class.java)
                startActivity(intentSetting)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    companion object{
        const val EXTRA_LOGIN="login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}