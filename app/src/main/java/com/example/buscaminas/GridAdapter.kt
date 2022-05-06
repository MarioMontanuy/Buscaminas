package com.example.buscaminas

import android.content.Context
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
        /*println("------------------------------")
        println("INDEX $index")
        println("VIEWGROUP $viewGroup")
        println("VIEW $view")*/
        var convertView = view
        if (convertView == null) {
//            println("converView es NULL")
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.grid_item, viewGroup, false)
            //if(viewGroup.height != 0){

            //}
        }
        val image = convertView?.findViewById<ImageView>(R.id.imageView2)
        image?.setImageResource(getItem(index).imageId)
        /*println("IMAGE ID ${getItem(index).imageId}")
        println("1- VIEWGROUP HEIGHT: ${viewGroup.height}")
        println("1- VIEW HEIGHT: ${convertView?.height}")*/
        val viewHeight = viewGroup.height/kotlin.math.sqrt(count.toDouble())
        //println("VIEW HEIGHT RESULT: $viewHeight")
        val layoutParams = convertView?.layoutParams
        layoutParams?.height = viewHeight.toInt()
        /*println("viewHeight to int: ${viewHeight.toInt()}")
        println("viewHeight seteeada: ${layoutParams?.height}")*/
        convertView?.layoutParams = layoutParams
        /*println("2- VIEWGROUP HEIGHT: ${viewGroup.height}")
        println("2- VIEW HEIGHT: ${convertView?.height}")*/
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
//        println("------------------------------")
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