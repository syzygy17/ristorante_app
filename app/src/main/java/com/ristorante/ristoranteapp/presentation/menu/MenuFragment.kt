package com.ristorante.ristoranteapp.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.FragmentMenuBinding
import com.ristorante.ristoranteapp.domain.home.News
import com.ristorante.ristoranteapp.domain.menu.Menu
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by viewModel()
    private val menuAdapter = MenuAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuRecyclerView()
        startObserving()
    }

    private fun setMenuRecyclerView() = binding.menuRecyclerView.apply {
        adapter = menuAdapter
    }

    private fun startObserving() {
        menuViewModel.viewState.observe(viewLifecycleOwner) {
            handleViewStateChanges(it)
        }
    }

    private fun handleViewStateChanges(state: ViewState<MenuViewState>) = when (state) {
        is ViewState.Data -> handleMenuViewStateChanges(state.data)
        is ViewState.Error -> onError(state.throwable)
        is ViewState.Loading -> setProgressBarVisibility(true)
    }

    private fun handleMenuViewStateChanges(state: MenuViewState) = when (state) {
        is MenuViewState.MenuFetched -> onMenuFetched(state.menu)
    }

    private fun onMenuFetched(menu: List<Menu>) {
        setProgressBarVisibility(false)
        menuAdapter.submitList(menu)
    }

    private fun onError(throwable: Throwable) {
        setProgressBarVisibility(false)
        val message = throwable.message ?: getString(R.string.general_error)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setProgressBarVisibility(value: Boolean) {
        binding.menuProgressBar.isVisible = value
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}