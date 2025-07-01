package com.sargis.khlopuzyan.data

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.sargis.khlopuzyan.data.di.testDataModule
import com.sargis.khlopuzyan.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin

class TestNoteApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            )
            modules(testDataModule + domainModule)
        }
    }
}