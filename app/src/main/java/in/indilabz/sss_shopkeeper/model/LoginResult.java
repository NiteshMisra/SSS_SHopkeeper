package in.indilabz.sss_shopkeeper.model;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("id")
    private Integer id;

    @SerializedName("unique_id")
    private String uniqueId;

    @SerializedName("name")
    private String name;

    @SerializedName("owner_name")
    private String ownerName;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("gender")
    private String gender;

    @SerializedName("password")
    private String password;

    @SerializedName("category")
    private Integer category;

    @SerializedName("shop_image")
    private String shopImage;

    @SerializedName("current_address")
    private String currentAddress;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("permanent_address")
    private String permanentAddress;

    @SerializedName("wallet")
    private Float wallet;

    @SerializedName("verified")
    private Integer verified;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public LoginResult(Integer id, String uniqueId, String name, String ownerName, String email, String phone, String gender, String password, Integer category, String shopImage, String currentAddress, String pincode, String permanentAddress, Float wallet, Integer verified, String createdAt, String updatedAt) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.ownerName = ownerName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.category = category;
        this.shopImage = shopImage;
        this.currentAddress = currentAddress;
        this.pincode = pincode;
        this.permanentAddress = permanentAddress;
        this.wallet = wallet;
        this.verified = verified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public Integer getCategory() {
        return category;
    }

    public String getShopImage() {
        return shopImage;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public String getPincode() {
        return pincode;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public Float getWallet() {
        return wallet;
    }

    public Integer getVerified() {
        return verified;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
