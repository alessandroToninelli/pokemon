package com.example.pokemon.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import com.example.pokemon.binding.BindListAdapterData
import com.example.pokemon.databinding.ItemPokemonBinding
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.common.BasePagedAdapter
import com.example.pokemon.ui.common.PagingListener
import com.toninelli.ton_store.binding.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PokemonPagedListAdapter(
    private val owner: LifecycleOwner,
    private val pagingListener: PagingListener,
    private val callback: (Int) -> Unit
) : BasePagedAdapter<Pokemon, ItemPokemonBinding>(object : DiffUtil.ItemCallback<Pokemon>() {

    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.url == newItem.url && oldItem.name == newItem.name
    }

}, owner.lifecycleScope, pagingListener),

    BindListAdapterData<Flow<PagingData<Pokemon>>> {

    override fun setData(data: Flow<PagingData<Pokemon>>) {
        owner.lifecycleScope.launch {
            data.collectLatest { pagingData ->
                    submitData(pagingData)
                }
            }
        }

    override fun bind(binding: ItemPokemonBinding, item: Pokemon?, position: Int) {
        binding.pokemon = item

        binding.root.setOnClickListener {
            item?.let {
                val id = it.url.substringAfter("pokemon/").substringBefore("/")
                callback(id.toInt())
            }
        }
    }

    override fun inflateViewBinding(parent: ViewGroup, viewType: Int): ItemPokemonBinding {
        return ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }


}
