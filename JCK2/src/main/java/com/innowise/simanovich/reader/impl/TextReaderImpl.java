package com.innowise.simanovich.reader.impl;

import com.innowise.simanovich.exception.TextCustomException;
import com.innowise.simanovich.reader.TextReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextReaderImpl implements TextReader {

  private static final Logger logger = LogManager.getLogger(TextReaderImpl.class);

  @Override
  public String readText(String filePath) throws TextCustomException {
    if (filePath != null) {
      Path path = Path.of(filePath);

      try {
        String text = Files.readString(path);
        logger.info("File successfully read: " + filePath);
        return text;
      } catch (IOException e) {
        String errorMessage = e.getMessage();
        logger.error("Failed to read file: " + filePath + " | Reason: " + errorMessage);
        throw new TextCustomException("Failed to read file", e);
      }

    } else {
      logger.error("File path is null");
      throw new TextCustomException("File path cannot be null");
    }
  }
}
