package com.example.mvvmhelper.app.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.example.mvvmhelper.BuildConfig
import com.example.mvvmhelper.app.event.AppViewModel
import com.example.mvvmhelper.app.event.EventViewModel
import com.example.mvvmhelper.app.weight.loadCallBack.EmptyCallback
import com.example.mvvmhelper.app.weight.loadCallBack.ErrorCallback
import com.example.mvvmhelper.app.weight.loadCallBack.LoadingCallback
import com.example.mvvmhelper.ui.activity.error.ErrorActivity
import com.example.mvvmhelper.ui.activity.welcome.WelcomeActivity
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import me.hgj.mvvmhelper.base.MvvmHelper

/**
 * @ClassName MyApp
 * @Author cheng hai hang
 * @Date 2022/5/18 21:16
 * @Description
 */

//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val appViewModel: AppViewModel by lazy { MyApp.appViewModelInstance }

//Application全局的ViewModel，用于发送全局通知操作
val eventViewModel: EventViewModel by lazy { MyApp.eventViewModelInstance }

class MyApp : Application(), ViewModelStoreOwner {

    companion object {
        lateinit var instance: MyApp
        lateinit var eventViewModelInstance: EventViewModel
        lateinit var appViewModelInstance: AppViewModel
    }

    private var appContext: Context? = null

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = baseContext
        mAppViewModelStore = ViewModelStore()
        MvvmHelper.init(this, BuildConfig.DEBUG)
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        eventViewModelInstance = getAppViewModelProvider().get(EventViewModel::class.java)
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)
        MultiDex.install(this)
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()


        //防止项目崩溃，崩溃后打开错误界面
//        CaocConfig.Builder.create()
//            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//            .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
//            .showErrorDetails(false) //是否必须显示包含错误详细信息的按钮 default: true
//            .showRestartButton(false) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
//            .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
//            .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
//            .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
//            .restartActivity(WelcomeActivity::class.java) // 重启的activity
//            .errorActivity(ErrorActivity::class.java) //发生错误跳转的activity
//            .apply()
    }

    private fun getAppContext(): Context? {
        return appContext
    }

    /**
     * 获取一个全局的ViewModel
     */
    private fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}