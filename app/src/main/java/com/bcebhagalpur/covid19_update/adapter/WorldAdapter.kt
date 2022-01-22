package com.bcebhagalpur.covid19_update.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bcebhagalpur.covid19_update.R
import com.bcebhagalpur.covid19_update.model.World

class WorldAdapter(val context: Context, private val itemList: ArrayList<World>) : RecyclerView.Adapter<WorldAdapter.WorldViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.world_recycler, parent, false)
        return WorldViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: WorldViewHolder, position: Int) {
        val world = itemList[position]
        holder.countryTv.text=world.country
        holder.totalCasesTv.text=world.cases.toString()
        holder.todayCasesTv.text=world.todayCases.toString()
        holder.totalDeathsTv.text=world.deaths.toString()
        holder.todayDeathsTv.text=world.todayDeaths.toString()

    }
    class WorldViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val countryTv:TextView=view.findViewById(R.id.countryTv)
        val totalCasesTv:TextView=view.findViewById(R.id.totalCasesTv)
        val todayCasesTv:TextView=view.findViewById(R.id.todayCasesTv)
        val totalDeathsTv:TextView=view.findViewById(R.id.totalDeathsTv)
        val todayDeathsTv:TextView=view.findViewById(R.id.todayDeathsTv)

    }

}