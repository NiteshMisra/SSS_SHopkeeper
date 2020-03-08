package in.indilabz.sss_shopkeeper.response;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoginResponse {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("username")
    private String userName;

    @SerializedName("name")
    private String name;

    @SerializedName("user_img")
    private String userImage;

    @SerializedName("unique_id")
    private String uniqueId;

    @SerializedName("category")
    private String category;

    @SerializedName("shop_p_id")
    private String shopPId;

    @SerializedName("discount")
    private String discount;

    @Nullable
    @SerializedName("remaining_discount")
    private String remainingDiscount;

    @SerializedName("current_address")
    private String currentAddress;

    @SerializedName("login_status")
    private Boolean loginStatus;

    @SerializedName("error")
    private String error;

    public LoginResponse(String userId, String userName, String name, String userImage, String uniqueId, String category, String shopPId, String discount, @Nullable String remainingDiscount, String currentAddress, Boolean loginStatus, String error) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.userImage = userImage;
        this.uniqueId = uniqueId;
        this.category = category;
        this.shopPId = shopPId;
        this.discount = discount;
        this.remainingDiscount = remainingDiscount;
        this.currentAddress = currentAddress;
        this.loginStatus = loginStatus;
        this.error = error;
    }

    @NotNull
    public String toString(){
        return userId + " " + userName + " " + name + " " + userImage + " " + uniqueId + " " + category + " " + shopPId + " " + discount + " "
                + " " + remainingDiscount + " " + loginStatus + " ";
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getCategory() {
        return category;
    }

    public String getShopPId() {
        return shopPId;
    }

    public String getDiscount() {
        return discount;
    }

    @Nullable
    public String getRemainingDiscount() {
        return remainingDiscount;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public String getError() {
        return error;
    }
}
