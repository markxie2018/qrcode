package markxie.game.qrcode.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import markxie.game.qrcode.room.AppDatabase
import markxie.game.qrcode.room.ScanData

class MainViewModel(app: Application) : AndroidViewModel(app) {

    val lastText = MutableLiveData<String>("")


    private val repository: MainRepository

    init {
        val scanDao = AppDatabase.getAppDataBase(app).scanDao()
        repository = MainRepository(scanDao)
    }

    fun insert(content: String) = viewModelScope.launch(Dispatchers.IO) {
        val data = repository.getByContent(content)

        if (data != null) {
            data.date = System.currentTimeMillis().toString()
            repository.insert(data)
        } else {
            repository.insert(ScanData(content, System.currentTimeMillis().toString()))
        }
    }

}