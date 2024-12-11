package com.example.subserror.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subserror.data.local.entity.EventEntity
import com.example.subserror.data.remote.response.Event
import com.example.subserror.data.remote.response.ListEventsItem
import com.example.subserror.databinding.UpcomingListLayoutBinding
import com.example.subserror.ui.detail.DetailActivity

class UpcomingEventAdapter : ListAdapter<EventEntity, UpcomingEventAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    class MyViewHolder(private val binding: UpcomingListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listEvent: EventEntity) {
            binding.tvUpcomingList.text = listEvent.name
            Glide.with(binding.root.context).load(listEvent.mediaCover)
                .into(binding.imgUpcomingList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            UpcomingListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        return if (currentList.size > 5) 5 else currentList.size
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
