package Assignment.StockManagementSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    protected <T> ResponseEntity<ResponseObject> sendResponse(T data, HttpStatus status) {
        return new ResponseEntity<>(new ResponseObject(data, status), status);
    }

    protected <T> ResponseEntity<ResponseObject> sendSuccessResponse(T data,HttpStatus status) {
        return sendResponse(data, status);
    }

    protected <T> ResponseEntity<ResponseObject> sendCreatedResponse(T data,HttpStatus status) {
        return sendResponse(data, status);
    }

    protected <T> ResponseEntity<ResponseObject> sendErrorResponse(String message, HttpStatus status){
        return sendResponse(message,status);
    }

    static class ResponseObject {
        private Object data;
        private HttpStatus status;

        public ResponseObject(Object data, HttpStatus status) {
            this.data = data;
            this.status = status;
        }

        public Object getData() {
            return data;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }
}
