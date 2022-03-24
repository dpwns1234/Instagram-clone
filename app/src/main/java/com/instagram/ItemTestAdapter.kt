package com.instagram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.instagram.model.Post

class ItemTestAdapter: RecyclerView.Adapter<ItemTestAdapter.ItemTestViewHolder>() {

    class ItemTestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listener: Int, item: Post) {
            itemView.
            itemView.txtUser_name.text = item.user_name
            itemView.setOnClickListener(listener)
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTestViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        return ItemTestAdapter.ItemTestViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemTestViewHolder, position: Int) {
        holder. // 미치겠다. 이건 어케해야하는겨...
    }


    override fun getItemCount(): Int {
        return items.count
    }
}