package in.indilabz.sss_shopkeeper.response;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

import in.indilabz.sss_shopkeeper.model.RegisterResult;

public class RegisterResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("error")
    private String error;

    @Nullable
    @SerializedName("result")
    private RegisterResult result;

    public RegisterResponse(Boolean success, String error, @Nullable RegisterResult result) {
        this.success = success;
        this.error = error;
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public @Nullable RegisterResult getResult() {
        return result;
    }
}
