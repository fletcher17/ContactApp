package com.decagon.android.sq007.contactrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.contactrecyclerview.ContactViewHolder
import com.decagon.android.sq007.model.ContactData

class ContactAdapter(items: List<ContactData>, ctx: Context) : RecyclerView.Adapter<ContactViewHolder>() {

    private var list: List<ContactData> = items
    private var context: Context = ctx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.name.text = list[position].nameData
        holder.number.text = list[position].phoneNumberData
    }
}