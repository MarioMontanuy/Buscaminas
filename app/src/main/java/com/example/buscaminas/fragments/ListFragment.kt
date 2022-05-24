package com.example.buscaminas.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
/*import com.example.buscaminas.database.ResultDataDatabase
import com.example.buscaminas.database.ResultDataRepository*/
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ListFragment: ListFragment() {

    var rListener : ResultListener? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val applicationScope = CoroutineScope(SupervisorJob())
        /*val database = ResultDataDatabase.getDatabase(requireActivity(), applicationScope)
        val repository = ResultDataRepository(database.resultDataDao())
        println("A***********************")
        println(database.resultDataDao().getListDataEntries())*/
        val values = arrayOf("Prueba1", "Prueba2", "Prueba3")
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, values)
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        /*if (requireActivity().supportFragmentManager.findFragmentById(R.id.fragDetail) != null){
            val fragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragDetail) as DetailFragment
            if(fragment != null && fragment.isVisible){
                fragment.showText("Hello!!!!")
            }else{
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                startActivity(intent)
            }
        }else{
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            startActivity(intent)
        }*/
        // TODO adaptar para un elemento de la base de datos
        rListener?.onResultSelected(listAdapter?.getItem(position) as String)

        /*val noteUri : Uri = ContentUris.withAppendedId(,id)
        rListener?.onResultSelected(noteUri)*/

    }

    interface ResultListener {
        fun onResultSelected(resultData: String)
    }

    fun setResultListener(newResultListener: ResultListener){
        this.rListener = newResultListener
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        rListener = try {
            context as ResultListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement ResultListener")
        }
    }
}