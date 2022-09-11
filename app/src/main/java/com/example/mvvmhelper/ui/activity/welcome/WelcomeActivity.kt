package com.example.mvvmhelper.ui.activity.welcome

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvmhelper.R
import com.example.mvvmhelper.ui.activity.main.MainActivity
import com.example.mvvmhelper.app.util.CacheUtil
import com.example.mvvmhelper.app.util.SettingUtil
import com.example.mvvmhelper.app.weight.banner.WelcomeBannerAdapter
import com.example.mvvmhelper.app.weight.banner.WelcomeBannerViewHolder
import com.example.mvvmhelper.databinding.ActivityWelcomeBinding
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.activity_welcome.*
import me.hgj.mvvmhelper.base.BaseDbActivity
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.gone
import me.hgj.mvvmhelper.ext.visible

class WelcomeActivity : BaseDbActivity<BaseViewModel, ActivityWelcomeBinding>() {
    private var resList = arrayOf("唱", "跳", "r a p")

    private lateinit var mViewPager: BannerViewPager<String, WelcomeBannerViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        //防止出现按Home键回到桌面时，再次点击重新进入该界面bug
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }
        mBind.click = ProxyClick()
        welcome_baseview.setBackgroundColor(SettingUtil.getColor(this))
        mViewPager = findViewById(R.id.banner_view)
        if (CacheUtil.isFirst()) {
            //是第一次打开App 显示引导页
            welcome_image.gone()
            mViewPager.apply {
                adapter = WelcomeBannerAdapter()
                setLifecycleRegistry(lifecycle)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if (position == resList.size - 1) {
                            welcomeJoin.visible()
                        } else {
                            welcomeJoin.gone()
                        }
                    }
                })
                create(resList.toList())
            }
        } else {
            //不是第一次打开App 0.3秒后自动跳转到主页
            welcome_image.visible()
            mViewPager.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                //带点渐变动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 300)
        }
    }

    inner class ProxyClick {
        fun toMain() {
            CacheUtil.setFirst(false)
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()
            //带点渐变动画
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

}