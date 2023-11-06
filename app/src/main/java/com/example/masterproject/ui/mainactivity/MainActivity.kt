package com.example.masterproject.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.masterproject.App
import com.example.masterproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(App.instance.getTestDataUseCase)
        )[MainViewModel::class.java]

        viewModel.getData()

        viewModel.result.observe(this) {
            binding.mainText.text = viewModel.result.value
        }
    }
}