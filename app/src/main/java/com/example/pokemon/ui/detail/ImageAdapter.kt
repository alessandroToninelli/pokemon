package com.example.pokemon.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.example.pokemon.binding.BindListAdapterData
import com.example.pokemon.databinding.ItemImageBinding
import com.example.pokemon.ui.common.BaseAdapter
import com.example.pokemon.vo.Resource
import com.example.pokemon.vo.case
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImageAdapter: BaseAdapter<String, ItemImageBinding>(object : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}), BindListAdapterData<Resource<List<String>>> {

    override fun inflateViewBinding(parent: ViewGroup, viewType: Int): ItemImageBinding {
        return ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: ItemImageBinding, item: String, position: Int) {
        binding.url = item
    }

    override fun setData(data: Resource<List<String>>) {
        data.case {
            submitList(it.data)
        }
    }


}