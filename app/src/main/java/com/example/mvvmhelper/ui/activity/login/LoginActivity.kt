package com.example.mvvmhelper.ui.activity.login

import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvmhelper.databinding.ActivityLoginBinding
import com.example.mvvmhelper.viewmodel.request.LoginViewModel
import com.example.mvvmhelper.viewmodel.request.RequestLoginRegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import me.hgj.mvvmhelper.base.BaseDbActivity

class LoginActivity : BaseDbActivity<LoginViewModel, ActivityLoginBinding>() {

    private val requestLoginRegisterViewModel : RequestLoginRegisterViewModel by viewModels()

    private lateinit var mAuth: FirebaseAuth

    override fun initView(savedInstanceState: Bundle?) {
        mBind.viewmodel = mViewModel

        mAuth = FirebaseAuth.getInstance()
    }

}