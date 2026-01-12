package vn.numdum.NailShop.domain;

public class RestReponse<T> {
    private int Statuscode;
    private String error;
    private Object message;
    private T data;

    public int getStatuscode() {
        return Statuscode;
    }

    public void setStatuscode(int statuscode) {
        Statuscode = statuscode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
