package turing.turingcodey.core;

public class ServiceResult<T> {
    private boolean success ;
    private String msg;
    private T data;

    public ServiceResult(boolean success, String msg, T data){
        this.success = success;
        this.msg = msg;
        this. data = data;
    }
}
