package com.example.subserror.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.subserror.R
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.subserror.databinding.ActivityDetailBinding
import com.example.subserror.ui.EventViewModel
import com.example.subserror.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
//    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: EventViewModel by viewModels {
            factory
        }

        binding.progressBar.visibility = View.VISIBLE
        val eventId = intent.getStringExtra("EVENT_ID")

        if (eventId?.toInt() != -1) {
            viewModel.getDetail(eventId.toString()).observe(this) { event ->
                binding.progressBar.visibility = View.GONE
                binding.titleTextView.text = event.name
                Glide.with(this).load(event.mediaCover).into(binding.eventImageView)
                binding.descriptionTextView.text = HtmlCompat.fromHtml(
                    event.description.toString(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                binding.ownerTextView2.text = event.ownerName
                val remain = (event.quota ?: 0) - (event.registrants ?: 0)
                binding.quotaTextView2.text = remain.toString()
                binding.timeTextView2.text = event.beginTime

                val likeButton = binding.likeButton
                if (event.isBookmarked) {
                    likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.baseline_favorite_24,
                        0,
                        0,
                        0
                    )
                } else {
                    likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.baseline_favorite_border_24,
                        0,
                        0,
                        0
                    )
                }
                binding.visitButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(event.link)
                    }
                    startActivity(intent)
                }

                likeButton.setOnClickListener {
                    if (event.isBookmarked) {
                        viewModel.deleteEvent(event)
                        likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.baseline_favorite_border_24,
                            0,
                            0,
                            0
                        )
                    } else {
                        viewModel.saveEvent(event)
                        likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.baseline_favorite_24,
                            0,
                            0,
                            0
                        )
                    }
                }
            }
        }
    }
}
