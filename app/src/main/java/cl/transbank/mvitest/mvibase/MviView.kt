package cl.transbank.mvitest.mvibase

/**
 * Interface MviView
 *
 * @author Tbk
 * @version 1.0
 * @since 1.0
 **/
interface MviView<I: MviIntent, S: MviViewState> {
    fun render(viewState: S)
    fun dispatch(intent: I)
}