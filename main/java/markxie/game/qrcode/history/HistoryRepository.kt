package markxie.game.qrcode.history

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import markxie.game.qrcode.room.ScanDao
import markxie.game.qrcode.room.ScanData

class HistoryRepository(private val scanDao: ScanDao) {

    val all: LiveData<List<ScanData>> = scanDao.getAll()

    @WorkerThread
    suspend fun insert(scanData: ScanData) {
        scanDao.insert(scanData)
    }
}