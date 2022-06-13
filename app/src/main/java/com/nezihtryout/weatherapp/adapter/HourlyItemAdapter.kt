package com.nezihtryout.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nezihtryout.weatherapp.R
import com.nezihtryout.weatherapp.data.model.HourlyModel

class HourlyItemAdapter(private val dataSet: ArrayList<HourlyModel>?) :
    RecyclerView.Adapter<HourlyItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hourInfoTv: TextView = view.findViewById(R.id.hour_info_tv)
        val weatherIv: ImageView = view.findViewById(R.id.weather_iv_hourly)
        val degreeTv: TextView = view.findViewById(R.id.degree_tv)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.hourly_weather, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val hour = dataSet?.get(position)?.dt
        viewHolder.hourInfoTv.text = hour.toString()
        when (dataSet?.get(position)?.weather?.get(0)?.icon) {
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
        viewHolder.degreeTv.text = dataSet?.get(position)?.temp.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = 24

}
