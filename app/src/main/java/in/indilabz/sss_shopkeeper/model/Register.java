package in.indilabz.sss_shopkeeper.model;

public class Register {

    private String phone;
    private String fullName;
    private String categoryText;
    private String email;
    private String currentAddress;
    private String perAddress;
    private String password;
    private String pincode;
    private String gender;
    private String ownerName;

    public Register(String phone, String fullName, String categoryText, String email, String currentAddress, String perAddress, String password, String pincode, String gender, String ownerName) {
        this.phone = phone;
        this.fullName = fullName;
        this.categoryText = categoryText;
        this.email = email;
        this.currentAddress = currentAddress;
        this.perAddress = perAddress;
        this.password = password;
        this.pincode = pincode;
        this.gender = gender;
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public String getPassword() {
        return password;
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
}
