package com.developer.revolut.rx

import com.developer.revolut.app.util.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

class TrampolineSchedulerProvider : SchedulersProvider() {
    override fun computation() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
}

class TestSchedulerProvider(private val scheduler: TestScheduler) : SchedulersProvider() {
    override fun computation() = scheduler
    override fun ui() = scheduler
    override fun io() = scheduler
}