package in.indilabz.sss_shopkeeper.model;

public class Register {

    private String phone;
    private String fullName;
    private String categoryText;
    private String email;
    private String currentAddress;
    private String perAddress;
    private String password;

    public Register(String phone, String fullName, String categoryText, String email, String currentAddress, String perAddress, String password) {
        this.phone = phone;
        this.fullName = fullName;
        this.categoryText = categoryText;
        this.email = email;
        this.currentAddress = currentAddress;
        this.perAddress = perAddress;
        this.password = password;
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
}
