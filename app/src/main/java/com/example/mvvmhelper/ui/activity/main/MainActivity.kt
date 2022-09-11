package com.example.mvvmhelper.ui.activity.main

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import com.example.mvvmhelper.R
import com.example.mvvmhelper.app.base.MyApp
import com.example.mvvmhelper.app.base.appViewModel
import com.example.mvvmhelper.app.util.StatusBarUtil
import com.example.mvvmhelper.databinding.ActivityMainBinding
import com.example.mvvmhelper.viewmodel.request.MainViewModel
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import me.hgj.mvvmhelper.base.BaseVBActivity

class MainActivity : BaseVBActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L

    override fun initView(savedInstanceState: Bundle?) {
        //进入首页检查更新
        Beta.checkUpgrade(false, true)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainfragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
        appViewModel.appColor.value?.let {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0) }
    }

    override fun initObserver() {
        appViewModel.appColor.observe(this, Observer {
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0)
        })
    }
}