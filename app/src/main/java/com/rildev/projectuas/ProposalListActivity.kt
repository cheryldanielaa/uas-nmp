package com.rildev.projectuas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rildev.projectuas.databinding.ActivityProposalListBinding

class ProposalListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProposalListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProposalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}