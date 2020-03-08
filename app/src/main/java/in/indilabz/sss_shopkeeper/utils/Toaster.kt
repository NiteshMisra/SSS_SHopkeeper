package `in`.indilabz.sss_shopkeeper.utils

import `in`.indilabz.sss_shopkeeper.INDIMaster
import android.widget.Toast

object Toaster {

    fun longt(value: String){

        Toast.makeText(INDIMaster.applicationContext(),
            value, Toast.LENGTH_LONG).show()
    }

    fun short(value: String){

        Toast.makeText(INDIMaster.applicationContext(),
            value, Toast.LENGTH_SHORT).show()
    }
}