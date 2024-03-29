package com.uas.halolaw.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.uas.halolaw.MainModel
import com.uas.halolaw.PengacaraAdapter
import com.uas.halolaw.R
import com.uas.halolaw.fragmentview.tvFragmentEditProfile
import com.uas.halolaw.retrofit.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"


    private lateinit var pengacaraAdapter: PengacaraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnmenuProfile.setOnClickListener{
            Intent(this@MainActivity, tvFragmentEditProfile::class.java).also {
                startActivity(it)
            }
        }
        setupRecyclerView()
        getDataFromApi()
    }

    private fun setupRecyclerView(){
        pengacaraAdapter = PengacaraAdapter(arrayListOf(), object : PengacaraAdapter.OnAdapterListener {
            override fun onClick(result: MainModel.Result) {
                startActivity(
                    Intent(this@MainActivity, DetailActivity::class.java)
                        .putExtra("intent_title", result.title)
                        .putExtra("intent_image", result.image)
                )
            }
        })
        recyclerView.apply {

            layoutManager = LinearLayoutManager(context)
            adapter = pengacaraAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data()
            .enqueue(object : Callback<MainModel> {
                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult( response.body()!! )
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    private fun showResult(results: MainModel) {
        for (result in results.result) printLog( "title: ${result.title}" )
        pengacaraAdapter.setData( results.result )
    }
}
