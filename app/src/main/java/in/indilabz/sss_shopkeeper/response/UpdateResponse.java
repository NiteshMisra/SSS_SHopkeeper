package in.indilabz.sss_shopkeeper.response;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

public class UpdateResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @Nullable
    @SerializedName("result")
    private Integer result;

    public UpdateResponse(Boolean success, String error, String message, @Nullable Integer result) {
        this.success = success;
        this.error = error;
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public @Nullable Integer getResult() {
        return result;
    }
}
