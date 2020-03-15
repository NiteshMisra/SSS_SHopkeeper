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

    @SerializedName("current_address")
    private String currentAddress;

    @SerializedName("permanent_address")
    private String permanentAddress;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public RegisterResult(Integer id, String name, String email, String phone, String password, String category, String currentAddress, String permanentAddress, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.category = category;
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
