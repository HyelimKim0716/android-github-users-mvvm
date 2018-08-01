package com.githubapi.search.searchgithubusers.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle

abstract class BaseDataBindingActivity<BINDING: ViewDataBinding>: BaseActivity() {
    protected lateinit var binding: BINDING
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
    }

}