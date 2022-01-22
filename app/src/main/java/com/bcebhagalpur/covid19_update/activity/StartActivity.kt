package com.bcebhagalpur.covid19_update.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.Toast
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bcebhagalpur.covid19_update.R
import com.bcebhagalpur.covid19_update.adapter.StateAdapter
import com.bcebhagalpur.covid19_update.classes.Client
import com.bcebhagalpur.covid19_update.classes.NotificationWorker
import com.bcebhagalpur.covid19_update.classes.Response
import com.bcebhagalpur.covid19_update.classes.StatewiseItem
import com.bcebhagalpur.covid19_update.util.ConnectionManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_india_meter.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class StartActivity : AppCompatActivity() {
    lateinit var stateAdapter: StateAdapter
    lateinit var imgCorona: ImageView

//    @InternalCoroutinesApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_india_meter)
    list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header, list, false))

    fetchResult()
    swipeToRefresh.setOnRefreshListener {
        fetchResult()
    }

        initWorker()
        list.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (list.getChildAt(0) != null) {
                    swipeToRefresh.isEnabled = list.firstVisiblePosition === 0 && list.getChildAt(
                        0
                    ).getTop() === 0
                }
            }
        })
        imgCorona = findViewById(R.id.imgCorona)
        imgCorona.setOnClickListener { startActivity(Intent(this, WorldMeterActivity::class.java)) }
    }

    private fun fetchResult() {
        if(ConnectionManager().checkConnectivity(this)) {
            try {
                GlobalScope.launch {
                    val response = withContext(Dispatchers.IO) { Client.api.clone().execute() }
                    if (response.isSuccessful) {
                        swipeToRefresh.isRefreshing = false
                        val data = Gson().fromJson(response.body?.string(), Response::class.java)
//                Log.i("info", response.body?.string())

                        launch(Dispatchers.Main) {
                            bindCombineData(data.statewise[0])
                            bindStateWiseData(data.statewise.subList(1, data.statewise.size))
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "No connection found", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, "Network connection not found", Toast.LENGTH_LONG).show()
        }
}

    private fun bindStateWiseData(subList: List<StatewiseItem>) {
        stateAdapter =
            StateAdapter(subList)
        list.adapter = stateAdapter
    }

    private fun bindCombineData(data: StatewiseItem) {
        val lastUpdatedTime = data.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastUpdateTv.text = "Last Updated\n ${getTimeAgo(
            simpleDateFormat.parse(lastUpdatedTime)
        )}"

        confirmedTv.text = data.confirmed
        activeTv.text = data.active
        recoveredTv.text = data.recovered
        deceasedTv.text = data.deaths
    }

//    @InternalCoroutinesApi
    @InternalCoroutinesApi
    private fun initWorker() {
//        val constraints = Constraints.
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()

        val constraints=androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "JOB_TAG",
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }
}

fun getTimeAgo(past: Date):String {
    val now= Date()
    val seconds= TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
    val minutes= TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
    val hours= TimeUnit.MILLISECONDS.toHours(now.time - past.time)

    return when{
        seconds < 60 -> {
            "Few seconds ago"
        }

        minutes < 60 -> {
            "$minutes minutes ago"
        }
        hours < 24 -> {
            "$hours hour ${minutes % 60} min ago"
        }
        else ->{
            SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(past).toString()
        }
    }
}

