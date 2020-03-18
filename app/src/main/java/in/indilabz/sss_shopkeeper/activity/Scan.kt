package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.model.Discount
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.json.JSONObject

class Scan : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var zXingScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            zXingScannerView = ZXingScannerView(this)
            setContentView(zXingScannerView)
        }catch (e : Exception){
            Log.e("Scan Error :",e.message.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    override fun handleResult(p0: Result?) {

        try{
            val intent = Intent()
            val dis = JSONObject(p0!!.text)
            val details = Discount(dis.get("unique_id").toString(),dis.get("student_id").toString(),
                dis.get("shop_id").toString(),dis.get("discount").toString(),
                dis.get("allowed_discount").toString())

            intent.putExtra("GSON", Gson().toJson(details))
            setResult(Activity.RESULT_OK,intent)
            finish()
        }catch (e : Exception){
            Toaster.longt("Invalid QR Code")
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (!shouldShowRequestPermissionRationale(permissions[0])){
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setMessage("We require camera permission to scan the QR Code. Please allow it from app's Setting -> Permissions.")
                    alertDialog.setPositiveButton("Ok"
                    ) { dialog, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri : Uri = Uri.fromParts("package",packageName,null)
                        intent.data = uri
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    alertDialog.setNegativeButton("No"){
                        dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    alertDialog.show()
                }else {
                    finish()
                }
            }else{
                zXingScannerView.setResultHandler(this)
                zXingScannerView.startCamera()
            }
        }
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
