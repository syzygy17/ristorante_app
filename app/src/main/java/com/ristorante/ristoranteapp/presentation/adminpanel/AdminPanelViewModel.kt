package com.ristorante.ristoranteapp.presentation.adminpanel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.adminpanel.AdminPanelRepository
import com.ristorante.ristoranteapp.domain.adminpanel.MutableMenu
import com.ristorante.ristoranteapp.domain.adminpanel.MutableNews
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminPanelViewModel(
    private val adminPanelRepository: AdminPanelRepository
) : ViewModel() {

    private val news: MutableNews by lazy {
        MutableNews()
    }
    val menu: MutableMenu by lazy {
        MutableMenu()
    }

    private val _viewState = MutableLiveData<ViewState<AdminPanelViewState>>()

    val viewState: LiveData<ViewState<AdminPanelViewState>> = _viewState

    fun onNewsFieldChanged(newsFieldType: NewsFieldType) = when (newsFieldType) {
        is NewsFieldType.Id -> news.id = newsFieldType.id
        is NewsFieldType.ImageUrl -> news.imageUrl = newsFieldType.imageUrl
        is NewsFieldType.Title -> news.title = newsFieldType.title
        is NewsFieldType.Description -> news.description = newsFieldType.description
    }

    fun onSaveNewsButtonClicked() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            saveNews()
        }
    }

    fun onSaveMenuButtonClicked() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            saveMenu()
        }
    }

    private suspend fun saveNews() = withContext(Dispatchers.IO) {
        when (val result = adminPanelRepository.saveNews(news)) {
            is Response.Success -> onNewsSaved(result.data)
            is Response.Failure -> onError(result.data)
        }
    }

    private suspend fun saveMenu() = withContext(Dispatchers.IO) {
        when (val result = adminPanelRepository.saveMenu(menu)) {
            is Response.Success -> onMenuSaved(result.data)
            is Response.Failure -> onError(result.data)
        }
    }

    private fun onNewsSaved(isNewsSaved: Boolean) {
        if (isNewsSaved) {
            _viewState.postValue(ViewState.Data(AdminPanelViewState.NewsSaved))
        }
    }

    private fun onMenuSaved(isMenuSaved: Boolean) {
        if (isMenuSaved) {
            _viewState.postValue(ViewState.Data(AdminPanelViewState.MenuSaved))
        }
    }

    private fun onError(throwable: Throwable) =
        _viewState.postValue(ViewState.Error(throwable))
}