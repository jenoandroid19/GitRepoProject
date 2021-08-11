package com.jeno.gitrepoproject.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jeno.gitrepoproject.R
import com.jeno.gitrepoproject.adapter.RepoListAdapter
import com.jeno.gitrepoproject.databinding.ActivityRepoListBinding
import com.jeno.gitrepoproject.helper.CommonHelper
import com.jeno.gitrepoproject.jdos.RepoListData
import com.jeno.gitrepoproject.viewmodel.RepoListViewModel

class RepoListActivity : AppCompatActivity() {

    lateinit var mRecyclerViewAdapter: RepoListAdapter
    lateinit var mCommonHelper : CommonHelper
    lateinit var mProgressDialog: Dialog
    lateinit var mActivityRepoBinding: ActivityRepoListBinding
    lateinit var mViewModel: RepoListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityRepoBinding = DataBindingUtil.setContentView(this,R.layout.activity_repo_list)
        mCommonHelper = CommonHelper()
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initRecyclerView()
        createData()
    }

    private fun initRecyclerView() {

        mActivityRepoBinding.repoview.apply {
            layoutManager = LinearLayoutManager(this@RepoListActivity)
            mRecyclerViewAdapter = RepoListAdapter()
            adapter = mRecyclerViewAdapter


        }
    }

    fun createData() {

        mActivityRepoBinding.refreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                if (mCommonHelper.isNetworkAvailable(this@RepoListActivity)){
                    fetchDataFromAPI(true)
                }else{
                    hideProgressDialog()
                    Toast.makeText(this@RepoListActivity,"Please turn on your mobile data",Toast.LENGTH_SHORT).show()
                }
            }
        })

        mViewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java)
        mViewModel.apiResponse.observe(this, Observer<Boolean>{
            if (it){
                hideProgressDialog()
            }else{
                hideProgressDialog()
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.allRepoList.observe(this, Observer {
            if(it != null && it.size > 0) {
                showHideViews(false)
                mRecyclerViewAdapter.setListData(it as ArrayList<RepoListData>)
                mRecyclerViewAdapter.notifyDataSetChanged()

            } else if (mCommonHelper.isNetworkAvailable(this)){
                fetchDataFromAPI(false)
            }else{
                showHideViews(true)
            }
        })

        mActivityRepoBinding.retryButton.setOnClickListener {
            if (mCommonHelper.isNetworkAvailable(this)){
                fetchDataFromAPI(false)
            }else{
                Toast.makeText(this,"Please turn on your mobile data",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showHideViews(pShowError : Boolean){
        if (pShowError){
            mActivityRepoBinding.networkRetryLayout.visibility = View.VISIBLE
            mActivityRepoBinding.refreshLayout.visibility = View.GONE
        }else{
            mActivityRepoBinding.networkRetryLayout.visibility = View.GONE
            mActivityRepoBinding.refreshLayout.visibility = View.VISIBLE
        }
    }

    fun fetchDataFromAPI(pIsSwipeRefresh : Boolean){
        showHideViews(false)
        if (!pIsSwipeRefresh){
            showProgressDialog()
        }
        mViewModel.makeApiCall()
    }

    fun showProgressDialog() {
        mProgressDialog = mCommonHelper.progressDialog(this,"Fetching data...")
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        if (this::mProgressDialog.isInitialized && mProgressDialog!=null && mProgressDialog.isShowing){
            mProgressDialog.dismiss()
        }
        if (mActivityRepoBinding.refreshLayout.isRefreshing){
            mActivityRepoBinding.refreshLayout.isRefreshing = false
        }
    }
}