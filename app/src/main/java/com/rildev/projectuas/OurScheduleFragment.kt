package com.rildev.projectuas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.rildev.projectuas.databinding.FragmentOurScheduleBinding
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding

private const val KEY_EVENTS="schedule"

//krn yg ditampilin ecycler view, jd gak pake ListFragment, tpi Fragment biasa
class OurScheduleFragment : Fragment() {
    //Our Schedule ini menyimpan list dari schedule yang ada

    //array kosongan utk menampung data yang dikirimkan dari main activity
    private var schedule:ArrayList<ScheduleBank> = ArrayList()

    //deklarasikan binding
    private lateinit var binding: FragmentOurScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        arguments?.let {
            //terima cabang dari mainactivity trs disimpan di list kosong yang udah
            //dideclare diatas
                schedule = it.getParcelableArrayList<ScheduleBank>(KEY_EVENTS) as ArrayList<ScheduleBank>
        }
    }

    //tampilin design fragmentnya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOurScheduleBinding.inflate(inflater, container, false)
        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.setHasFixedSize(true) //utk memastikan setiap card pny ukuran yg sama
        //kirimin parameter listschedule ke OurScheduleAdapter
        binding.recSchedule.adapter = OurScheduleAdapter(schedule)
        //Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(schedule: ArrayList<ScheduleBank>) =
            OurScheduleFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_EVENTS, schedule) //events ini buat receive ArrayList dari MainActivity
                }
            }
    }
}