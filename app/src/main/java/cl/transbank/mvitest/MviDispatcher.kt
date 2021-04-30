package cl.transbank.mvitest

import kotlinx.coroutines.flow.flow

/**
 * Class MviDispatcher
 *
 * @author Tbk
 * @version 1.0
 * @since 1.0
 **/
class MviDispatcher {
    fun dispatchAction(action: MainActivityActions) = flow {
        when (action) {
            is MainActivityActions.Add -> {
                emit(MainActivityResult.Loading)
                emit(callSomeUseCase(action.value))
            }
            is MainActivityActions.Remove -> {
                emit(MainActivityResult.Loading)
                emit(callSomeUseCase(action.value))
            }
        }
    }

    fun callSomeUseCase(int: Int): MainActivityResult {
        return MainActivityResult.Success(int)
    }
}