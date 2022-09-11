package com.example.mvvmhelper.app.weight.loadCallBack

import com.example.mvvmhelper.R
import com.kingja.loadsir.callback.Callback


class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}