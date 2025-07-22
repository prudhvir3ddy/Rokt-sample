package com.prudhvir3ddy.roktsample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.mparticle.MParticle
import com.mparticle.rokt.RoktConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            delay(5000) // Wait time for SDK to get initialised
            showRoktOverlay()
        }

    }

    private fun showRoktOverlay() {
        val attributes = mapOf(
            "email" to "j.smith@example.com",
            "firstname" to "Jenny",
            "lastname" to "Smith",
            "billingzipcode" to "90210",
            "confirmationref" to "54321",
        )

        val roktConfig = RoktConfig.Builder().colorMode(RoktConfig.ColorMode.LIGHT).build()


        MParticle.getInstance()?.Rokt()?.selectPlacements(
            identifier = "RoktExperience",
            attributes = attributes,
            fontTypefaces = mapOf(),
            config = roktConfig,
            embeddedViews = null, // optional placeHolders (see Embedded Placements below)
            callbacks = null, // optional callbacks (define as appropriate, see below)
        )
    }
}