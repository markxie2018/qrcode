package markxie.game.qrcode.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.history_activity.*
import markxie.game.qrcode.R
import markxie.game.qrcode.adapter.HistoryAdapter
import markxie.game.qrcode.adapter.MyClick
import markxie.game.qrcode.databinding.HistoryActivityBinding
import markxie.game.qrcode.extension.inflateLayout
import markxie.game.qrcode.extension.setBlinkAnim
import markxie.game.qrcode.extension.share
import markxie.game.qrcode.firebase.FaCollection
import markxie.game.qrcode.room.ScanData
import markxie.game.qrcode.tools.ClickUtil
import markxie.game.qrcode.tools.L


class HistoryActivity : AppCompatActivity() {

    private lateinit var viewBinding: HistoryActivityBinding
    private lateinit var viewModel: HistoryViewModel

    private var bottomSheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.setContentView(this, R.layout.history_activity)
        viewBinding.lifecycleOwner = this

        viewModel = ViewModelProviders
            .of(this)
            .get(HistoryViewModel::class.java)

        initView()
        initLiveData()
        initListener()
        initAd()
    }


    private fun initView() {
        initRV(rv_qrCodeHistory)
    }


    private fun initRV(recyclerView: RecyclerView) {
        rv_qrCodeHistory.adapter = HistoryAdapter(object : MyClick {
            override fun onClick(scanData: ScanData) {
                if (ClickUtil.isFastDoubleClick) {
                    return
                }

                FaCollection.setClickFa(FaCollection.ITEM_VIEW)

                // using BottomSheetDialog
                val dialogView = inflateLayout(R.layout.bottom_sheet)
                val goToWeb: Button = dialogView.findViewById(R.id.b_goToWeb)
                val share: Button = dialogView.findViewById(R.id.b_share)


                if (scanData.data.startsWith("http")
                    || scanData.data.startsWith("https")
                ) {

                    goToWeb.visibility = View.VISIBLE
                    goToWeb.setOnClickListener {

                        Intent(Intent.ACTION_VIEW, Uri.parse(scanData.data))
                            .run { startActivity(this) }

                        bottomSheetDialog?.dismiss()
                        FaCollection.setClickFa(FaCollection.GO_TO_WEB, FaCollection.ITEM_VIEW)
                    }
                }


                share.setOnClickListener {
                    share(scanData.data, "Share")
                    bottomSheetDialog?.dismiss()
                    FaCollection.setClickFa(FaCollection.SHARE, FaCollection.ITEM_VIEW)
                }

                bottomSheetDialog = BottomSheetDialog(this@HistoryActivity)
                bottomSheetDialog?.run {
                    setContentView(dialogView)
                    show()
                }
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initLiveData() {

        viewModel.all.observe(this, Observer { data ->

            data?.let {

                when (data.size) {
                    0 -> {
                        iv_listNoData.visibility = View.VISIBLE
                        iv_listNoData.setBlinkAnim()

                        rv_qrCodeHistory.visibility = View.GONE
                    }
                    else -> {
                        iv_listNoData.visibility = View.GONE
                        iv_listNoData.clearAnimation()

                        rv_qrCodeHistory.visibility = View.VISIBLE
                        (rv_qrCodeHistory.adapter as HistoryAdapter).updateList(it)
                    }
                }
            }
        })
    }

    private fun initListener() {
        iv_listNoData.setOnClickListener { finish() }
    }

    private fun initAd() {
        ad_View.loadAd(AdRequest.Builder().build())
    }
}