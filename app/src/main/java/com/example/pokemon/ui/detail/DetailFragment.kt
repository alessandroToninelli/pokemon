package com.example.pokemon.ui.detail

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentDetailBinding
import com.example.pokemon.util.autoCleared
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : ScopeFragment(R.layout.fragment_detail) {

    private var binding: FragmentDetailBinding by autoCleared()

    private val model: DetailViewModel by viewModel()

    private var adapterImage: ImageAdapter by autoCleared()
    private var adapterType: TypeAdapter by autoCleared()
    private var adapterStat: StatAdapter by autoCleared()

    private val pokemonId by lazy {
        navArgs<DetailFragmentArgs>().value.id
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.model = model

        adapterImage = ImageAdapter()
        adapterStat = StatAdapter()
        adapterType = TypeAdapter()

        binding.images.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        binding.images.adapter = adapterImage
        binding.types.adapter = adapterType
        binding.stats.adapter = adapterStat

        model.loadDetail(pokemonId)

    }

}