package com.jeno.gitrepoproject.ui

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
    var mRepoList : ArrayList<RepoListData> = ArrayList()
    var mRepoCopyList : ArrayList<RepoListData> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityRepoBinding = DataBindingUtil.setContentView(this, R.layout.activity_repo_list)
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

    private fun createData() {

        mActivityRepoBinding.refreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if (mCommonHelper.isNetworkAvailable(this@RepoListActivity)) {
                    fetchDataFromAPI(true)
                } else {
                    hideProgressDialog()
                    Toast.makeText(
                        this@RepoListActivity,
                        "Please turn on your mobile data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        mActivityRepoBinding.searchRepo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                searchRepoList(p0.toString())
            }
        })

        mViewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java)
        mViewModel.apiResponse.observe(this, Observer<Boolean> {
            if (it) {
                hideProgressDialog()
            } else {
                hideProgressDialog()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.allRepoList.observe(this, Observer {
            if (it != null && it.size > 0) {
                showHideViews(false)
                mRepoList.addAll(it)
                updateAdapter(it as ArrayList<RepoListData>)

            } else if (mCommonHelper.isNetworkAvailable(this)) {
                fetchDataFromAPI(false)
            } else {
                showHideViews(true)
            }
        })

        mActivityRepoBinding.retryButton.setOnClickListener {
            if (mCommonHelper.isNetworkAvailable(this)){
                fetchDataFromAPI(false)
            }else{
                Toast.makeText(this, "Please turn on your mobile data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun searchRepoList(pSearchRepo: String){
        mRepoCopyList.clear()
        if (pSearchRepo.isEmpty()){
            mRepoCopyList.addAll(mRepoList)
        }else{
            for (repository in mRepoList){
                if (repository.repositoryName.toLowerCase().contains(pSearchRepo) || repository.language?.toLowerCase()?.contains(pSearchRepo)!!){
                    mRepoCopyList.add(repository)
                }
            }
        }

        if (mRepoCopyList.isEmpty()){
            mActivityRepoBinding.errorMsg.visibility = View.VISIBLE
        }else{
            mActivityRepoBinding.errorMsg.visibility = View.GONE
        }
        updateAdapter(mRepoCopyList)
    }

    private fun updateAdapter(pRepoList : ArrayList<RepoListData>){
        mRecyclerViewAdapter.setListData(pRepoList)
        mRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun showHideViews(pShowError: Boolean){
        if (pShowError){
            mActivityRepoBinding.networkRetryLayout.visibility = View.VISIBLE
            mActivityRepoBinding.refreshLayout.visibility = View.GONE
        }else{
            mActivityRepoBinding.networkRetryLayout.visibility = View.GONE
            mActivityRepoBinding.refreshLayout.visibility = View.VISIBLE
        }
    }

    private fun fetchDataFromAPI(pIsSwipeRefresh: Boolean){
        showHideViews(false)
        if (!pIsSwipeRefresh){
            showProgressDialog()
        }
        mViewModel.makeApiCall()
    }

    private fun showProgressDialog() {
        mProgressDialog = mCommonHelper.progressDialog(this, "Fetching data...")
        mProgressDialog.show()
    }

    private fun hideProgressDialog() {
        if (this::mProgressDialog.isInitialized && mProgressDialog!=null && mProgressDialog.isShowing){
            mProgressDialog.dismiss()
        }
        if (mActivityRepoBinding.refreshLayout.isRefreshing){
            mActivityRepoBinding.refreshLayout.isRefreshing = false
        }
    }
}