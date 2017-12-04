package com.araba.arabacustomer.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.araba.arabacustomer.R
import com.araba.arabacustomer.interfaces.InstancesInterface
import io.reactivex.Observer


/**
 * Created by Rajneesh on 23-Aug-17.
 */
abstract class BaseActivity : AppCompatActivity(), InstancesInterface, Observer<Any> {
    abstract public fun setContentView()
    abstract public fun getAllIds()
    abstract public fun initView(savedInstanceState: Bundle?)
    val currActivity: Activity = this
    val currContext: Context = this
    var mToolbar: Toolbar? = null

    internal var ll_inflator_container: LinearLayout? = null
    internal var mNoDataFound: TextView? = null
    var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        ll_inflator_container = findViewById(R.id.ll_inflator_container)
        mNoDataFound = findViewById(R.id.noDataFound)


        setContentView()
        getAllIds()
        initView(savedInstanceState)
    }

    fun initializeView(inner_view: Int): View {
        try {
            ll_inflator_container!!.removeAllViews();
            val inflatingInfo = layoutInflater.inflate(inner_view, ll_inflator_container, false)
            ll_inflator_container!!.addView(inflatingInfo)
            view = ll_inflator_container

        } catch (e: Exception) {
            view = null
            e.printStackTrace()
        }
        return view!!
    }

    private fun readyToolBar() {
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
    }

    fun showToolBar() {
        readyToolBar()
        mToolbar!!.visibility = View.VISIBLE
    }

    fun showToolBarBack() {
        try {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        } catch (e: Exception) {

        }

    }

    fun setTitle(actionbar_title: String) {
        try {
            supportActionBar!!.title = "" + actionbar_title
        } catch (e: Exception) {

        }

    }

    fun viewContent(mParentView: Int, mNoDataFoundFoundView: Int, mText: String) {
        ll_inflator_container!!.visibility = mParentView
        mNoDataFound!!.visibility = mNoDataFoundFoundView
        mNoDataFound!!.text = mText
    }
}