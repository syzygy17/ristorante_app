package com.ristorante.ristoranteapp.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.FragmentAuthBinding
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import com.ristorante.ristoranteapp.utils.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        startObserving()
    }

    private fun setListeners() = with(binding) {
        emailEditText.doAfterTextChanged {
            authViewModel.onAuthFieldChanged(
                AuthFieldType.Email(
                    email = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        passwordEditText.doAfterTextChanged {
            authViewModel.onAuthFieldChanged(
                AuthFieldType.Password(
                    password = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        signInButton.setOnClickListener {
            authViewModel.onSignInButtonClicked()
            hideKeyboard()
        }
        createAccount.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registrationFragment)
        }
    }

    private fun startObserving() {
        authViewModel.viewState.observe(viewLifecycleOwner) {
            handleViewStateChanges(it)
        }
        authViewModel.isUserAuthorized.observe(viewLifecycleOwner) { isAuthorized ->
            if (isAuthorized) navigateToHomeFragment()
        }
    }

    private fun handleViewStateChanges(state: ViewState<AuthViewState>) = when (state) {
        is ViewState.Data -> handleAuthViewStateChanges(state.data)
        is ViewState.Error -> onError(state.throwable)
        is ViewState.Loading -> setProgressBarVisibility(true)
    }

    private fun handleAuthViewStateChanges(state: AuthViewState) = when (state) {
        is AuthViewState.Authorized -> onAuthorized()
        is AuthViewState.AdminAuthorized -> findNavController().navigate(R.id.action_authFragment_to_adminPanelFragment)
    }

    private fun onAuthorized() {
        setProgressBarVisibility(false)
        navigateToHomeFragment()
    }

    private fun onError(throwable: Throwable) {
        setProgressBarVisibility(false)
        val message = throwable.message ?: getString(R.string.general_error)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setProgressBarVisibility(value: Boolean) {
        binding.authProgressBar.isVisible = value
    }

    private fun navigateToHomeFragment() =
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}