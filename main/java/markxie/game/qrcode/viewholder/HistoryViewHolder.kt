package markxie.game.qrcode.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import markxie.game.qrcode.R
import markxie.game.qrcode.extension.convertLongToTime
import markxie.game.qrcode.room.ScanData

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var content: TextView = view.findViewById(R.id.tv_content)
    private var date: TextView = view.findViewById(R.id.tv_date)

    fun bindResult(data: ScanData) {
        content.text = data.data
        date.text = data.date.toLongOrNull()?.convertLongToTime() ?: ""
    }
}