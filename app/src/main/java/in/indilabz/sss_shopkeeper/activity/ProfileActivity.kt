package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ActivityProfileBinding
import `in`.indilabz.sss_shopkeeper.model.Shop
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Base64
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dmax.dialog.SpotsDialog
import java.io.ByteArrayOutputStream
import java.lang.Exception

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dialog: AlertDialog
    private lateinit var shop: Shop
    private lateinit var image: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        dialog = SpotsDialog.Builder().setContext(this).build()

        define()

        binding.changeImage.setOnClickListener {
            selectImage()
        }

        binding.fullName.setOnClickListener{
            editProfile("Full name", binding.fullName)
        }

        binding.phone.setOnClickListener{
            editProfile("Phone", binding.phone)
        }

        binding.category.setOnClickListener{
            //editProfile("Category", binding.category)
        }

        binding.address.setOnClickListener{
            editProfile("Address", binding.address)
        }

        binding.logout.setOnClickListener {
            INDIPreferences.session(false)
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            val path: Uri = data.data!!
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)!!
                val byteStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
                val imgBytes = byteStream.toByteArray()
                image = Base64.encodeToString(imgBytes, Base64.DEFAULT)
                binding.profileImage.scaleType = ImageView.ScaleType.FIT_XY
                binding.profileImage.setImageBitmap(bitmap)
                val shop : Shop = INDIPreferences.shop()!!
                shop.imageUrl = image
                INDIPreferences.shop(shop)

                dialog.show()

                RetrofitInstance.updateRetrofit(
                    INDIMaster.api().updateImage(
                        shop.id.toString(),
                        image
                    )
                    , updateResult)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 10)
    }

    private fun define(){

        shop = INDIPreferences.shop()!!

        binding.uniqueId.text = shop.id.toString()
        binding.fullName.text = shop.name
        binding.phone.text = shop.phone
        binding.email.text = shop.email
        binding.category.text = shop.category
        binding.address.text = shop.current_address

        if (shop.imageUrl != "") {

            val decodeBytes = Base64.decode(shop.imageUrl, 0)
            val bitmap = BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.size)
            binding.profileImage.scaleType = ImageView.ScaleType.FIT_XY
            binding.profileImage.setImageBitmap(bitmap)
        }
    }


    private fun editProfile(title: String, textView: TextView) {

        val taskEditText = EditText(this)

        taskEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        val dpi: Float = this.resources.displayMetrics.density
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Edit $title")
            .setView(taskEditText)
            .setPositiveButton(
                "Submit"
            ) { _, _ ->

                when (title) {
                    "Full name" -> {
                        textView.text = taskEditText.text
                        updateName()
                    }
                    "Phone" -> {
                        textView.text = taskEditText.text
                        updatePhone()
                    }
                    "Category" -> {
                        Toaster.longt("to do")
                    }

                    "Address" -> {
                        textView.text = taskEditText.text
                        updateAddress()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.setView(
            taskEditText, (19.toFloat() * dpi).toInt(),
            (5.toFloat() * dpi).toInt(),
            (14.toFloat() * dpi).toInt(),
            (5.toFloat() * dpi).toInt()
        )
        dialog.show()
    }

    private fun updateName(){

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updateName(
                shop.id.toString(),
                binding.fullName.text.toString()
            )
            , updateResult)
    }

    private fun updatePhone(){

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updatePhone(
                shop.id.toString(),
                binding.phone.text.toString()
            )
            , updateResult)
    }

    private fun updateAddress(){

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updateAddress(
                shop.id.toString(),
                binding.address.text.toString()
            )
            , updateResult)
    }

    private val updateResult = { _:Int, bool:Boolean, value:UpdateResponse ->

        dialog.dismiss()

        if(bool && value.success){
            Toaster.longt("Profile updated successfully")
        }
        else{
            Toaster.longt("Failed to update profile")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
