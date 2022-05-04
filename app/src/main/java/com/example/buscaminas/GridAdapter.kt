package com.example.buscaminas

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class GridAdapter(
    private val context: Context,
    private val arrayList: ArrayList<GridItem>,
) :
    BaseAdapter(){
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
            /*val image = convertView.findViewById<ImageView>(R.id.imageView2)
            val item = getItem(index)
            println("ITEM ID ${item.id}")
            println("ITEM FLAG ${item.flag}")
            println("ITEM INSERT ${item.insertFlag}")
            if (!item.insertFlag){
                println("ITEM --> $item")
                changeImage(item.id, image)
            }else{
                println("CONTROLO LA FLAG")
                checkAndUpdateFlag(index, image)
            }*/
        return convertView
    }

    /*private fun checkAndUpdateFlag(index: Int, image: ImageView) {
        if(!getItemFlag(index)){
            image.setImageResource(R.drawable.bandera)
            setItemFlag(index, true)
        }else{
            image.setImageResource(R.drawable.capa_parrilla)
            setItemFlag(index, false)
        }
    }*/

}