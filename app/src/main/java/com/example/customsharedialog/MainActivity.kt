package com.example.customsharedialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customsharedialog.databinding.ActivityMainBinding
import com.example.customsharedialog.feature.sharedialog.presentation.ShareBottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnShare.setOnClickListener {
            val share = ShareBottomSheetDialog("A simple text to share!")
            share.show(supportFragmentManager, "share")
        }
    }

}