package markxie.game.qrcode.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScanData(
    @ColumnInfo(name = "scan_content")
    var data: String,
    @ColumnInfo(name = "scan_day")
    var date: String,
    var title: String = "",
    var image: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)