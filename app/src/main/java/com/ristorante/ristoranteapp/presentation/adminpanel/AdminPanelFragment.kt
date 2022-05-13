package com.ristorante.ristoranteapp.presentation.adminpanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.FragmentAdminPanelBinding
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdminPanelFragment : Fragment() {

    private var _binding: FragmentAdminPanelBinding? = null
    private val binding get() = _binding!!
    private val adminPanelViewModel: AdminPanelViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPanelBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        startObserving()
    }

    private fun setListeners() = with(binding) {
        newsIdEditText.doAfterTextChanged {
            adminPanelViewModel.onNewsFieldChanged(
                NewsFieldType.Id(
                    id = Integer.parseInt((it ?: return@doAfterTextChanged).toString())
                )
            )
        }
        newsImageUrlEditText.doAfterTextChanged {
            adminPanelViewModel.onNewsFieldChanged(
                NewsFieldType.ImageUrl(
                    imageUrl = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        newsTitleEditText.doAfterTextChanged {
            adminPanelViewModel.onNewsFieldChanged(
                NewsFieldType.Title(
                    title = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        newsDescriptionEditText.doAfterTextChanged {
            adminPanelViewModel.onNewsFieldChanged(
                NewsFieldType.Description(
                    description = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        saveNewsButton.setOnClickListener {
            adminPanelViewModel.onSaveNewsButtonClicked()
        }
    }

    private fun startObserving() {
        adminPanelViewModel.viewState.observe(viewLifecycleOwner) {
            handleViewStateChanges(it)
        }
    }

    private fun handleViewStateChanges(state: ViewState<AdminPanelViewState>) = when (state) {
        is ViewState.Data -> handleAdminPanelViewStateChanges(state.data)
        is ViewState.Error -> onError(state.throwable)
        is ViewState.Loading -> setProgressBarVisibility(true)
    }

    private fun handleAdminPanelViewStateChanges(state: AdminPanelViewState) = when (state) {
        is AdminPanelViewState.NewsSaved -> onNewsSaved()
    }

    private fun onNewsSaved() {
        setProgressBarVisibility(false)
        showToast(getString(R.string.news_saved_successfully))
    }

    private fun onError(throwable: Throwable) {
        setProgressBarVisibility(false)
        val message = throwable.message ?: getString(R.string.general_error)
        showToast(message)
    }

    private fun showToast(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    private fun setProgressBarVisibility(value: Boolean) {
        binding.adminPanelProgressBar.isVisible = value
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}