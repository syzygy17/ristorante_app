package com.ristorante.ristoranteapp.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.FragmentMenuBinding
import com.ristorante.ristoranteapp.databinding.FragmentRegistrationBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}