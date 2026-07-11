package com.innowise.simanovich.reader;

import com.innowise.simanovich.exception.TextCustomException;

public interface TextReader {

  String readText(String filePath) throws TextCustomException;
}
