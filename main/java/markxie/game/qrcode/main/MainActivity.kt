package markxie.game.qrcode.main


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import markxie.game.qrcode.databinding.ActivityMainBinding
import java.util.Arrays.asList
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import markxie.game.qrcode.R
import markxie.game.qrcode.extension.*
import markxie.game.qrcode.firebase.FaCollection
import markxie.game.qrcode.tools.ClickUtil
import markxie.game.qrcode.history.HistoryActivity


class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var bottomSheetDialog: BottomSheetDialog? = null

    private val CAMERA_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.lifecycleOwner = this

        viewModel = ViewModelProviders
            .of(this)
            .get(MainViewModel::class.java)

        initView()
        initListener()
        initLiveData()
        initAd()

    }

    private fun initQrCode() {

        val formats = asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)

        viewBinding.bdvScanner.run {
            setStatusText("")
            barcodeView.decoderFactory = DefaultDecoderFactory(formats)
            initializeFromIntent(intent)
            decodeContinuous(callback)
        }
    }

    private fun initView() {
        viewBinding.tvResult.movementMethod = ScrollingMovementMethod()

        initQrCode()

    }

    private fun initListener() {

        viewBinding.tvResult.setOnClickListener {
            if (ClickUtil.isFastDoubleClick) {
                return@setOnClickListener
            }

            FaCollection.setClickFa(FaCollection.QRCODE_RESULT)

            // using BottomSheetDialog
            val dialogView = inflateLayout(R.layout.bottom_sheet)
            val goToWeb: Button = dialogView.findViewById(R.id.b_goToWeb)
            val share: Button = dialogView.findViewById(R.id.b_share)


            if (viewModel.lastText.value!!.startsWith("http") ||
                viewModel.lastText.value!!.startsWith("https")
            ) {

                goToWeb.visibility = View.VISIBLE
                goToWeb.setOnClickListener {
                    Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.lastText.value))
                        .run { startActivity(this) }
                    bottomSheetDialog?.dismiss()
                    FaCollection.setClickFa(FaCollection.GO_TO_WEB, FaCollection.QRCODE_RESULT)
                }
            }


            share.setOnClickListener {
                viewModel.lastText.value?.run {
                    share(this, "Share")
                }
                bottomSheetDialog?.dismiss()
                FaCollection.setClickFa(FaCollection.SHARE, FaCollection.QRCODE_RESULT)
            }


            bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog?.run {
                setContentView(dialogView)
                show()
            }
        }

        viewBinding.bHistory.setOnClickListener {
            startActivity<HistoryActivity>()
            FaCollection.setClickFa(FaCollection.GO_TO_HISTORY_PAGE)
        }
    }

    private fun initLiveData() {
        viewModel.lastText.observe(this, Observer {

            it?.run {
                when (it) {
                    "" -> {
                        viewBinding.tvResult.visibility = View.GONE
                        viewBinding.ivNoData.visibility = View.VISIBLE
                        viewBinding.ivNoData.setBlinkAnim()
                    }
                    else -> {
                        viewBinding.ivNoData.visibility = View.GONE
                        viewBinding.ivNoData.clearAnimation()

                        viewBinding.tvResult.visibility = View.VISIBLE
                        //每次掃描後都將 scroll 滑至頂部
                        viewBinding.tvResult.scrollTo(0, 0)
                        viewBinding.tvResult.text = it
                        viewModel.insert(it)

                        //Added preview of scanned barcode
                        //viewBinding.ivQrcode.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW))
                    }
                }
            }
        })
    }


    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == viewModel.lastText.value) {
                // Prevent duplicate scans
                return
            }
            viewModel.lastText.value = result.text
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    override fun onResume() {
        super.onResume()
        viewBinding.adView.resume()
        viewBinding.bdvScanner.resume()
    }

    override fun onPause() {
        viewBinding.adView.pause()
        viewBinding.bdvScanner.pause()
        super.onPause()
    }

    override fun onDestroy() {
        viewBinding.adView.destroy()
        super.onDestroy()
    }

    fun pause(view: View) {
        viewBinding.bdvScanner.pause()
    }

    fun resume(view: View) {
        viewBinding.bdvScanner.resume()
    }

    fun triggerScan(view: View) {
        viewBinding.bdvScanner.decodeSingle(callback)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return viewBinding.bdvScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish()
            }
        }
    }

    private fun initAd() {
        adView.loadAd(AdRequest.Builder().build())
    }

}
