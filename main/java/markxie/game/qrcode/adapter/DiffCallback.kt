package markxie.game.qrcode.adapter

import androidx.recyclerview.widget.DiffUtil
import markxie.game.qrcode.room.ScanData

class DiffCallback(private val oldList: List<ScanData>, private val newList: List<ScanData>) : DiffUtil.Callback() {


    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size


    //判斷是否相同 viewHolder，相同電梯向下，不相同直接換 viewHolder
    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            oldList[oldPos].data == newList[newPos].data


    //areItemsTheSame return true 調用
    //判斷是否相同內容，相同就不做事，不相同則更新該 view 內容，電梯向下
    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
        oldList[oldPos].data == newList[newPos].data


    //高效更新，不要求高效則可以不用實作，實作時資料太多最好放子線程，避免阻塞
    //areItemsTheSame() 返回 true 而 areContentsTheSame() 返回 false 時觸發
    //標記不同內容的部分進行部分更新
    //notice: Need to prevent oldPos oob exception
    override fun getChangePayload(oldPos: Int, newPos: Int): Any? {
        return super.getChangePayload(oldPos, newPos)
    }
}
