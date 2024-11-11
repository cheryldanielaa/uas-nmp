package com.rildev.projectuas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rildev.projectuas.databinding.ActivityAchievementDetailsBinding.inflate
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding
import com.rildev.projectuas.databinding.FragmentWhoWeAreBinding

/**
 * A simple [Fragment] subclass.
 * Use the [WhoWeAreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class WhoWeAreFragment : Fragment() {
    private lateinit var binding: FragmentWhoWeAreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhoWeAreBinding.inflate(inflater, container, false)
        binding.btnLike.setOnClickListener(){
            var likeCount = binding.btnLike.text.toString().toInt()
            likeCount++
            binding.btnLike.text = likeCount.toString()
        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WhoWeAreFragment().apply {
                arguments = Bundle().apply {
                    //putString(ARGS_LIKE, binding.btnLike.text)
                }
            }
    }

}