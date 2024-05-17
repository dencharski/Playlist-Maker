package com.example.playlistmaker.current_playlist.ui

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
import com.example.playlistmaker.databinding.TrackItemBinding
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.search.ui.TrackListAdapter

class CurrentPlayListTrackAdapter:
    RecyclerView.Adapter<CurrentPlayListTrackAdapter.TrackViewHolder>() {
    private var trackList = arrayListOf<TrackApp>()
    private var itemClickListener: ItemClickInterface? = null
    private var itemLongClickListener: ItemLongClickInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        return TrackViewHolder(
            TrackItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemClickListener,itemLongClickListener
        )
    }

    override fun getItemCount() = trackList.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    fun setTrackList(trackList: ArrayList<TrackApp>) {
        this.trackList = trackList
        notifyDataSetChanged()
    }

    interface ItemClickInterface {
        fun onItemClick(track: TrackApp)
    }
    interface ItemLongClickInterface {
        fun onItemLongClick(track: TrackApp): Boolean
    }

    fun setInItemClickListener(itemClickListener: ItemClickInterface) {
        this.itemClickListener = itemClickListener
    }
    fun setInItemLongClickListener(itemLongClickListener: ItemLongClickInterface) {
        this.itemLongClickListener = itemLongClickListener
    }

    class TrackViewHolder(
        binding: TrackItemBinding,
        private val itemClickListener: ItemClickInterface?,
        private val itemLongClickListener: ItemLongClickInterface?
    ) : RecyclerView.ViewHolder(binding.root) {

        private val trackName: TextView = binding.textViewTrackName
        private val artistName: TextView = binding.textViewArtistName
        private val trackTime: TextView = binding.textViewTrackTime
        private val artworkImageView: ImageView = binding.imageViewArtwork
        private var itemTrackApp: TrackApp? = null

        init {
            itemView.setOnClickListener {
                itemTrackApp?.let { it1 ->
                    itemClickListener?.onItemClick(
                        it1
                    )
                }
            }
            itemView.setOnLongClickListener{
                itemTrackApp?.let { it1 -> itemLongClickListener?.onItemLongClick(it1) } == true
            }
        }

        fun bind(track: TrackApp) {
            itemTrackApp = track
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

    companion object {
        const val cornerRadius: Float = 2f
    }

}