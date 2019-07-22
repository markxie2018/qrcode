package markxie.game.qrcode.main

import androidx.annotation.WorkerThread
import markxie.game.qrcode.room.ScanDao
import markxie.game.qrcode.room.ScanData

class MainRepository(private val scanDao: ScanDao) {


    @WorkerThread
    suspend fun insert(scanData: ScanData) {
        scanDao.insert(scanData)
    }

    @WorkerThread
    suspend fun getByContent(s: String): ScanData? {
        return scanDao.getByContent(s)
    }

}