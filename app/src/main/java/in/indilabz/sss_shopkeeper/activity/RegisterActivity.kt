package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ActivityRegisterBinding
import `in`.indilabz.sss_shopkeeper.model.Register
import `in`.indilabz.sss_shopkeeper.response.CategoryResponse
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import dmax.dialog.SpotsDialog

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {
        if (parent!!.adapter == genderAdapter){
            genderSelected = false
        }else if (parent.adapter == categoryAdapter){
            categorySelected = false
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (parent!!.adapter == genderAdapter){

            if (position == 0) {
                genderSelected = false
            } else {
                gender = parent.getItemAtPosition(position).toString()
                genderSelected = true
            }

        }else if(parent.adapter == categoryAdapter){
            if (position == 0) {
                categorySelected = false
            } else {
                categoryPosition = position
                categorySelected = true
            }
        }
    }

    private lateinit var binding: ActivityRegisterBinding
    private var serverOtp: String? = null
    private var categorySelected: Boolean = false
    private var categoryPosition : Int = 0
    private var genderSelected: Boolean = false
    private lateinit var gender: String
    private lateinit var genderAdapter : ArrayAdapter<CharSequence>
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_register
        )

        dialog = SpotsDialog.Builder().setContext(this).build()

        genderAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.genderArray,
            android.R.layout.simple_spinner_item
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.gender.adapter = genderAdapter
        binding.gender.onItemSelectedListener = this

        RetrofitInstance.getCategoryRetrofit(
            INDIMaster.api().getCategory(),
            category
        )

        dialog.setCanceledOnTouchOutside(false)
        dialog.setMessage("Please wait...")
        dialog.show()

        binding.submit.setOnClickListener {

            if (binding.ownerName.editText!!.text.isEmpty()){
                Toaster.longt("Enter Shop's Owner name")
                return@setOnClickListener
            }

            if (binding.phone.editText!!.text.length != 10) {
                Toaster.longt("Enter valid Mobile No.")
                return@setOnClickListener
            }

            if (binding.password.editText!!.text.length <= 4) {
                Toaster.longt("Password must be greater than 4 digit")
                return@setOnClickListener
            }
            if (!categorySelected) {
                Toaster.longt("Select Shop category")
                return@setOnClickListener
            }

            if (binding.curAddress.editText!!.text.isEmpty()){
                Toaster.longt("Enter your current address")
                return@setOnClickListener
            }

            if (binding.perAddress.editText!!.text.isEmpty()){
                Toaster.longt("Enter your permanent address")
                return@setOnClickListener
            }

            if (binding.email.editText!!.text.isEmpty()){
                Toaster.longt("Enter your email")
                return@setOnClickListener
            }

            if (binding.pinCode.editText!!.text.toString().isEmpty()){
                Toaster.longt("Enter the pincode")
                return@setOnClickListener
            }

            if (binding.fullName.editText!!.text.isEmpty() ){
                Toaster.longt("Enter the shop's full name")
                return@setOnClickListener
            }

            if (binding.password.editText!!.text.toString() != binding.confirmPassword.editText!!.text.toString()){
                Toaster.longt("Password not matching")
                return@setOnClickListener
            }

            executeRegister()
        }
    }

    private fun executeRegister() {

        dialog.setTitle("Please wait...")
        dialog.setMessage("Sending OTP...")
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        RetrofitInstance.getOTPRetrofit(
            INDIMaster.api().sendOtp(
                binding.phone.editText!!.text.toString()
            ),
            otp
        )

    }

    private val category = { _: Int, bool: Boolean, value: CategoryResponse ->

        if (bool) {

            dialog.dismiss()
            val result = value.categoryResults
            if (value.success && result != null) {

                val list = ArrayList<String>()
                list.add("Select Category")
                for (item in result) {
                    list.add(item.title)
                }

                categoryAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    list
                )
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.category.adapter = categoryAdapter
                binding.category.onItemSelectedListener = this

            }
        }
    }

    private val otp = { _: Int, bool: Boolean, value: UpdateResponse ->

        if (bool && value.result != null) {

            serverOtp = value.result.toString()

            val register = Register(
                binding.phone.editText!!.text.toString(),
                binding.fullName.editText!!.text.toString(),
                categoryPosition.toString(),
                binding.email.editText!!.text.toString(),
                binding.curAddress.editText!!.text.toString(),
                binding.perAddress.editText!!.text.toString(),
                binding.password.editText!!.text.toString(),
                binding.pinCode.editText!!.text.toString(),
                gender,
                binding.ownerName.editText!!.text.toString()
            )

            dialog.dismiss()

            val intent = Intent(this, OTPActivity::class.java)
            intent.putExtra("GSON", Gson().toJson(register))
            intent.putExtra("Otp", serverOtp)
            startActivity(intent)

        } else {
            dialog.dismiss()
            Toast.makeText(this, value.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog.isShowing){
            dialog.dismiss()
        }
    }
}
