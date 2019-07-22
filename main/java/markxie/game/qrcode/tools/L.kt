package markxie.game.qrcode.tools

import android.util.Log

import markxie.game.qrcode.BuildConfig

/**
 * Created by 老謝 on 2016/10/6.
 */

class L {


    companion object {
        private const val APP_TAG = "aaa"

        //加上 @JvmStatic 在調用上就跟使用 java Static 變數一樣方便
        @JvmStatic
        fun e(message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(APP_TAG, message)
            }
        }

        @JvmStatic
        fun e(aaa: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(aaa, message)
            }
        }
    }

}
