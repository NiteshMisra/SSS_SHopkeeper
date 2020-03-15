package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.model.Discount
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var discount: TextView? = null
    private var sumStudent: TextView? = null
    private var sumAdmin: TextView? = null
    private var profile: LinearLayout? = null
    private var submitAmount: String = ""
    private lateinit var fab : FloatingActionButton
    private val SCAN_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        discount = findViewById(R.id.discount)
        sumStudent = findViewById(R.id.sumStudent)
        sumAdmin = findViewById(R.id.sumAdmin)
        profile = findViewById(R.id.profile)
        fab = findViewById(R.id.fab)


        fab.setOnClickListener {
            startActivityForResult(Intent(this,Scan::class.java),SCAN_REQUEST_CODE)
        }

        this.profile!!.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun sale(discount : Discount, amount: String){

        this.submitAmount = amount

        val discount2 = (amount.toInt() * discount.discount.toInt())/100
        val availedDiscount = (amount.toInt() * discount.allowed_discount.toInt())/100

        val sendAmount = Math.abs(discount2 - availedDiscount)

        RetrofitInstance.getAvailedRetrofit(
            INDIMaster.api().discountLog(
                discount.unique_id,
                discount.student_id,
                discount.shop_id,
                sendAmount.toString()
            ),
            discountRes)
    }

    private val discountRes = {_: Int,bool:Boolean, value: UpdateResponse ->

        if(bool){
            Toaster.longt("Updated")
        }else{
            Toaster.longt(value.error)
        }
    }

    private fun currentSale(context: Context, dis: Discount) {
        val textInputLayout = TextInputLayout(context)
        textInputLayout.setPadding(
            resources.getDimensionPixelOffset(R.dimen.fab_margin), // if you look at android alert_dialog.xml, you will see the message textview have margin 14dp and padding 5dp. This is the reason why I use 19 here
            0,
            resources.getDimensionPixelOffset(R.dimen.fab_margin),
            0
        )
        val input = EditText(context)
        textInputLayout.hint = "Sale"
        input.inputType = InputType.TYPE_CLASS_NUMBER
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(context)
            .setTitle("Sale")
            .setView(textInputLayout)
            .setMessage("Please enter the current sale amount!")
            .setPositiveButton("Submit") { dialog, _ ->
                // do some thing with input.text
                if(input.text.isNotEmpty() && input.text.toString().toInt() > 0){

                    sale(dis, input.text.toString())
                    dialog.cancel()
                }
                else{
                    Toaster.longt("Invalid amount!")
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()

        alert.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            try{

                val value: String  = data.getStringExtra("GSON")!!
                val dis : Discount = Gson().fromJson(value, Discount::class.java)
                currentSale(this@MainActivity, dis)
            }
            catch(e: Exception){
                Toaster.longt("Invalid student details!")
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
