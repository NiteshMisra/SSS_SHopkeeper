package in.indilabz.sss_shopkeeper.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResult {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("password")
    private String password;

    @SerializedName("category")
    private String category;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("gender")
    private String gender;

    @SerializedName("owner_name")
    private String ownerName;

    @SerializedName("current_address")
    private String currentAddress;

    @SerializedName("permanent_address")
    private String permanentAddress;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public RegisterResult(Integer id, String name, String email, String phone, String password, String category, String pincode, String gender, String ownerName, String currentAddress, String permanentAddress, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.category = category;
        this.pincode = pincode;
        this.gender = gender;
        this.ownerName = ownerName;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getCategory() {
        return category;
    }

    public String getPincode() {
        return pincode;
    }

    public String getGender() {
        return gender;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
