package com.example.pokemon.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentSlideshowBinding
import com.example.pokemon.util.autoCleared
import com.google.android.material.transition.MaterialSharedAxis

class SlideshowFragment : Fragment(R.layout.fragment_slideshow) {

    private var binding : FragmentSlideshowBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSlideshowBinding.bind(view)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }


}