package com.ristorante.ristoranteapp.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import com.ristorante.ristoranteapp.domain.registration.RegistrationRepository
import com.ristorante.ristoranteapp.domain.registration.User
import com.ristorante.ristoranteapp.utils.FILL_FIELDS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val registrationRepository: RegistrationRepository
) : ViewModel() {

    private val user: User by lazy {
        User()
    }
    private val _viewState = MutableLiveData<ViewState<RegistrationViewState>>()

    val viewState: LiveData<ViewState<RegistrationViewState>> = _viewState

    fun onRegistrationFieldChanged(registrationFieldType: RegistrationFieldType) =
        when (registrationFieldType) {
            is RegistrationFieldType.Login -> user.login = registrationFieldType.login
            is RegistrationFieldType.Email -> user.email = registrationFieldType.email
            is RegistrationFieldType.Password -> user.password = registrationFieldType.password
        }

    fun onSignUpButtonClicked() = when {
        user.login.isBlank() || user.email.isBlank() || user.password.isBlank() -> onError(
            Throwable(
                message = FILL_FIELDS
            )
        )
        else -> onSignUp()
    }


    private fun onSignUp() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            signUp()
        }
    }

    private suspend fun signUp() = withContext(Dispatchers.IO) {
        when (val result = registrationRepository.signUp(user)) {
            is Response.Success -> onRegistered(result.data)
            is Response.Failure -> onError(result.data)
        }
    }

    private fun onRegistered(isRegistered: Boolean) {
        if (isRegistered) {
            _viewState.postValue(ViewState.Data(RegistrationViewState.Registered(user.login)))
        }
    }

    private fun onError(throwable: Throwable) =
        _viewState.postValue(ViewState.Error(throwable))

}
