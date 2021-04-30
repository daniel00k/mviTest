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
    private var viewState = MainActivityViewState.Success(MainActivityState(counter = 0, text = ""))
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
                is MainActivityIntents.EditText -> {
                    mviDispatcher.dispatchAction(MainActivityActions.EditText(intent.text))
                        .collect {
                            processResult(it)
                        }
                }
            }
        }
    }

    override fun processResult(result: MainActivityResult) {
        when (result) {
            is MainActivityResult.AddOrRemove.Success -> {
                viewState = reduce(viewState, result)
                state.value = viewState
            }
            is MainActivityResult.AddOrRemove.Loading -> {
                state.value = MainActivityViewState.Loading
            }
            is MainActivityResult.UpdateText.Success -> {
                viewState = reduceEditText(viewState, result)
                state.value = viewState
            }
        }
    }

    private fun reduce(
        currentState: MainActivityViewState.Success, result: MainActivityResult.AddOrRemove.Success
    ): MainActivityViewState.Success {
        return MainActivityViewState.Success(
            MainActivityState(
                counter = currentState.state.counter + result.value,
                text = currentState.state.text
            )
        )
    }

    private fun reduceEditText(
        currentState: MainActivityViewState.Success, result: MainActivityResult.UpdateText.Success
    ): MainActivityViewState.Success = MainActivityViewState.Success(
        MainActivityState(
            counter = currentState.state.counter,
            text = result.text.toString()
        )
    )

}