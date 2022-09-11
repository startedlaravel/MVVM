package com.example.mvvmhelper.ui.activity.error

import android.content.ClipData
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.ToastUtils
import com.example.mvvmhelper.app.ext.clipboardManager
import com.example.mvvmhelper.app.ext.showMessage
import com.example.mvvmhelper.app.util.SettingUtil
import com.example.mvvmhelper.app.util.StatusBarUtil
import com.example.mvvmhelper.databinding.ActivityErrorBinding
import me.hgj.mvvmhelper.base.BaseDbActivity
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.clickNoRepeat

class ErrorActivity : BaseDbActivity<BaseViewModel, ActivityErrorBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        StatusBarUtil.setColor(this, SettingUtil.getColor(this), 0)
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        mBind.errorRestart.clickNoRepeat{
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        mBind.errorSendError.clickNoRepeat {
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessage(it, "发现有Bug不去打作者脸？", "必须打", {
                    val mClipData = ClipData.newPlainText("errorLog", it)
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager?.setPrimaryClip(mClipData)
                    ToastUtils.showShort("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=824868922"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtils.showShort("请先安装QQ")
                    }
                }, "我不敢")
            }
        }
    }
}