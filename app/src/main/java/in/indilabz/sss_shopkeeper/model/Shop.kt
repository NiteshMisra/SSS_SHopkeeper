package `in`.indilabz.sss_shopkeeper.model

import androidx.annotation.Nullable

data class Shop(
    var user_id: String,
    var username: String,
    var name: String,
    var user_img: String,
    var unique_id: String,
    var category: String,
    var shop_p_id: String,
    var discount: String,
    var remaining_discount: String?,
    var current_address: String,
    var login_status: Boolean
)
