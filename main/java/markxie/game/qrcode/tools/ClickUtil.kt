package markxie.game.qrcode.tools

object ClickUtil {
    private var lastClickTime: Long = 0
    private const val DIFF: Long = 1000
    private var lastButtonId = -1

    /**
     * 判斷兩次點擊的間隔，小於1000 則認為無效
     *
     * @return
     */
    val isFastDoubleClick: Boolean
        get() = isFastDoubleClick(-1, DIFF)

    /**
     * 判斷兩次點擊的間隔，小於diff 則認為無效
     *
     * @param diff
     * @return
     */
    @JvmOverloads
    fun isFastDoubleClick(buttonId: Int, diff: Long = DIFF): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            L.e("短時間觸發")
            return true
        }
        lastClickTime = time
        lastButtonId = buttonId
        return false
    }

}
