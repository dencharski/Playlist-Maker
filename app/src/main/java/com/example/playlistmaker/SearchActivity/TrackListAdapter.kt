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
import java.text.SimpleDateFormat
import java.util.Locale

class TrackListAdapter() :
    RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

    private var trackList= arrayListOf<Track>()
    companion object{
        const val cornerRadius:Float=2f
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        )
    }

    override fun getItemCount() = trackList.size


    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    fun setTrackList(trackList:ArrayList<Track>){
        this.trackList=trackList
        notifyDataSetChanged()
    }

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val trackName: TextView = itemView.findViewById(R.id.text_view_track_name)
        private val artistName: TextView = itemView.findViewById(R.id.text_view_artist_name)
        private val trackTime: TextView = itemView.findViewById(R.id.text_view_track_time)
        private val artworkImageView: ImageView = itemView.findViewById(R.id.image_view_artwork)


        fun bind(track: Track) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toLongOrNull())


            Glide.with(itemView)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(cornerRadius,itemView.context)))
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