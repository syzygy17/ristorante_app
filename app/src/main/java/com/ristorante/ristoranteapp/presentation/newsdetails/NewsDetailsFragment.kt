package com.ristorante.ristoranteapp.presentation.newsdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.FragmentNewsDetailsBinding


class NewsDetailsFragment : Fragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        titleNewsTextView.text = args.title
        newsDetailsImage.load(args.imageUrl)
        newsDescriptionTextView.text = args.description
        arrowToolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}