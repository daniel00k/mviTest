package cl.transbank.mvitest

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cl.transbank.mvitest.databinding.ActivityMainBinding
import cl.transbank.mvitest.mvibase.MviView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MviView<MainActivityIntents, MainActivityViewState>,
    View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        lifecycleScope.launch {
            viewModel.state.collect {
                render(it)
            }
        }
    }

    private fun setListeners() {
        binding.buttonLess.setOnClickListener(this)
        binding.buttonPlus.setOnClickListener(this)
    }

    override fun render(viewState: MainActivityViewState) {
        when (viewState) {
            is MainActivityViewState.Success -> {
                binding.clickResult.text = viewState.state.counter.toString()
                binding.clickResult.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
            is MainActivityViewState.Loading -> {
                binding.clickResult.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun dispatch(intent: MainActivityIntents) {
        viewModel.processIntent(intent)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLess -> dispatch(MainActivityIntents.SubtractOne(-1))
            binding.buttonPlus -> dispatch(MainActivityIntents.AddOne(1))
        }
    }

}