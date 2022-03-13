package indi.huhy.archetypes.jwtsecurityweb.entity.response;

import lombok.Data;

@Data
public class BaseResult<T> {

    private int code;
    private String msg;
    private T t;

    public BaseResult(int code) {
        this.code = code;
    }

    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(int code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.t = t;
    }
}
