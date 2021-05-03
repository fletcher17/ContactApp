package com.decagon.android.sq007.contactrecyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var name = itemView.findViewById<TextView>(R.id.contactsName_textView)!!
    var number = itemView.findViewById<TextView>(R.id.contactsNumber_textView)!!
}