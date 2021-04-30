package cl.transbank.mvitest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.transbank.mvitest.mvibase.MviViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Class MainActivityViewModel
 *
 * @author Tbk
 * @version 1.0
 * @since 1.0
 **/
class MainActivityViewModel : ViewModel(), MviViewModel<MainActivityIntents, MainActivityResult> {
    val mviDispatcher = MviDispatcher()
    private var viewState = MainActivityViewState.Success(MainActivityState(0))
    val state = MutableStateFlow<MainActivityViewState>(viewState)

    override fun processIntent(intent: MainActivityIntents) {
        viewModelScope.launch {
            when (intent) {
                is MainActivityIntents.AddOne -> {
                    mviDispatcher.dispatchAction(MainActivityActions.Add(intent.input)).collect {
                        processResult(it)
                    }
                }
                is MainActivityIntents.SubtractOne -> {
                    mviDispatcher.dispatchAction(MainActivityActions.Remove(intent.input)).collect {
                        processResult(it)
                    }
                }
            }
        }
    }

    override fun processResult(result: MainActivityResult) {
        when (result) {
            is MainActivityResult.Success -> {
                viewState = reduce(viewState, result)
                state.value = viewState
            }
            is MainActivityResult.Loading -> {
                state.value = MainActivityViewState.Loading
            }
        }
    }

    private fun reduce(
        currentState: MainActivityViewState.Success, result: MainActivityResult.Success
    ): MainActivityViewState.Success {
        return MainActivityViewState.Success(MainActivityState(currentState.state.counter + result.value))
    }

}