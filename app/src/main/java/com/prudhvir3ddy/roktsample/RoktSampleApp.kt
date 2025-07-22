package com.prudhvir3ddy.roktsample

import android.app.Application
import com.mparticle.MParticle
import com.mparticle.MParticleOptions

class RoktSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initMparticle()

    }

    private fun initMparticle() {
        val options: MParticleOptions = MParticleOptions.builder(this)
            .credentials(
                "",   // TODO (left this empty intentionally) The key provided by your Rokt account representative
                "" // TODO ( left this empty intentionally ) The secret provided by your Rokt account representative
            ).environment(MParticle.Environment.Development)  // Specify the data environment with environment:
            // Set it to .development if you are still testing your integration.
            // Set it to .production if your integration is ready for production data.
            // The default is .autoDetect which attempts to detect the environment automatically
            .build()

        MParticle.start(options)
    }
}