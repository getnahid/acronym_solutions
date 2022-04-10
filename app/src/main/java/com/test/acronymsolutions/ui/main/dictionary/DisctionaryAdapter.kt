package com.test.acronymsolutions.ui.main.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.acronymsolutions.databinding.ItemProductBinding
import com.test.acronymsolutions.domain.dictionary.entity.DictionaryEntity
import com.test.acronymsolutions.domain.dictionary.entity.LongFormEntity

class DisctionaryAdapter(private val products: MutableList<LongFormEntity>) : RecyclerView.Adapter<DisctionaryAdapter.ViewHolder>(){
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

    inner class ViewHolder(private val itemBinding: ItemProductBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(product: LongFormEntity){
            itemBinding.productNameTextView.text = product.lf
            itemBinding.root.setOnClickListener {
                onTapListener?.onTap(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

    override fun getItemCount() = products.size
}