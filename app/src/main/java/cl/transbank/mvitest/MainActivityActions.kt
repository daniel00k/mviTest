package cl.transbank.mvitest

sealed class MainActivityActions {
    data class Add(val value: Int) : MainActivityActions()
    data class Remove(val value: Int) : MainActivityActions()
}
