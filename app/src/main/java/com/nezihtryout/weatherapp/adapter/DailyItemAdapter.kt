package com.nezihtryout.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nezihtryout.weatherapp.R
import com.nezihtryout.weatherapp.data.model.DailyModel


class DailyItemAdapter(private val dataSet: ArrayList<DailyModel>?) :
    RecyclerView.Adapter<DailyItemAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTv: TextView = view.findViewById(R.id.day_tv)
        val weatherIv: ImageView = view.findViewById(R.id.weather_iv_daily)
        val dayDegTv: TextView = view.findViewById(R.id.day_deg_tv)
        val nightDegTv: TextView = view.findViewById(R.id.night_deg_tv)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.daily_weather, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dayTv.text = dataSet?.get(position + 1)?.dt
        viewHolder.dayDegTv.text = dataSet?.get(position + 1)?.temp?.day
        viewHolder.nightDegTv.text = dataSet?.get(position + 1)?.temp?.night

        when (dataSet?.get(position + 1)?.weather?.get(0)?.icon) {
            "01d" -> viewHolder.weatherIv.setImageResource(R.drawable._01d)
            "01n" -> viewHolder.weatherIv.setImageResource(R.drawable._01n)
            "02d" -> viewHolder.weatherIv.setImageResource(R.drawable._02d)
            "02n" -> viewHolder.weatherIv.setImageResource(R.drawable._02n)
            "03d" -> viewHolder.weatherIv.setImageResource(R.drawable._03d)
            "03n" -> viewHolder.weatherIv.setImageResource(R.drawable._03d)
            "04d" -> viewHolder.weatherIv.setImageResource(R.drawable._04d)
            "04n" -> viewHolder.weatherIv.setImageResource(R.drawable._04d)
            "09d" -> viewHolder.weatherIv.setImageResource(R.drawable._09d)
            "09n" -> viewHolder.weatherIv.setImageResource(R.drawable._09d)
            "10d" -> viewHolder.weatherIv.setImageResource(R.drawable._10d)
            "10n" -> viewHolder.weatherIv.setImageResource(R.drawable._10n)
            "11d" -> viewHolder.weatherIv.setImageResource(R.drawable._11d)
            "11n" -> viewHolder.weatherIv.setImageResource(R.drawable._11d)
            "13d" -> viewHolder.weatherIv.setImageResource(R.drawable._13d)
            "13n" -> viewHolder.weatherIv.setImageResource(R.drawable._13d)
            "50d" -> viewHolder.weatherIv.setImageResource(R.drawable._50d)
            "50n" -> viewHolder.weatherIv.setImageResource(R.drawable._50d)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet!!.size - 1

}