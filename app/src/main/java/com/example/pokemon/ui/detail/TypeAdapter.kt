package com.example.pokemon.ui.detail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.pokemon.binding.BindListAdapterData
import com.example.pokemon.databinding.ItemStatBinding
import com.example.pokemon.databinding.ItemTypeBinding
import com.example.pokemon.model.Stat
import com.example.pokemon.model.Type
import com.example.pokemon.ui.common.BaseAdapter
import com.example.pokemon.vo.Resource
import com.example.pokemon.vo.case

class TypeAdapter : BaseAdapter<Type, ItemTypeBinding>(object : DiffUtil.ItemCallback<Type>(){
    override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean {
        return oldItem.type.name == newItem.type.name
    }

}), BindListAdapterData<Resource<List<Type>>> {
    override fun inflateViewBinding(parent: ViewGroup, viewType: Int): ItemTypeBinding {
        return ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: ItemTypeBinding, item: Type, position: Int) {
        binding.type = item
    }

    override fun setData(data: Resource<List<Type>>) {
        data.case {
            submitList(it.data)
        }
    }
}