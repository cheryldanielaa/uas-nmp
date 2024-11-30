package com.rildev.projectuas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rildev.projectuas.databinding.ActivityApplyTeamBinding

class ApplyTeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityApplyTeamBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        }
    }
}