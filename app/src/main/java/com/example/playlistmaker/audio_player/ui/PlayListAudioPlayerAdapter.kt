package com.example.playlistmaker.audio_player.ui

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.databinding.PlaylistItemHorizontalBinding


class PlayListAudioPlayerAdapter :
    RecyclerView.Adapter<PlayListAudioPlayerAdapter.PlayListViewHolder>() {
    private var listOfPlayLists = arrayListOf<PlayList>()
    private var itemClickListener: ItemClickPlayListInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {

        return PlayListViewHolder(
            PlaylistItemHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemClickListener
        )
    }

    override fun getItemCount() = listOfPlayLists.size

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.bind(listOfPlayLists[position])
    }

    fun setListOfPlayLists(playLists: List<PlayList>) {
        this.listOfPlayLists = playLists as ArrayList<PlayList>
        notifyDataSetChanged()
    }


    interface ItemClickPlayListInterface {
        fun onItemClick(playList: PlayList)
    }

    fun setInItemClickListener(itemClickListener: ItemClickPlayListInterface) {
        this.itemClickListener = itemClickListener
    }

    class PlayListViewHolder(
        binding: PlaylistItemHorizontalBinding,
        private val itemClickListener: ItemClickPlayListInterface?
    ) : RecyclerView.ViewHolder(binding.root) {

        private val playListName: TextView = binding.textViewPlaylistName
        private val playListSize: TextView = binding.textViewPlaylistSize
        private val imageViewPlayListImage: ImageView = binding.imageViewPlaylistImage
        private var itemPlaylist: PlayList? = null

        init {

            itemView.setOnClickListener {
                itemPlaylist?.let { it1 ->
                    itemClickListener?.onItemClick(
                        it1
                    )
                }

            }
        }

        fun bind(playList: PlayList) {
            itemPlaylist = playList
            playListName.text = playList.playListName
            playListSize.text = setTextInPlaylistSize(playList.sizeOfTrackIdsList)


            Glide.with(itemView)
                .load(playList.playlistImageUri)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(cornerRadius, itemView.context)))
                .into(imageViewPlayListImage)
        }

        private fun dpToPx(dp: Float, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            ).toInt()
        }

        private fun setTextInPlaylistSize(size: Int): String {
            var tracksString: String = ""

            tracksString = if (size in 10..20) {
                "треков"
            } else {
                when (divideNumber(size)) {
                    0, in 5..9 -> {
                        "треков"
                    }

                    1 -> {
                        "трек"
                    }

                    in 2..4 -> {
                        "трека"
                    }

                    else -> {
                        Log.d(this.javaClass.name, "smth wrong : name exception")
                        "трек"
                    }
                }
            }

            return "$size $tracksString"
        }

        private fun divideNumber(size: Int): Int {
            var number = size

            if (number > 10) {
                number %= 10
                divideNumber(number)
            }
            return number

        }
    }

    companion object {
        const val cornerRadius: Float = 2f

    }

}