package cl.transbank.mvitest

import cl.transbank.mvitest.mvibase.MviResult

sealed class MainActivityResult : MviResult {
    sealed class AddOrRemove : MainActivityResult() {
        data class Success(val value: Int) : AddOrRemove()
        object Loading : MainActivityResult()
    }

    sealed class UpdateText : MainActivityResult() {
        data class Success(val text: CharSequence?) : UpdateText()
        object Loading : MainActivityResult()
    }
}
