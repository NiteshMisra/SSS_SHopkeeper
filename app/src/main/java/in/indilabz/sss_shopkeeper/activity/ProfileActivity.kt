package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ActivityProfileBinding
import `in`.indilabz.sss_shopkeeper.model.ShopDetail
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import `in`.indilabz.student_union.rest.APIHelper
import android.app.AlertDialog
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import dmax.dialog.SpotsDialog

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dialog: AlertDialog
    private lateinit var shopDetail: ShopDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        dialog = SpotsDialog.Builder().setContext(this).build()

        getData()

        binding.fullName.setOnClickListener{
            editProfile("full name", binding.fullName)
        }

        binding.phone.setOnClickListener{
            editProfile("phone", binding.phone)
        }

        binding.username.setOnClickListener{
            editProfile("email", binding.username)
        }

        binding.category.setOnClickListener{
            editProfile("category", binding.category)
        }

        binding.address.setOnClickListener{
            editProfile("address", binding.address)
        }

    }

    private fun define(shop: ShopDetail){

        this.shopDetail = shop

        binding.uniqueId.text = shop.unique_id
        binding.fullName.text = shop.name
        binding.phone.text = shop.contact_number
        binding.username.text = shop.username
        binding.category.text = shop.category
        binding.address.text = shop.current_address
    }

    private fun getData(){

        dialog.show()

        RetrofitInstance.getRetrofit(
            INDIMaster.api().profile(
                INDIPreferences.shop()!!.shop_p_id
            )
            , result)
    }

    private fun editProfile(title: String, textView: TextView) {

        val taskEditText = EditText(this)

        taskEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        val dpi: Float = this.getResources().getDisplayMetrics().density
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Edit $title")
            .setView(taskEditText)
            .setPositiveButton(
                "Submit"
            ) { dialog, which ->
                textView.text = taskEditText.text
                update()
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

    private fun update(){

        dialog.show()

        RetrofitInstance.getRetrofit(
            INDIMaster.api().update(
                shopDetail.id,
                binding.fullName.text.toString(),
                binding.phone.text.toString(),
                binding.category.text.toString(),
                binding.address.text.toString()
            )
            , updateResult)
    }


    private val updateResult = { _:Int, bool:Boolean, value:String ->

        dialog.dismiss()

        if(bool){
            Toaster.longt("Profile updated successfully")
            getData()
        }
        else{
            Toaster.longt("Failed to update profile")
        }
    }


    private val result = {_:Int, bool:Boolean, value:String ->

        dialog.dismiss()

       if(bool){

           val result = APIHelper.result(value)

           val datumList:Array<ShopDetail> = INDIMaster.gson.fromJson(
               result,
               Array<ShopDetail>::class.java)

           define(datumList[0])
        }
        else{
            Toaster.longt("Error while getting data")
        }
    }
}
