package com.example.giphytestapp.presentation.ui.recyclerviews

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytestapp.R
import com.google.android.material.button.MaterialButton

class DetailedAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<DetailedAdapter.DetailedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.detailed_item_layout, parent, false)
        return DetailedViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: DetailedViewHolder, position: Int) {
        val itemsText = items[position]
        holder.itemView.findViewById<TextView>(R.id.textview).text = itemsText
        holder.itemView.findViewById<MaterialButton>(R.id.remove_button).setOnClickListener {
            Log.i("AAADIP", "remove_button clicked")
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Some title")
                .setMessage("Some message")
                .setPositiveButton("Some positive",  object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Log.i("AAADIP", "positive button clicked")
                    }
                })
                .setNegativeButton("Some negative", null)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class DetailedViewHolder(view: View): RecyclerView.ViewHolder(view)
}