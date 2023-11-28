package com.webflux.reactivesecurity.Exceptions;

public class AuthException extends ApiException{
    public AuthException(String message, String erroCode) {
        super(message, erroCode);
    }
}
