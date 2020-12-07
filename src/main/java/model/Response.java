package model;

import com.google.gson.Gson;

public class Response<T> {
    private static final Gson gson = new Gson();
    private ResponseType type;
    private T message;

    public Response(ResponseType type, T message) {
        this.type = type;
        this.message = message;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String convertToJson() {
        return gson.toJson(this);
    }
}
