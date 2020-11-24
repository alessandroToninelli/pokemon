package com.toninelli.ton_store.binding

import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.binding.BindListAdapterData
import com.example.pokemon.binding.BindListAdapterInformation
import com.example.pokemon.vo.Resource
import com.example.pokemon.vo.Status
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("showHide")
fun showHide(view: View, b: Boolean) {
    view.visibility = if (b) View.VISIBLE else View.GONE
}

@BindingAdapter("showHideIfResLoading")
fun showHide(view: View, res: Resource<Any>?) {
    view.visibility = if (res?.status != Status.LOADING) View.VISIBLE else View.GONE
}


@Suppress("UNCHECKED_CAST")
@BindingAdapter("adapterData")
fun <T> bindAdapterData(view: RecyclerView, data: T?) {
    data?.let {
        if (view.adapter is BindListAdapterData<*>) {
            (view.adapter as BindListAdapterData<T>).setData(it)
        }
    }
}


@BindingAdapter("loadPhoto")
fun loadPhoto(view: ImageView, url: String?, ) {

    url?.let {
        Glide.with(view).load(it).into(view)
    }
}


