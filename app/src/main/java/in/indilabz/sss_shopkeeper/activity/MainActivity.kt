package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.model.Discount
import `in`.indilabz.sss_shopkeeper.model.DiscountLog
import `in`.indilabz.sss_shopkeeper.model.Shop
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import `in`.indilabz.student_union.rest.APIHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    var scan:IntentIntegrator? = null

    var discount: TextView? = null
    var sumStudent: TextView? = null
    var sumAdmin: TextView? = null
    var cardView: CardView? = null
    var profile: LinearLayout? = null
    var submitAmount: String = ""
    lateinit var fab : FloatingActionButton

    val shop: Shop? = INDIPreferences.shop()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        cardView = findViewById(R.id.updateAmount)
        discount = findViewById(R.id.discount)
        sumStudent = findViewById(R.id.sumStudent)
        sumAdmin = findViewById(R.id.sumAdmin)
        profile = findViewById(R.id.profile)
        fab = findViewById(R.id.fab)

        scan = IntentIntegrator(this@MainActivity)

        fab.setOnClickListener {
            scan!!.initiateScan()
        }

        profile!!.setOnClickListener { view ->

            val integrator = IntentIntegrator(this@MainActivity)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.setPrompt("Scan")
            integrator.setCameraId(0)
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(false)
            integrator.initiateScan()

            //currentSale(this@MainActivity)
        }

        cardView!!.setOnClickListener{ view ->

            updateAmount(this)
        }

        findViewById<LinearLayout>(R.id.profile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        try{
            discount!!.text = "${shop!!.discount} %"
        }
        catch (e: Exception){
            e.printStackTrace()
        }

        log()
    }

    private fun update(amount: String){

        this.submitAmount = amount

        RetrofitInstance.getRetrofit(
            INDIMaster.api().updateAmount(
                shop!!.shop_p_id,
                amount
            ),
            result)
    }

    private fun sale(discount: Discount, amount: String){

        this.submitAmount = amount

        val disAmount = (discount.percent.toInt() * amount.toInt())/100

        val oriAmount = (shop!!.discount.toInt() * amount.toInt())/100

        RetrofitInstance.getRetrofit(
            INDIMaster.api().discountLog(

                discount.student_id,
                shop!!.shop_p_id,
                amount,
                disAmount,
                oriAmount
            ),
            discountRes)
    }

    private fun log(){

        RetrofitInstance.getRetrofit(
            INDIMaster.api().totalDiscount(
                shop!!.shop_p_id
            ),
            log)
    }

    private val result = { _: Int, bool:Boolean, value: String ->

        if(bool){

            if(value.contains("200")){

                var shop = shop!!
                shop.discount = submitAmount

                INDIPreferences.shop(shop)

                discount!!.text = "$submitAmount%"
            }
            else{
                Toaster.longt("Unable to update discount!")
            }
        }
    }

    private val discountRes = { _: Int, bool:Boolean, value: String ->

        if(bool){

            if(value.contains("200")){

                Toaster.longt(value)
            }
            else{
                Toaster.longt("Unable to update discount!")
            }
        }
    }

    private val log = { _: Int, bool:Boolean, value: String ->

        if(bool){

            val data = APIHelper.result(value)

            val datum = data.replace("[", "").replace("]","")

            val discountLog = INDIMaster.gson.fromJson(datum, DiscountLog::class.java)

            sumStudent!!.text = discountLog.discount
            sumAdmin!!.text = discountLog.total_discount
        }
    }

    private fun updateAmount(context: Context) {
        val textInputLayout = TextInputLayout(context)
        textInputLayout.setPadding(
            resources.getDimensionPixelOffset(R.dimen.fab_margin), // if you look at android alert_dialog.xml, you will see the message textview have margin 14dp and padding 5dp. This is the reason why I use 19 here
            0,
            resources.getDimensionPixelOffset(R.dimen.fab_margin),
            0
        )
        val input = EditText(context)
        textInputLayout.hint = "Amount"
        input.setInputType(InputType.TYPE_CLASS_NUMBER)
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(context)
            .setTitle("Discount")
            .setView(textInputLayout)
            .setMessage("Please enter the amount of discount you are offering!")
            .setPositiveButton("Submit") { dialog, _ ->
                // do some thing with input.text
                if(input.text.isNotEmpty()){

                    update(input.text.toString())
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
        input.setInputType(InputType.TYPE_CLASS_NUMBER)
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(context)
            .setTitle("Sale")
            .setView(textInputLayout)
            .setMessage("Please enter the current sale amount!")
            .setPositiveButton("Submit") { dialog, _ ->
                // do some thing with input.text
                if(input.text.isNotEmpty()){

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

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {


            try{

                val dis = INDIMaster.gson.fromJson(result!!.contents, Discount::class.java)

                if(dis.percent.toInt()>0){

                    currentSale(this@MainActivity, dis)
                }
                else{
                    Toaster.longt("No discount for this student!")
                }
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
