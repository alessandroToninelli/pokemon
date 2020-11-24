package com.example.pokemon.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.pokemon.binding.BindListAdapterData
import com.example.pokemon.databinding.ItemStatBinding
import com.example.pokemon.model.Stat
import com.example.pokemon.ui.common.BaseAdapter
import com.example.pokemon.vo.Resource
import com.example.pokemon.vo.case

class StatAdapter : BaseAdapter<Stat, ItemStatBinding>(object : DiffUtil.ItemCallback<Stat>(){
    override fun areItemsTheSame(oldItem: Stat, newItem: Stat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Stat, newItem: Stat): Boolean {
        return oldItem.base_stat == newItem.base_stat
    }

}), BindListAdapterData<Resource<List<Stat>>> {
    override fun inflateViewBinding(parent: ViewGroup, viewType: Int): ItemStatBinding {
        return ItemStatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: ItemStatBinding, item: Stat, position: Int) {
        binding.stat = item
    }

    override fun setData(data: Resource<List<Stat>>) {
        data.case {
            submitList(it.data)
        }
    }
}