package com.rildev.projectuas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.FragmentOurScheduleBinding
import org.json.JSONObject

private const val KEY_EVENTS="schedule"

//krn yg ditampilin ecycler view, jd gak pake ListFragment, tpi Fragment biasa
class OurScheduleFragment : Fragment() {
    //Our Schedule ini menyimpan list dari schedule yang ada
    //array kosongan utk menampung data yang dikirimkan dari main activity
    private var scheduleList:ArrayList<ScheduleBank> = ArrayList()

    //deklarasikan binding
    private lateinit var binding: FragmentOurScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        isiSchedules()
    }

    //tampilin design fragmentnya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOurScheduleBinding.inflate(inflater, container, false)
        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.setHasFixedSize(true) //utk memastikan setiap card pny ukuran yg sama
        isiSchedules()
        //Inflate the layout for this fragment
        return binding.root
    }

    private fun isiSchedules() {
        val url = "https://ubaya.xyz/native/160422026/project/schedulecard.php"
        val q = Volley.newRequestQueue(activity)

        //objek StringRequest punya 4 parameter -> 1. request method || 2. url || 3. listener kalo sukses -> if server status OK || 4. listener kalo error
        var stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                //Log.d sama Log.e itu sebenernya cuman beda warna
                //cek isi apiresult, bener ada isinya atau ga
                Log.d("apiresult", it) //it contains JSON string from API

                //ini ambil structur JSON paling atas yaitu {}JSON
                val obj = JSONObject(it)

                //check for result key. If value is OK, then proceed
                //cara panggil obj pake getString --> ini tergantung isinya, bisa getString atau getInt
                if (obj.getString("result") == "OK") {
                    //data --> ini ambil structur di JSON yaitu []data
                    //karena JSON bentuknya object, sedangkan data bentuknya array, maka convert object jadi array
                    val data = obj.getJSONArray("data")

                    //kasi tau GSON kalo JSONnya itu array of objects, ga cmn 1 objek aja
                    val sType = object : TypeToken<ArrayList<ScheduleBank>>() {}.type

                    //deseralize gson string into ArrayList ScheduleBank
                    //fromJson() -> parameter 1: data yg mau dijadiin ArrayList || parameter 2: Type of object yang mau di-deseralize into
                    //fromJson parameter pertama harus string makanya di toString()
                    val schedules: ArrayList<ScheduleBank> = Gson().fromJson(
                        data.toString(),
                        sType
                    )

                    scheduleList.clear()
                    scheduleList.addAll(schedules)

                    binding.recSchedule.adapter = OurScheduleAdapter(scheduleList)

                    //Logcat the playlists arraylist to prove that it contains data
                    Log.d("cekisiarray", schedules.toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}