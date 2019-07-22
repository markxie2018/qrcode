package markxie.game.qrcode.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import markxie.game.qrcode.R
import markxie.game.qrcode.extension.inflateLayout
import markxie.game.qrcode.room.ScanData
import markxie.game.qrcode.viewholder.HistoryViewHolder

class HistoryAdapter(private val myClick: MyClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryViewHolder(parent.context.inflateLayout(R.layout.item_history))
    }

    private var scanDatas = mutableListOf<ScanData>()

    override fun getItemCount(): Int = scanDatas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HistoryViewHolder) {
            holder.bindResult(scanDatas[position])
            holder.itemView.setOnClickListener { myClick.onClick(scanDatas[position]) }
        }

    }


    fun updateList(_scanDatas: List<ScanData>) {
        val diffCallback = DiffCallback(this.scanDatas, _scanDatas)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.scanDatas.clear()
        this.scanDatas.addAll(_scanDatas)
        diffResult.dispatchUpdatesTo(this)
    }

}

interface MyClick {
    fun onClick(scanData: ScanData)
}