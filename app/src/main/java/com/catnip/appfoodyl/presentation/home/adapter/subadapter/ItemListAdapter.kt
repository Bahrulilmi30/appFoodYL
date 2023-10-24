package com.catnip.appfoodyl.presentation.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catnip.appfoodyl.presentation.home.adapter.viewholder.GridItemListViewHolder
import com.catnip.appfoodyl.presentation.home.adapter.viewholder.LinearItemListViewHolder
import com.catnip.appfoodyl.core.ViewHolderBinder
import com.catnip.appfoodyl.data.model.Menu
import com.catnip.appfoodyl.databinding.ItemGridMenuBinding
import com.catnip.appfoodyl.databinding.ItemLinearMenuBinding

class ItemListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object: DiffUtil.ItemCallback<Menu>(){
        override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterLayoutMode.GRID.ordinal -> {
                GridItemListViewHolder(
                    binding = ItemGridMenuBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onItemClick
                )
            }
            else -> {
                LinearItemListViewHolder(
                    binding = ItemLinearMenuBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onItemClick
                )
            }
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Menu>).bind(differ.currentList[position])
    }
    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }
    fun setData(menuData: List<Menu>) {
        differ.submitList(menuData)
    }
    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}
