package com.agustin.sample.viewmodels

import androidx.lifecycle.ViewModel
import com.agustin.sample.repos.MainRepository


class MainViewModel(
    private val mainRepo: MainRepository
) : ViewModel() {

}

sealed class State {
    object Loading : State()
    object Success : State()
    data class Error(val exception: Throwable) : State()
}