package com.prudhvir3ddy.roktsample

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mparticle.MParticle
import com.mparticle.MpRoktEventCallback
import com.mparticle.UnloadReasons
import com.mparticle.kits.RoktLayout
import com.mparticle.rokt.RoktConfig
import com.mparticle.rokt.RoktEmbeddedView
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MainScreen()
                }
            }
        }

        // uncomment below code and comment above to test embedded view via XML
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

//        val showAdButton = findViewById<Button>(R.id.showAdButton)
//        showAdButton.setOnClickListener {
//            showRoktEmbeddedView()
//        }
    }

    private fun showRoktEmbeddedView() {
        val callbacks = object : MpRoktEventCallback {
            override fun onLoad() {
                Log.d("mParticle", "onLoad")
            }

            override fun onUnload(reason: UnloadReasons) {
                Log.d("mParticle", "Rokt onUnload called: ${reason.name}")
            }

            override fun onShouldShowLoadingIndicator() {
                Log.d("mParticle", "onShouldShowLoadingIndicator")
            }

            override fun onShouldHideLoadingIndicator() {
                Log.d("mParticle", "onShouldHideLoadingIndicator")
            }
        }
        val attributes = hashMapOf(
            "Email" to "prudhvi.reddy@deliveroo.com",
            "Country" to "uk",
            "FirstName" to "Prudhvi",
            "LastName" to "Reddy",
            "BillingZipCode" to "EC1V9NR",
            "ConfirmationRef" to "12345",
        )
        val roktWidget = findViewById<RoktEmbeddedView>(R.id.roktEmbeddedView)
        val placeHolders = mapOf(Pair("RoktEmbedded1", WeakReference(roktWidget)))

        val roktConfig = RoktConfig.Builder().colorMode(RoktConfig.ColorMode.LIGHT).build()

        MParticle.getInstance()?.Rokt()?.selectPlacements(
            identifier = "helperstage",
            attributes = attributes,
            fontTypefaces = mapOf(),
            callbacks = callbacks,
            embeddedViews = placeHolders,
            config = roktConfig
        )
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

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    var sdkTriggered by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .padding(8.dp),
    ) {
        val attributes = mapOf(
            "email" to "prudhvi.reddy@deliveroo.com",
            "firstname" to "Prudhvi",
            "lastname" to "Reddy",
            "country" to "uk",
        )
        val callbacks = object : MpRoktEventCallback {
            override fun onLoad() = println("View loaded")
            override fun onUnload(reason: UnloadReasons) = println("View unloaded due to: $reason")
            override fun onShouldShowLoadingIndicator() = println("Show loading indicator")
            override fun onShouldHideLoadingIndicator() = println("Hide loading indicator")
        }

        Button(onClick = { sdkTriggered = true }) {
            Text(text = "Load Ad")
        }
        RoktLayout(
            sdkTriggered = sdkTriggered,
            identifier = "helperstage",
            attributes = attributes,
            location = "Location1",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            callbacks
        )
    }
}