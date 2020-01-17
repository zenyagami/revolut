package com.developer.revolut.app.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class SchedulersProvider @Inject internal constructor() {
    open fun io(): Scheduler = Schedulers.io()
    open fun ui(): Scheduler = AndroidSchedulers.mainThread()
}