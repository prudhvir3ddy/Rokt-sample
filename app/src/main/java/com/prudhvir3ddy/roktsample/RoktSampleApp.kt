package com.prudhvir3ddy.roktsample

import android.app.Application
import android.util.Log
import com.mparticle.MParticle
import com.mparticle.MParticleOptions
import com.rokt.roktsdk.Rokt
import com.rokt.roktsdk.RoktEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoktSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initMparticle()

    }

    private fun initMparticle() {
        GlobalScope.launch {
            Rokt.globalEvents().collect { event ->
                if (event is RoktEvent.InitComplete)
                    Log.d("Rokt", "received init completed $event")
            }
        }
        val options: MParticleOptions = MParticleOptions.builder(this)
            .credentials(
                "",   // TODO (left this empty intentionally) The key provided by your Rokt account representative
                "" // TODO ( left this empty intentionally ) The secret provided by your Rokt account representative
            ).environment(MParticle.Environment.Development)
            .logLevel(MParticle.LogLevel.VERBOSE)
            .build()

        MParticle.start(options)
    }
}