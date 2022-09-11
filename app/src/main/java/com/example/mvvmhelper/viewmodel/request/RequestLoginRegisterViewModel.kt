package com.example.mvvmhelper.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.mvvmhelper.viewmodel.state.ResultState
import com.google.firebase.auth.UserInfo
import me.hgj.mvvmhelper.base.BaseViewModel

/**
 * @ClassName RequestLoginRegisterViewModel
 * @Author cheng hai hang
 * @Date 2022/5/29 19:30
 * @Description
 */
class RequestLoginRegisterViewModel : BaseViewModel() {

    //方式1  自动脱壳过滤处理请求结果，判断结果是否成功
    var loginResult = MutableLiveData<ResultState<UserInfo>>()

    //方式2  不用框架帮脱壳，判断结果是否成功
//    var loginResult2 = MutableLiveData<ResultState<ApiResponse<UserInfo>>>()

}