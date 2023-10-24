package com.catnip.appfoodyl.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.appfoodyl.core.ViewHolderBinder
import com.catnip.appfoodyl.data.model.Menu
import com.catnip.appfoodyl.databinding.ItemGridMenuBinding
import com.catnip.appfoodyl.databinding.ItemLinearMenuBinding
import com.catnip.appfoodyl.utils.toCurrencyFormat

class GridItemListViewHolder (
    private val binding: ItemGridMenuBinding,
    private val onItemClick: (Menu) -> Unit
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu>{
    override fun bind(item: Menu){
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }

        binding.ivPictMenu.load(item.imageUrl){
            crossfade(true)
        }
        binding.tvTextMenu.text = item.name
        binding.tvHargaMenu.text = item.price.toCurrencyFormat()
    }
}

class LinearItemListViewHolder (
    private val binding: ItemLinearMenuBinding,
    private val onItemClick: (Menu) -> Unit
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu){
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }

        binding.ivPictMenu.load(item.imageUrl)
        binding.tvTextMenu.text = item.name
        binding.tvHargaMenu.text = item.price.toCurrencyFormat()
    }
}