package com.breaksol.buttontapper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.breaksol.buttontapper.R
import com.breaksol.buttontapper.database.Record
import com.breaksol.buttontapper.databinding.ItemRecordBinding

class RecordAdapter(private val dataSet: List<Record>, private val context: Context) :
        RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRecordBinding.bind(view)

        fun colorBestPosition(color: Int) {
            binding.tvPlace.setTextColor(color)
            binding.tvScore.setTextColor(color)
            binding.tvRows.setTextColor(color)
            binding.tvColumns.setTextColor(color)
            binding.tvTime.setTextColor(color)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_record, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvPlace.text = "${position + 1}"
        viewHolder.binding.tvScore.text = dataSet[position].result.toString()
        viewHolder.binding.tvRows.text = dataSet[position].rows.toString()
        viewHolder.binding.tvColumns.text = dataSet[position].columns.toString()
        viewHolder.binding.tvTime.text = dataSet[position].time.toString()

        when(position) {
            0 -> viewHolder.colorBestPosition(ContextCompat.getColor(context, R.color.gold))
            1 -> viewHolder.colorBestPosition(ContextCompat.getColor(context, R.color.dark_silver))
            2 -> viewHolder.colorBestPosition(ContextCompat.getColor(context, R.color.bronze))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}