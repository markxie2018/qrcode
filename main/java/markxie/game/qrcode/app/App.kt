package markxie.game.qrcode.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric
import markxie.game.qrcode.BuildConfig
import markxie.game.qrcode.R

class App : Application() {

    companion object{
         lateinit var app: App
         lateinit var fa: FirebaseAnalytics
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        fa = FirebaseAnalytics.getInstance(this)
        initCrashlytics()

        MobileAds.initialize(this, resources.getString(R.string.ad_app_id))
    }

    private fun initCrashlytics() {
        // Set up Crashlytics, disabled for debug builds
        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit)
    }
}