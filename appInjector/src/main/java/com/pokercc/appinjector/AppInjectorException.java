package com.pokercc.appinjector;

/**
 * Created by Cisco on 2017/11/21.
 */

public class AppInjectorException extends RuntimeException {

    public AppInjectorException(String message) {
        super(message);
    }

    public AppInjectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppInjectorException(Throwable cause) {
        super(cause);
    }
}
