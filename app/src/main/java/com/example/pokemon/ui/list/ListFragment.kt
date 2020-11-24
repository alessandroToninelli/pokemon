package com.example.pokemon.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentListBinding
import com.example.pokemon.ui.common.PagingListener
import com.example.pokemon.util.autoCleared
import com.example.pokemon.vo.Failure
import com.example.pokemon.vo.Status
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : ScopeFragment(R.layout.fragment_list), PagingListener {

    private var binding : FragmentListBinding by autoCleared()

    private var adapter : PokemonPagedListAdapter by autoCleared()

    private val model : ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.loadListResult()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        binding.model = model

        adapter = PokemonPagedListAdapter(viewLifecycleOwner, this){
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(it))
        }

        binding.list.adapter = adapter

    }

    override fun refreshStatus(status: Status, failure: Failure?) {
        binding.progress.status = status
    }

    override fun appendStatus(status: Status, failure: Failure?) {
        binding.progress.status = status
    }

}