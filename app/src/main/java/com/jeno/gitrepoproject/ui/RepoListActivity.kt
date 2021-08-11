package com.jeno.gitrepoproject.ui

import android.app.Dialog
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
import com.jeno.gitrepoproject.helper.ViewHelper
import com.jeno.gitrepoproject.jdos.RepoListData
import com.jeno.gitrepoproject.viewmodel.RepoListViewModel

class RepoListActivity : AppCompatActivity() {

    lateinit var recyclerViewAdapter: RepoListAdapter
    lateinit var mRepoView : RecyclerView
    lateinit var mViewHelper : ViewHelper
    lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        mRepoView = findViewById(R.id.repoview)
        mViewHelper = ViewHelper()
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
        viewModel.apiResponse.observe(this, Observer<Boolean>{
            if (it){
                hideProgressDialog()
            }else{
                hideProgressDialog()
            }
        })

        viewModel.allRepoList.observe(this, Observer {
            if(it != null && it.size > 0) {
                recyclerViewAdapter.setListData(it as ArrayList<RepoListData>)
                recyclerViewAdapter.notifyDataSetChanged()

            } else {
                showProgressDialog()
                viewModel.makeApiCall()
            }
        })
    }

    fun showProgressDialog() {
        mProgressDialog = mViewHelper.progressDialog(this,"Fetching data...")
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog!=null && mProgressDialog.isShowing){
            mProgressDialog.dismiss()
        }
    }
}