package markxie.game.qrcode.firebase

import android.os.Bundle
import markxie.game.qrcode.extension.convertToHour


class FaCollection {
    companion object {

        val SHARE = "share"
        val GO_TO_WEB = "go_to_web"
        val QRCODE_RESULT ="qrcode_result"
        val GO_TO_HISTORY_PAGE ="go_to_history_page"
        val ITEM_VIEW ="item_view"

        val TIME = "time"
        val Click = "click"


        @JvmStatic
        fun setClickFa(label: String, action: String = Click) {
            val param = Bundle()
            param.putString(action, label)
            param.putString(TIME, System.currentTimeMillis().convertToHour())
            FaHelper.setFAEvent(Click, param)
        }
    }
}