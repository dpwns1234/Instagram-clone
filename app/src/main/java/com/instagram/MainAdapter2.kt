package com.instagram

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.instagram.model.Main
import com.instagram.model.Post

class MainAdapter2(private val context: Context): RecyclerView.Adapter<MainAdapter2.MainViewHolder>() {

    val data = mutableListOf<Main>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val profileImage: ImageView = itemView.findViewById(R.id.iv_profile_image)
        private val profileName: TextView =  itemView.findViewById(R.id.tv_profile_name)
        private val createdDate: TextView = itemView.findViewById(R.id.tv_created_date)

        fun bind(data: Main) {
            profileName.text = data.feed[0].user.nickname
            createdDate.text = data.post[0].createdAt
        }
    }
}