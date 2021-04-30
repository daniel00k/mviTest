package cl.transbank.mvitest

import cl.transbank.mvitest.mvibase.MviResult

sealed class MainActivityResult: MviResult {
    data class Success(val value: Int) : MainActivityResult()
    object Loading : MainActivityResult()
}
