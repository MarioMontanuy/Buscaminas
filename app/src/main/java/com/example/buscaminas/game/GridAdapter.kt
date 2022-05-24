package com.example.buscaminas.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.buscaminas.R

class GridAdapter(
    private val context: Context,
    private val arrayList: ArrayList<GridItem>,
) :
    BaseAdapter() {

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): GridItem {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(index: Int, view: View?, viewGroup: ViewGroup): View? {
        var convertView = view
        if (convertView == null) {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.grid_item, viewGroup, false)
        }
        val image = convertView?.findViewById<ImageView>(R.id.imageView2)
        image?.setImageResource(getItem(index).imageId)
        val viewHeight = viewGroup.height / kotlin.math.sqrt(count.toDouble())
        val layoutParams = convertView?.layoutParams
        layoutParams?.height = viewHeight.toInt()
        convertView?.layoutParams = layoutParams
        return convertView
    }
}