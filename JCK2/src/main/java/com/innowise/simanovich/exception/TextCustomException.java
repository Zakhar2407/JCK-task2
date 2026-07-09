package com.innowise.simanovich.exception;

public class TextCustomException extends Exception{

  public TextCustomException() {
    super();
  }

  public TextCustomException(String message) {
    super(message);
  }

  public TextCustomException(Throwable cause) {
    super(cause);
  }

  public TextCustomException(String message, Throwable cause) {
    super(message, cause);
  }
}
