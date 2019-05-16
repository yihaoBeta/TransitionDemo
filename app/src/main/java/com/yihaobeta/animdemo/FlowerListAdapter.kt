package com.yihaobeta.animdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FlowerListAdapter(private val dataList: MutableList<FlowerBean>) :
    RecyclerView.Adapter<FlowerListAdapter.FlowerViewHolder>() {

    fun setData(data: List<FlowerBean>){
        dataList.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_layout, parent,false)
        return FlowerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        Glide.with(holder.itemView).load(dataList[position].picurl).into(holder.imageIv)
        holder.nameTv.text = dataList[position].name
        holder.classifyTv.text = dataList[position].classify
        holder.descriptionTv.text = dataList[position].description
    }

    class FlowerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.item_name)
        val imageIv:ImageView = view.findViewById(R.id.item_image)
        val classifyTv:TextView = view.findViewById(R.id.item_classify)
        val descriptionTv:TextView = view.findViewById(R.id.item_description)
    }
}