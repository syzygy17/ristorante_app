package com.ristorante.ristoranteapp.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.menu.Menu
import com.ristorante.ristoranteapp.domain.menu.MenuRepository
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<MenuViewState>>()

    val viewState: LiveData<ViewState<MenuViewState>> = _viewState

    init {
        onGetMenu()
    }

    private fun onGetMenu() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            getMenu()
        }
    }

    private suspend fun getMenu() = withContext(Dispatchers.IO) {
        when (val result = menuRepository.getMenu()) {
            is Response.Success -> onMenuFetched(result.data)
            is Response.Failure -> onError(result.data)
        }
    }

    private fun onMenuFetched(menu: List<Menu>) =
        _viewState.postValue(ViewState.Data(MenuViewState.MenuFetched(menu)))

    private fun onError(throwable: Throwable) =
        _viewState.postValue(ViewState.Error(throwable))
}