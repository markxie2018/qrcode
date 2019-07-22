package markxie.game.qrcode.firebase

import android.os.Bundle
import markxie.game.qrcode.BuildConfig
import markxie.game.qrcode.app.App

class FaHelper {
    companion object {

        @JvmStatic
        fun setFAEvent(key: String, param: Bundle) {
            if (BuildConfig.DEBUG) {
                return
            }
            App.fa.logEvent(key, param)
        }
    }
}