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

class TrackListAdapter(private val trackList: ArrayList<Track>) :
    RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }


    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val trackName: TextView = itemView.findViewById(R.id.text_view_track_name)
        private val artistName: TextView = itemView.findViewById(R.id.text_view_artist_name)
        private val trackTime: TextView = itemView.findViewById(R.id.text_view_track_time)
        private val artworkImageView: ImageView = itemView.findViewById(R.id.image_view_artwork)


        fun bind(track: Track) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text = track.trackTime

            Glide.with(itemView)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(2f,itemView.context)))
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