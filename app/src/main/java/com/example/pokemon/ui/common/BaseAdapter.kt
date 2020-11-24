package com.example.pokemon.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item, Binding>(
    private val diffCallback: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseAdapter.BaseViewHolder<Binding>>(diffCallback) where Binding : ViewDataBinding {

    protected lateinit var binding: Binding

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Binding> {
        binding = inflateViewBinding(parent, viewType)
       return createViewHolder(binding, viewType)
    }

    abstract fun inflateViewBinding(parent: ViewGroup, viewType: Int): Binding

    final override fun onBindViewHolder(holder: BaseViewHolder<Binding>, position: Int) {
        bind(holder.binding, getItem(position),position)
        holder.binding.executePendingBindings()
    }

    open fun createViewHolder(binding: Binding, viewType: Int): BaseViewHolder<Binding> {
        return BaseViewHolder(binding)
    }

    final override fun onBindViewHolder(
        holder: BaseViewHolder<Binding>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onPayloads(payloads)
        super.onBindViewHolder(holder, position, payloads)
    }

    open fun onPayloads(payloads: MutableList<Any>){ }

    abstract fun bind(binding: Binding, item: Item, position: Int)

    open class BaseViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)

}
