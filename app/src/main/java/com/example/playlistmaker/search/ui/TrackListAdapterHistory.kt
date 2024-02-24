package com.example.playlistmaker.search.ui

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.databinding.TrackItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TrackListAdapterHistory :
    RecyclerView.Adapter<TrackListAdapterHistory.TrackViewHolder>() {
    private var trackList = arrayListOf<TrackDtoApp>()
    private var itemClickListener: ItemClickInterfaceHistory? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            TrackItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemClickListener
        )
    }

    override fun getItemCount() = trackList.size
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    fun setTrackList(trackList: ArrayList<TrackDtoApp>) {
        this.trackList = trackList
        notifyDataSetChanged()
    }

    interface ItemClickInterfaceHistory {
        fun onItemClickHistory(track: TrackDtoApp)
    }

    fun setInItemClickListener(itemClickListener: ItemClickInterfaceHistory) {
        this.itemClickListener = itemClickListener
    }

    class TrackViewHolder(
        binding: TrackItemBinding,
        private val itemClickListener: ItemClickInterfaceHistory?
    ) : RecyclerView.ViewHolder(binding.root) {
        private val trackName: TextView = binding.textViewTrackName
        private val artistName: TextView = binding.textViewArtistName
        private val trackTime: TextView = binding.textViewTrackTime
        private val artworkImageView: ImageView = binding.imageViewArtwork
        private var itemTrackDtoApp: TrackDtoApp? = null

        init {
            itemView.setOnClickListener {
                itemTrackDtoApp?.let { it1 ->
                    itemClickListener?.onItemClickHistory(
                        it1
                    )
                }
            }
        }

        fun bind(track: TrackDtoApp) {
            itemTrackDtoApp = track
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text = SimpleDateFormat(
                "mm:ss",
                Locale.getDefault()
            ).format(track.trackTimeMillis.toLongOrNull())

            Glide.with(itemView)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(cornerRadius, itemView.context)))
                .into(artworkImageView)
        }

        private fun dpToPx(dp: Float, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            ).toInt()
        }
    }

    companion object {
        const val cornerRadius: Float = 2f
    }
}