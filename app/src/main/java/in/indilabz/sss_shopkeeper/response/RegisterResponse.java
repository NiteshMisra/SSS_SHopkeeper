package in.indilabz.sss_shopkeeper.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("response_code")
    private Integer responseCode;

    @SerializedName("contact_number")
    private String contactNumber;

    @SerializedName("full-name")
    private String fullName;

    @SerializedName("category")
    private String category;

    @SerializedName("discount")
    private String discount;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("current_address")
    private String currentAddress;

    @SerializedName("response_msg")
    private String responseMessage;

    public RegisterResponse(Integer responseCode, String contactNumber, String fullName, String category, String discount, String password, String email, String currentAddress, String responseMessage) {
        this.responseCode = responseCode;
        this.contactNumber = contactNumber;
        this.fullName = fullName;
        this.category = category;
        this.discount = discount;
        this.password = password;
        this.email = email;
        this.currentAddress = currentAddress;
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCategory() {
        return category;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }
}
