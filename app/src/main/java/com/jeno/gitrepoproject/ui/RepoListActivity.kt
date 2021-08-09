package com.jeno.gitrepoproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jeno.gitrepoproject.R
import com.jeno.gitrepoproject.adapter.RepoListAdapter
import com.jeno.gitrepoproject.jdos.RepoListData
import com.jeno.gitrepoproject.viewmodel.RepoListViewModel

class RepoListActivity : AppCompatActivity() {

    lateinit var recyclerViewAdapter: RepoListAdapter
    lateinit var mRepoView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        mRepoView = findViewById(R.id.repoview)

        initRecyclerView()
        createData()
    }

    private fun initRecyclerView() {

        mRepoView.apply {
            layoutManager = LinearLayoutManager(this@RepoListActivity)
            recyclerViewAdapter = RepoListAdapter()
            adapter = recyclerViewAdapter


        }
    }

    fun createData() {

        val viewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java)
        viewModel.getRecyclerListDataObserver().observe(this, Observer<ArrayList<RepoListData>>{

            if(it != null) {
                recyclerViewAdapter.setListData(it)
                recyclerViewAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, "Error in getting data from api.", Toast.LENGTH_LONG).show()
            }

        })
        viewModel.makeApiCall()
    }
}