package cl.transbank.mvitest.mvibase

/**
 * Interface MviViewModel
 *
 * @author Tbk
 * @version 1.0
 * @since 1.0
 **/
interface MviViewModel<I: MviIntent, R: MviResult> {
    fun processIntent(intent: I)
    fun processResult(result: R)
}