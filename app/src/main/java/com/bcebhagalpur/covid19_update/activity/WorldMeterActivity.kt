package com.bcebhagalpur.covid19_update.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bcebhagalpur.covid19_update.R
import com.bcebhagalpur.covid19_update.adapter.WorldAdapter
import com.bcebhagalpur.covid19_update.model.World
import com.bcebhagalpur.covid19_update.util.ConnectionManager
import org.json.JSONException

class WorldMeterActivity : AppCompatActivity() {

    private lateinit var worldAdapter: WorldAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    private val worldCoronaMeter = arrayListOf<World>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_meter)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        progressLayout=findViewById(R.id.progressLayout)

        progressBar=findViewById(R.id.progressBar)

        progressLayout.visibility= View.VISIBLE


        val queue = Volley.newRequestQueue(this)
        val url = "https://coronavirus-19-api.herokuapp.com/countries"

         if(ConnectionManager().checkConnectivity(this)) {
            val jsonArrayRequest =
                JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                    try {
                        progressLayout.visibility = View.GONE


                        for (i in 0 until it.length()) {
                            val worldJsonObject = it.getJSONObject(i)
                            val worldObject =
                                World(
                                    worldJsonObject.getString("country"),
                                    worldJsonObject.getInt("cases"),
                                    worldJsonObject.getInt("todayCases"),
                                    worldJsonObject.getInt("deaths"),
                                    worldJsonObject.getInt("todayDeaths")
                                )
                            worldCoronaMeter.add(worldObject)
                            worldAdapter =
                                WorldAdapter(
                                    this,
                                    worldCoronaMeter
                                )
                            recyclerView.adapter = worldAdapter
                            recyclerView.layoutManager = layoutManager
                            recyclerView.setHasFixedSize(true)
                            println("response1 is $it")

                        }

                    } catch (e: JSONException) {
                        println("response2 is $it")
                        Toast.makeText(this, "some error occurred 2", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    println("response3 is $it")
                    Toast.makeText(this, "some error occurred", Toast.LENGTH_SHORT).show()
                })
            queue.add(jsonArrayRequest)
        }else{
             Toast.makeText(this,"network connection not found",Toast.LENGTH_LONG).show()
         }


    }
}
