package com.rildev.projectuas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.FragmentWhoWeAreFragmentBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.HashMap

class WhoWeAreFragment : Fragment() {
    private lateinit var binding:FragmentWhoWeAreFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        show()

        binding = FragmentWhoWeAreFragmentBinding.inflate(inflater, container, false)
        binding.btnLike.setOnClickListener {
            val q = Volley.newRequestQueue(activity)
            val url = "https://ubaya.xyz/native/160422026/project/set_likes.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener { response ->
                    Log.d("cekparams", response)

                    val obj = JSONObject(response)
                    if (obj.getString("result") == "OK") {
                        show()
                    } else {
                        Toast.makeText(activity, "Error updating likes", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("apiresult", error.message.toString())
                }) {
            }
            q.add(stringRequest)
        }
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

    private fun show() {
        val url = "https://ubaya.xyz/native/160422026/project/aboutus.php"
        val q = Volley.newRequestQueue(activity)

        //objek StringRequest punya 4 parameter -> 1. request method || 2. url || 3. listener kalo sukses -> if server status OK || 4. listener kalo error
        var stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apiresult", it) //it contains JSON string from API
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    val sType = object : TypeToken<List<AboutUs>>() {}.type
                    val aboutUs: List<AboutUs> = Gson().fromJson(data.toString(), sType)

                    val first = aboutUs[0]

                    binding.txtNama.text = first.name
                    binding.txtDescription.text = first.description

                    val imageUrl = first.photo
                    val builder = Picasso.Builder(binding.root.context)
                    builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                    Picasso.get().load(imageUrl).into(binding.imgLogo)

                    var newLikes = first.num_likes
                    binding.btnLike.text = "$newLikes"

                    Log.d("cekisiarray", aboutUs.toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }
}