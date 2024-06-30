package com.glacierpower.rickandmorty

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.glacierpower.rickandmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _viewBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        _viewBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        super.onCreate(savedInstanceState)
        setContentView(_viewBinding.root)

    }
}