package com.example.pokemon.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadType
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.util.errorOrNull
import com.example.pokemon.util.toStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BasePagedAdapter<Item, Binding>(
    diffCallback: DiffUtil.ItemCallback<Item>,
    scope: CoroutineScope,
    pagingListener: PagingListener? = null
) : PagingDataAdapter<Item, BasePagedAdapter.PagedBaseViewHolder<Binding>>(diffCallback) where Binding : ViewDataBinding, Item : Any {

    private lateinit var binding: Binding

    init {
        scope.launch {
            loadStateFlow.collectLatest {
                it.forEach { loadType, _, loadState ->
                    when (loadType) {
                        LoadType.REFRESH ->
                            pagingListener?.refreshStatus(
                                loadState.toStatus(),
                                loadState.errorOrNull()
                            )
                        LoadType.PREPEND -> pagingListener?.prependStatus(
                            loadState.toStatus(),
                            loadState.errorOrNull()
                        )
                        LoadType.APPEND -> pagingListener?.appendStatus(
                            loadState.toStatus(),
                            loadState.errorOrNull()
                        )
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagedBaseViewHolder<Binding> {
        binding = inflateViewBinding(parent, viewType)
        return createViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: PagedBaseViewHolder<Binding>, position: Int) {
        bind(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    open fun createViewHolder(binding: Binding, viewType: Int): PagedBaseViewHolder<Binding> {
        return PagedBaseViewHolder(binding)
    }

    abstract fun bind(binding: Binding, item: Item?, position: Int)

    abstract fun inflateViewBinding(parent: ViewGroup, viewType: Int): Binding

    class PagedBaseViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)

}