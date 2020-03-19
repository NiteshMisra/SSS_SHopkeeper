package `in`.indilabz.sss_shopkeeper.model

data class Shop(
    var id: Int,
    var name : String,
    var email : String,
    var phone : String,
    var password : String,
    var category: String,
    var current_address: String,
    var imageUrl : String,
    var gender : String,
    var pinCode : String,
    var ownerName : String
)
