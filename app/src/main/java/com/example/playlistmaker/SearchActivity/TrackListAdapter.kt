package com.example.playlistmaker.SearchActivity

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.Track
import com.example.playlistmaker.databinding.TrackItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TrackListAdapter() :
    RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

    private var trackList = arrayListOf<Track>()

    private var itemClickListener: ItemClickInterface? = null

    companion object {
        const val cornerRadius: Float = 2f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        //return TrackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false))

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

    fun setTrackList(trackList: ArrayList<Track>) {
        this.trackList = trackList
        notifyDataSetChanged()
    }

    interface ItemClickInterface {
        fun onItemClick(track:Track)
    }

    fun setInItemClickListener(itemClickListener: ItemClickInterface) {
        this.itemClickListener = itemClickListener
    }

    class TrackViewHolder(
        binding: TrackItemBinding,
        private val itemClickListener: ItemClickInterface?
    ) : RecyclerView.ViewHolder(binding.root) {

        private val trackName: TextView = binding.textViewTrackName
        private val artistName: TextView = binding.textViewArtistName
        private val trackTime: TextView = binding.textViewTrackTime
        private val artworkImageView: ImageView = binding.imageViewArtwork
        private var itemTrack:Track?=null

        init {
            itemView.setOnClickListener { itemTrack?.let { it1 -> itemClickListener?.onItemClick(it1) } }
        }

        fun bind(track: Track) {
            itemTrack=track
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text = track.getTrackTime()


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


}