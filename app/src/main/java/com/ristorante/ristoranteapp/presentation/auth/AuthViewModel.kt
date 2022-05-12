package com.ristorante.ristoranteapp.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.auth.AuthRepository
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import com.ristorante.ristoranteapp.domain.registration.User
import com.ristorante.ristoranteapp.presentation.registration.RegistrationViewState
import com.ristorante.ristoranteapp.utils.FILL_FIELDS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val user: User by lazy {
        User()
    }
    private val _viewState = MutableLiveData<ViewState<AuthViewState>>()

    val viewState: LiveData<ViewState<AuthViewState>> = _viewState
    val isUserAuthorized = MutableLiveData<Boolean>()

    init {
        checkIsUserAuthorized()
    }

    private fun checkIsUserAuthorized() {
        isUserAuthorized.value = authRepository.isUserAuthorized()
    }

    fun onAuthFieldChanged(authFieldType: AuthFieldType) = when (authFieldType) {
        is AuthFieldType.Email -> user.email = authFieldType.email
        is AuthFieldType.Password -> user.password = authFieldType.password
    }

    fun onSignInButtonClicked() = when {
        user.email.isBlank() || user.password.isBlank() -> onError(Throwable(FILL_FIELDS))
        else -> onSignIn()
    }

    private fun onSignIn() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            signIn()
        }
    }

    private suspend fun signIn() = withContext(Dispatchers.IO) {
        when (val result = authRepository.signIn(user)) {
            is Response.Success -> onAuthorized(result.data)
            is Response.Failure -> onError(result.data)
        }
    }

    private fun onAuthorized(isAuthorized: Boolean) {
        if (isAuthorized) {
            _viewState.postValue(ViewState.Data(AuthViewState.Authorized))
        }
    }

    private fun onError(throwable: Throwable) =
        _viewState.postValue(ViewState.Error(throwable))

}