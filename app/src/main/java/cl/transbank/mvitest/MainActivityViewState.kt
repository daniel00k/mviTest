package cl.transbank.mvitest

import cl.transbank.mvitest.mvibase.MviViewState

sealed class MainActivityViewState : MviViewState {
    data class Success(val state: MainActivityState) : MainActivityViewState()
    object Loading : MainActivityViewState()
}
