package com.medina.juanantonio.lyrify.car

import android.content.Intent
import androidx.car.app.CarAppService
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator
import com.medina.juanantonio.lyrify.car.viewmodels.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LyrifyCarService : CarAppService() {
    
    @Inject
    lateinit var carViewModel: CarViewModel
    
    @Inject
    lateinit var homeScreenFactory: LyrifyHomeScreen.Factory

    override fun onCreateSession(): Session {
        return LyrifyCarSession()
    }

    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

    inner class LyrifyCarSession : Session() {
        override fun onCreateScreen(intent: Intent): Screen {
            return homeScreenFactory.create(carContext)
        }
    }
}
