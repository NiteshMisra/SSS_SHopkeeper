package in.indilabz.sss_shopkeeper.response;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;
import in.indilabz.sss_shopkeeper.model.LoginResult;

public class LoginResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String error;

    @Nullable
    @SerializedName("result")
    private LoginResult loginResult;

    public LoginResponse(Boolean success, String message, String error, @Nullable LoginResult loginResult) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.loginResult = loginResult;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    @Nullable
    public LoginResult getLoginResult() {
        return loginResult;
    }
}
