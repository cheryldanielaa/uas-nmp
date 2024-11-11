package com.rildev.projectuas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding
import com.rildev.projectuas.databinding.FragmentWhoWeAreFragmentBinding


class WhoWeAreFragment : Fragment() {
    private lateinit var binding:FragmentWhoWeAreFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhoWeAreFragmentBinding.inflate(inflater, container, false)
            binding.btnLike.setOnClickListener(){
            var likeCount = binding.btnLike.text.toString().toInt()
            likeCount++
            binding.btnLike.text = likeCount.toString()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WhoWeAreFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}