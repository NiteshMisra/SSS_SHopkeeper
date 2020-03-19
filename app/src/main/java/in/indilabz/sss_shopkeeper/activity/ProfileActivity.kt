package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ActivityProfileBinding
import `in`.indilabz.sss_shopkeeper.model.Shop
import `in`.indilabz.sss_shopkeeper.response.CategoryResponse
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import dmax.dialog.SpotsDialog
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != 0 && firstTime > 0) {
            updateCategory(position)
        } else {
            firstTime++
        }
    }

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dialog: AlertDialog
    private lateinit var shop: Shop
    private var firstTime: Int = 0

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

        binding.gender.setOnClickListener {
            selectGender()
        }

        binding.fullName.setOnClickListener {
            editProfile("Shop name", binding.fullName)
        }

        binding.phone.setOnClickListener {
            editProfile("Phone", binding.phone)
        }

        binding.address.setOnClickListener {
            editProfile("Address", binding.address)
        }

        binding.pinCode.setOnClickListener {
            editProfile("Pincode", binding.pinCode)
        }

        binding.ownerName.setOnClickListener {
            editProfile("Owner name", binding.ownerName)
        }

        binding.logout.setOnClickListener {
            INDIPreferences.session(false)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        RetrofitInstance.getCategoryRetrofit(
            INDIMaster.api().getCategory(),
            category
        )

    }

    private fun selectGender() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Select gender")
        val gender = arrayOf("Male", "Female")
        builder.setItems(gender)
        { _, which ->
            setGender(gender[which])
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setGender(value: String) {

        binding.gender.text = value
        updateGender()
    }

    private fun updateGender() {

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updateGender(
                shop.id.toString(),
                binding.gender.text.toString()
            )
        ) { _: Int, bool: Boolean, value: UpdateResponse ->

            dialog.dismiss()

            if (bool && value.success) {
                Toaster.longt("Profile updated successfully")
                val shop: Shop = INDIPreferences.shop()!!
                shop.gender = binding.gender.text.toString()
                INDIPreferences.shop(shop)

            } else {
                Toaster.longt("Failed to update profile")
            }
        }
    }

    private val category = { _: Int, bool: Boolean, value: CategoryResponse ->

        if (bool) {

            val result = value.categoryResults
            if (value.success && result != null) {

                val list = ArrayList<String>()
                list.add("Select Category")
                for (item in result) {
                    list.add(item.title)
                }

                val categoryAdapter = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_item,
                    list
                )

                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.category.adapter = categoryAdapter
                binding.category.onItemSelectedListener = this
                binding.category.setSelection(INDIPreferences.shop()!!.category.toInt())

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            val path: Uri = data.data!!
            try {

                val byteStream = ByteArrayOutputStream()
                dialog.show()

                MultipartUploadRequest(
                    this,
                    serverUrl = "http://3.19.184.22/student-union/index.php/api/v1/shop/update/${INDIPreferences.shop()!!.id}"
                )
                    .setMethod("POST")
                    .addFileToUpload(
                        filePath = path.toString(),
                        parameterName = "shop_image"
                    ).startUpload()

                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)!!
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
                binding.profileImage.scaleType = ImageView.ScaleType.FIT_XY
                binding.profileImage.setImageBitmap(bitmap)
                val shop: Shop = INDIPreferences.shop()!!
                shop.imageUrl = path.toString()
                INDIPreferences.shop(shop)

                dialog.dismiss()

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

    private fun define() {

        shop = INDIPreferences.shop()!!

        binding.uniqueId.text = shop.id.toString()
        binding.fullName.text = shop.name
        binding.phone.text = shop.phone
        binding.email.text = shop.email
        binding.address.text = shop.current_address
        binding.ownerName.text = shop.ownerName
        binding.gender.text = shop.gender
        binding.pinCode.text = shop.pinCode

        if (shop.imageUrl != "") {

            try {
                binding.profileImage.scaleType = ImageView.ScaleType.FIT_XY
                Glide.with(this)
                    .load("http://3.19.184.22/student-union/index.php/image/shop/${shop.imageUrl}/234")
                    .into(binding.profileImage)
            } catch (e: Exception) {
                Toaster.longt("Not found")
            }
        }
    }


    private fun editProfile(title: String, textView: TextView) {

        val taskEditText = EditText(this)

        if (title == "Phone" || title == "Pincode") {
            taskEditText.inputType = InputType.TYPE_CLASS_PHONE
        } else {
            taskEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        val dpi: Float = this.resources.displayMetrics.density
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Edit $title")
            .setView(taskEditText)
            .setPositiveButton(
                "Submit"
            ) { dialog1 , _ ->

                if (taskEditText.text.toString().isNotEmpty()) {
                    when (title) {
                        "Shop name" -> {
                            textView.text = taskEditText.text
                            updateName()
                        }
                        "Owner name" -> {
                            textView.text = taskEditText.text
                            updateOwnerName()
                        }
                        "Pincode" -> {
                            textView.text = taskEditText.text
                            updatePincode()
                        }
                        "Phone" -> {
                            textView.text = taskEditText.text
                            updatePhone()
                        }

                        "Address" -> {
                            textView.text = taskEditText.text
                            updateAddress()
                        }
                    }
                }else{
                    dialog1.dismiss()
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

    private fun updateOwnerName() {

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updateOwnerName(
                shop.id.toString(),
                binding.ownerName.text.toString()
            )
        ) { _: Int, bool: Boolean, value: UpdateResponse ->

            dialog.dismiss()

            if (bool && value.success) {
                Toaster.longt("Profile updated successfully")
                val shop: Shop = INDIPreferences.shop()!!
                shop.ownerName = binding.ownerName.text.toString()
                INDIPreferences.shop(shop)

            } else {
                Toaster.longt("Failed to update profile")
            }
        }
    }

    private fun updatePincode() {

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updatePinCode(
                shop.id.toString(),
                binding.pinCode.text.toString()
            )
        ) { _: Int, bool: Boolean, value: UpdateResponse ->

            dialog.dismiss()

            if (bool && value.success) {
                Toaster.longt("Profile updated successfully")
                val shop: Shop = INDIPreferences.shop()!!
                shop.pinCode = binding.pinCode.text.toString()
                INDIPreferences.shop(shop)

            } else {
                Toaster.longt("Failed to update profile")
            }
        }
    }

    private fun updateName() {

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updateName(
                shop.id.toString(),
                binding.fullName.text.toString()
            )
        ) { _: Int, bool: Boolean, value: UpdateResponse ->

            dialog.dismiss()

            if (bool && value.success) {
                Toaster.longt("Profile updated successfully")
                val shop: Shop = INDIPreferences.shop()!!
                shop.name = binding.fullName.text.toString()
                INDIPreferences.shop(shop)

            } else {
                Toaster.longt("Failed to update profile")
            }
        }
    }

    private fun updateCategory(category: Int) {

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updateCategory(
                shop.id.toString(),
                category
            )
        ) { _: Int, bool: Boolean, value: UpdateResponse ->

            dialog.dismiss()

            if (bool && value.success) {
                Toaster.longt("Profile updated successfully")
                val shop: Shop = INDIPreferences.shop()!!
                shop.category = category.toString()
                INDIPreferences.shop(shop)

            } else {
                Toaster.longt("Failed to update profile")
            }
        }
    }

    private fun updatePhone() {

        dialog.show()

        RetrofitInstance.getOTPRetrofit(
            INDIMaster.api().sendOtp(
                binding.phone.text.toString()
            ),
            otp
        )
    }

    private val otp = { _: Int, bool: Boolean, value: UpdateResponse ->

        if (bool && value.result != null) {

            val serverOtp = value.result.toString()
            dialog.dismiss()

            val intent = Intent(this, ProfileOTP::class.java)
            intent.putExtra("Mobile", binding.phone.text.toString())
            intent.putExtra("Otp", serverOtp)
            startActivity(intent)

        } else {
            dialog.dismiss()
            Toast.makeText(this, value.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateAddress() {

        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().updateAddress(
                shop.id.toString(),
                binding.address.text.toString()
            )
        ) { _: Int, bool: Boolean, value: UpdateResponse ->

            dialog.dismiss()

            if (bool && value.success) {
                Toaster.longt("Profile updated successfully")
                val shop: Shop = INDIPreferences.shop()!!
                shop.current_address = binding.address.text.toString()
                INDIPreferences.shop(shop)

            } else {
                Toaster.longt("Failed to update profile")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
