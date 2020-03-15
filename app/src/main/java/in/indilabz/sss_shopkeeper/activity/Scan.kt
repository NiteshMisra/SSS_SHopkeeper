package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.model.Discount
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.json.JSONObject

class Scan : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var zXingScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zXingScannerView = ZXingScannerView(this)
        setContentView(zXingScannerView)
    }

    override fun handleResult(p0: Result?) {

        val intent = Intent()
        val dis = JSONObject(p0!!.text)
        val details = Discount(dis.get("unique_id").toString(),dis.get("student_id").toString(),
            dis.get("shop_id").toString(),dis.get("discount").toString(),
                    dis.get("allowed_discount").toString())

        intent.putExtra("GSON", Gson().toJson(details))
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        zXingScannerView.setResultHandler(this)
        zXingScannerView.startCamera()
    }

}
