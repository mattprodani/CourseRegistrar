package Views;

public class APIResultWrapper<T> {
    public final T data;
    public final boolean success;

    public APIResultWrapper(T data, boolean success) {
        this.data = data;
        this.success = success;
        this.errormsg = null;
    }

    public APIResultWrapper(T data, boolean success, String errormsg) {
        this.data = data;
        this.success = success;
        this.errormsg = errormsg;
    }

    public final String errormsg;
}
