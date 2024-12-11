package com.example.subserror.ui.finished

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subserror.data.local.entity.EventEntity
import com.example.subserror.data.remote.response.ListEventsItem
import com.example.subserror.databinding.FavouriteListLayoutBinding
import com.example.subserror.databinding.FinishedListLayoutBinding
import com.example.subserror.ui.detail.DetailActivity

class FavouriteAdapter : ListAdapter<EventEntity, FavouriteAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    class MyViewHolder(private val binding: FavouriteListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listEvent: EventEntity) {
            binding.tvItemTitle.text = listEvent.name
            Glide.with(binding.root.context).load(listEvent.imageLogo).into(binding.imgPoster)
            binding.tvWaktu.text = listEvent.beginTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FavouriteListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)


        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("EVENT_ID", list.id.toString())
            holder.itemView.context.startActivity(intentDetail)

        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
