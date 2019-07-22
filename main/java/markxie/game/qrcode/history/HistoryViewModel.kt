package markxie.game.qrcode.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import markxie.game.qrcode.room.AppDatabase
import markxie.game.qrcode.room.ScanData
import java.util.ArrayList

class HistoryViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: HistoryRepository
    val all: LiveData<List<ScanData>>

    init {
        val wordsDao = AppDatabase.getAppDataBase(app).scanDao()
        repository = HistoryRepository(wordsDao)
        all = repository.all
    }
}