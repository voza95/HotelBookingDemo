package com.example.hotelbookingdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbookingdemo.MainActivity
import com.example.hotelbookingdemo.model.HotelsItem
import com.example.hotelbookingdemo.databinding.RowHotelItemBinding
import kotlinx.android.synthetic.main.row_hotel_item.view.*

class HotelAdapter(
    var mContext: MainActivity,
        var mList: ArrayList<HotelsItem>,
    var setOnItemClickListener: SetOnItemClickListener
) : RecyclerView.Adapter<HotelAdapter.MyViewHolder>() {

    interface SetOnItemClickListener{

        fun hotelDetails(position: Int, HotelsItem: HotelsItem? = null)

    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = RowHotelItemBinding.inflate(layoutInflater)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tvHotelName.text = mList[position].hotelName
        holder.itemView.tvLocationName.text = mList[position].locationName

        holder.itemView.hotelLL.setOnClickListener {
            setOnItemClickListener.hotelDetails(position,mList[position])
        }
    }

    override fun getItemCount(): Int {
        return  mList.size
    }
}