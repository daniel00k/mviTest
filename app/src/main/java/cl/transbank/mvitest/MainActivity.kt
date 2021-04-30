package cl.transbank.mvitest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dispatch(MainActivityIntents.EditText(s))
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun render(viewState: MainActivityViewState) {
        when (viewState) {
            is MainActivityViewState.Success -> {
                binding.clickResult.text = viewState.state.counter.toString()
                binding.text.text = viewState.state.text
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