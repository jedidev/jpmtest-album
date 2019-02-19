package com.tsheal.albums

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class MyApplication: Application() {
    companion object {
        private var context: WeakReference<Context>? = null

        fun getContext(): WeakReference<Context>? = context
    }

    override fun onCreate() {
        super.onCreate()
        MyApplication.context = WeakReference(applicationContext)
    }
}