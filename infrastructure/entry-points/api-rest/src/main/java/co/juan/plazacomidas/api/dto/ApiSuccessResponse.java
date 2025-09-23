package co.juan.plazacomidas.api.dto;

import lombok.Data;

@Data
public class ApiSuccessResponse<T> {
    private boolean success;
    private String errorMessage;
    private T data;

    public ApiSuccessResponse(T data) {
        this.success = true;
        this.errorMessage = null;
        this.data = data;
    }
}
