package com.example.buscaminas

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class GridAdapter(
    private val context: Context,
    private val arrayList: ArrayList<Int>,
) :
    BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Int {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(index: Int, view: View?, viewGroup: ViewGroup): View? {
        var convertView = view
        println("GETVIEW")
        if (convertView == null) {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.grid_item, viewGroup, false)
        }else{
            val item = getItem(index)
            println("ITEM --> $item")

            val image = convertView.findViewById<ImageView>(R.id.imageView2)
            changeImage(item, image)
        }
        return convertView
    }
    private fun changeImage(item: Int, image: ImageView){
        when(item){
            -1 -> image.setImageResource(R.drawable.bomb)
            //0 -> image.setImageResource(R.drawable.number0)
            1 -> image.setImageResource(R.drawable.number1)
            2 -> image.setImageResource(R.drawable.number2)
            3 -> image.setImageResource(R.drawable.number3)
            /*4 -> image.setImageResource(R.drawable.number4)
            5 -> image.setImageResource(R.drawable.number5)
            6 -> image.setImageResource(R.drawable.number6)
            7 -> image.setImageResource(R.drawable.number7)
            8 -> image.setImageResource(R.drawable.number8)*/
        }
    }
}