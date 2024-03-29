package com.example.chaquopy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val mList: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.textTitle.text = message.sender

        // sets the text to the textview from our itemHolder class
        holder.textDesc.text = message.message

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textTitle: TextView = itemView.findViewById(R.id.tv_title)
        val textDesc: TextView = itemView.findViewById(R.id.tv_desc)
    }
}