package com.sargis.khlopuzyan.note

import android.app.Application
import com.sargis.khlopuzyan.data.di.dataModule
import com.sargis.khlopuzyan.domain.di.domainModule
import com.sargis.khlopuzyan.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin

class NoteApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NoteApp)
            modules(dataModule + domainModule + presentationModule)
        }
    }

}