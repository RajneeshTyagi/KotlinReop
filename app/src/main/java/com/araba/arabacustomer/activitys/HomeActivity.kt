package com.araba.arabacustomer.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.Toast
import com.araba.arabacustomer.R
import com.araba.arabacustomer.activity.BaseActivity
import com.araba.arabacustomer.adapters.ParentProductListAdapter
import com.araba.arabacustomer.models.CategoryResponseModel
import com.araba.arabacustomer.network.NetworkCalls
import io.reactivex.disposables.Disposable

/**
 * Created by rajneeshkumar on 11/1/17.
 */
class HomeActivity : BaseActivity() {

    companion object {
        fun start(mActivity: Activity) {
            val mIntent = Intent(mActivity, HomeActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mActivity.startActivity(mIntent)
            mActivity.finish()
        }
    }

    //    var collapsing_toolbar: CollapsingToolbarLayout? = null
//    var customToolbar: Toolbar? = null
    var drawerLayout: DrawerLayout? = null
    var informationMenuLayout: NavigationView? = null
    var cartMenuLayout: NavigationView? = null
    var cart: ImageView? = null
    var information: ImageView? = null
    var productList: RecyclerView? = null

    override fun getAllIds() {
//        collapsing_toolbar = findViewById(R.id.collapsing_toolbar)
//        customToolbar = findViewById(R.id.customToolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        informationMenuLayout = findViewById(R.id.informationMenuLayout)
        cartMenuLayout = findViewById(R.id.cartMenuLayout)
        cart = findViewById(R.id.cart)
        information = findViewById(R.id.information)
        productList = findViewById(R.id.productList)
    }

    override fun setContentView() {
        initializeView(R.layout.activity_home)
//        NetworkCalls.getCountryList(this)
//        resources.getDrawable()
    }

    override fun initView(savedInstanceState: Bundle?) {
        NetworkCalls.getCategoryList(this)

//        customToolbar?.title = ""
//        setSupportActionBar(customToolbar)
        productList?.layoutManager = LinearLayoutManager(this@HomeActivity)
        cart?.setOnClickListener { drawerLayout?.openDrawer(cartMenuLayout) }
        information?.setOnClickListener { drawerLayout?.openDrawer(informationMenuLayout) }


    }

    override fun onNext(value: Any?) {
        if (value is CategoryResponseModel) {
            var mCategoryResponseModel = value
            if (mCategoryResponseModel.status == 1) {
                productList?.adapter = ParentProductListAdapter(this, mCategoryResponseModel.data.categoryList)
            }
            Toast.makeText(this, mCategoryResponseModel.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onError(e: Throwable) {
        System.out.println("hello Error !" + e.toString())
    }

    override fun onComplete() {
    }

}