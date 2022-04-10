package com.test.acronymsolutions.ui.main.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.acronymsolutions.databinding.ItemDictionaryBinding
import com.test.acronymsolutions.domain.dictionary.entity.LongFormEntity

class DictionaryAdapter(private val products: MutableList<LongFormEntity>) : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>(){
    interface OnItemTap {
        fun onTap(product: LongFormEntity)
    }

    fun setItemTapListener(l: OnItemTap){
        onTapListener = l
    }

    private var onTapListener: OnItemTap? = null

    fun updateList(mProducts: List<LongFormEntity>){
        products.clear()
        products.addAll(mProducts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemDictionaryBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(entry: LongFormEntity){
            itemBinding.entryObject = entry
            itemBinding.root.setOnClickListener {
                onTapListener?.onTap(entry)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDictionaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

    override fun getItemCount() = products.size
}