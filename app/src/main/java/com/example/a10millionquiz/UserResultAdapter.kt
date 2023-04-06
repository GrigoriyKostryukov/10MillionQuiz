package com.example.a10millionquiz

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a10millionquiz.database.UserResult


class UserResultViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val res: Resources = itemView.context.resources
    private val username: TextView = itemView.findViewById(R.id.username)
    private val score: TextView = itemView.findViewById(R.id.user_score)

    fun bind(item: UserResult) {
        username.text = item.name
        score.text = item.maxScore.toString()
    }

    companion object {
        fun from(parent: ViewGroup): UserResultViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.user_result_list_item, parent, false)
            return UserResultViewHolder(view)
        }
    }
}

class UserResultAdapter : RecyclerView.Adapter<UserResultViewHolder>() {

    var data = listOf<UserResult>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: UserResultViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserResultViewHolder {
        return UserResultViewHolder.from(parent)
    }
}