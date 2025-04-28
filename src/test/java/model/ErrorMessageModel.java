package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ErrorMessageModel {
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

