package com.ristorante.ristoranteapp.presentation.registration

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
import com.ristorante.ristoranteapp.databinding.FragmentRegistrationBinding
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import com.ristorante.ristoranteapp.utils.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val registrationViewModel: RegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        startObserving()
    }

    private fun setListeners() = with(binding) {
        loginEditText.doAfterTextChanged {
            registrationViewModel.onRegistrationFieldChanged(
                RegistrationFieldType.Login(
                    login = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        emailEditText.doAfterTextChanged {
            registrationViewModel.onRegistrationFieldChanged(
                RegistrationFieldType.Email(
                    email = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        passwordEditText.doAfterTextChanged {
            registrationViewModel.onRegistrationFieldChanged(
                RegistrationFieldType.Password(
                    password = (it ?: return@doAfterTextChanged).toString()
                )
            )
        }
        signUpButton.setOnClickListener {
            registrationViewModel.onSignUpButtonClicked()
            hideKeyboard()
        }
        alreadyHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_authFragment)
        }
    }

    private fun startObserving() {
        registrationViewModel.viewState.observe(viewLifecycleOwner) {
            handleViewStateChanges(it)
        }
    }

    private fun handleViewStateChanges(state: ViewState<RegistrationViewState>) = when (state) {
        is ViewState.Data -> handleRegistrationViewStateChanges(state.data)
        is ViewState.Error -> onError(state.throwable)
        is ViewState.Loading -> setProgressBarVisibility(true)
    }

    private fun handleRegistrationViewStateChanges(state: RegistrationViewState) = when (state) {
        is RegistrationViewState.Registered -> onRegistered(state.login)
    }

    private fun onRegistered(login: String) {
        setProgressBarVisibility(false)
        showToast("${getString(R.string.welcome)} $login")
        findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
    }

    private fun onError(throwable: Throwable) {
        setProgressBarVisibility(false)
        val message = throwable.message ?: getString(R.string.general_error)
        showToast(message)
    }

    private fun setProgressBarVisibility(value: Boolean) {
        binding.registrationProgressBar.isVisible = value
    }

    private fun showToast(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}