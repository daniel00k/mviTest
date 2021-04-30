package cl.transbank.mvitest

import cl.transbank.mvitest.mvibase.MviIntent

sealed class MainActivityIntents : MviIntent {
    data class AddOne(val input: Int) : MainActivityIntents()
    data class SubtractOne(val input: Int) : MainActivityIntents()
    data class EditText(val text: CharSequence?) : MainActivityIntents()
}
