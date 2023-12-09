package com.azka.submissionsatuandroidfundamental.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.azka.submissionsatuandroidfundamental.data.local.entity.UserEntity
import com.azka.submissionsatuandroidfundamental.databinding.UserItemBinding
import com.bumptech.glide.Glide

class FavoriteUserAdapter(
    private val onClick: (UserEntity) -> Unit,
    private val onDelete: (UserEntity) -> Unit
) : ListAdapter<UserEntity, FavoriteUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onClick(user)
        }
        holder.binding.btnDelete.setOnClickListener{
            onDelete(user)
        }
    }

    class MyViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity){
            binding.tvUsername.text = user.username
            binding.btnDelete.isVisible = true
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.imgItemPhoto)
                .clearOnDetach()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}