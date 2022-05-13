package com.ristorante.ristoranteapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.FragmentHomeBinding
import com.ristorante.ristoranteapp.domain.home.News
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter = NewsAdapter {
        navigateToNewsDetailsFragment(it)
    }

    private fun navigateToNewsDetailsFragment(news: News) {
        val action = HomeFragmentDirections.actionHomeFragmentToNewsDetailsFragment(
            title = news.title,
            imageUrl = news.imageUrl,
            description = news.description
        )
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNewsRecyclerView()
        startObserving()
    }

    private fun setNewsRecyclerView() = binding.newsRecyclerView.apply {
        adapter = newsAdapter
    }

    private fun startObserving() {
        newsViewModel.viewState.observe(viewLifecycleOwner) {
            handleViewStateChanges(it)
        }
    }

    private fun handleViewStateChanges(state: ViewState<NewsViewState>) = when (state) {
        is ViewState.Data -> handleNewsViewStateChanges(state.data)
        is ViewState.Error -> onError(state.throwable)
        is ViewState.Loading -> setProgressBarVisibility(true)
    }

    private fun handleNewsViewStateChanges(state: NewsViewState) = when (state) {
        is NewsViewState.NewsFetched -> onNewsFetched(state.news)
    }

    private fun onNewsFetched(news: List<News>) {
        setProgressBarVisibility(false)
        newsAdapter.submitList(news)
    }

    private fun onError(throwable: Throwable) {
        setProgressBarVisibility(false)
        val message = throwable.message ?: getString(R.string.general_error)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setProgressBarVisibility(value: Boolean) {
        binding.homeProgressBar.isVisible = value
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}