package com.jeno.gitrepoproject.adapter

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jeno.gitrepoproject.R
import com.jeno.gitrepoproject.jdos.RepoListData

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.MyViewHolder>()  {
    var items = ArrayList<RepoListData>()

    fun setListData(data: ArrayList<RepoListData>) {
        this.items = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)

        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvTitle = view.findViewById<TextView>(R.id.tvFirstName)
        val tvDesc = view.findViewById<TextView>(R.id.tvDesc)
        val tvRepoName = view.findViewById<TextView>(R.id.tvReponame)
        val tvStartCount = view.findViewById<TextView>(R.id.tvCount)
        val tvLanguage = view.findViewById<TextView>(R.id.tvLanguage)
        val tvLangColor = view.findViewById<TextView>(R.id.tvLangColor)

        fun bind(data: RepoListData) {
            tvTitle.text = data.username
            if(!TextUtils.isEmpty(data.description)) {
                tvDesc.text = data.description
            } else {
                tvDesc.text = "No Desc available."
            }
            tvRepoName.text = data.repositoryName
            tvStartCount.text = data.totalStars.toString()
            tvLanguage.text = data.language
            if (data.languageColor != null){
                tvLangColor.setBackgroundColor(Color.parseColor(data.languageColor))
            }
        }

    }
}