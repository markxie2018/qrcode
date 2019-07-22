package markxie.game.qrcode.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ScanData)

    @Update
    suspend fun update(data: ScanData)

    @Delete
    suspend fun delete(data: ScanData)

    @Query("SELECT * FROM ScanData WHERE scan_content LIKE :scanContent LIMIT 1")
    suspend fun getByContent(scanContent: String): ScanData?

    @Query("SELECT * FROM ScanData ORDER BY scan_day DESC")
    fun getAll(): LiveData<List<ScanData>>
}