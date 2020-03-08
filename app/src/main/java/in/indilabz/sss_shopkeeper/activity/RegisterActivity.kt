package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ActivityRegisterBinding
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import java.io.ByteArrayOutputStream
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var image : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_register
        )

        binding.uploadPhoto.setOnClickListener {
            selectImage()
        }

        binding.submit.setOnClickListener {

            if(binding.phone.editText!!.text.length == 10 &&

                binding.fullName.editText!!.text.isNotEmpty() &&

                binding.category.editText!!.text.isNotEmpty() &&

                binding.email.editText!!.text.isNotEmpty() &&

                binding.curAddress.editText!!.text.isNotEmpty() &&

                binding.password.editText!!.text.length>4)
            {

                executeRegister()
            }
            else{

                Toast.makeText(this, "Invalid form data!", Toast.LENGTH_LONG).show()
            }
        }

        binding.swipe.setOnRefreshListener { binding.swipe.isRefreshing = false }
    }

    private fun selectImage() {
        val intent = Intent();
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null){
            val path: Uri = data.data!!
            try {
                val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,path)!!
                val byteStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteStream)
                val imgBytes = byteStream.toByteArray()
                image = Base64.encodeToString(imgBytes, Base64.DEFAULT)
                Toast.makeText(this,"Image selected successfully",Toast.LENGTH_LONG).show()

            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    private fun executeRegister() {

        binding.swipe.isRefreshing = true

        RetrofitInstance.getRegisterRetrofit(
            INDIMaster.api().register(
                binding.phone.editText!!.text.toString(),
                binding.fullName.editText!!.text.toString(),
                binding.category.editText!!.text.toString(),
                binding.email.editText!!.text.toString(),
                binding.curAddress.editText!!.text.toString(),
                binding.password.editText!!.text.toString(),
                0
            ),
            register
        )
    }

    private val register = { _:Int, bool:Boolean, value:String ->

        binding.swipe.isRefreshing = false

        if(bool){

            Toast.makeText(this, "Registered successfully", Toast.LENGTH_LONG).show()

            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
        else{
            Toast.makeText(this, value, Toast.LENGTH_LONG).show()
        }
    }
}
