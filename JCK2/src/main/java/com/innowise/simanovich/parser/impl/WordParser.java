package com.innowise.simanovich.parser.impl;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.parser.TextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordParser implements TextParser {

  private static final Logger logger = LogManager.getLogger(WordParser.class);
  private final TextParser nextParser;

  public WordParser(TextParser nextParser) {
    this.nextParser = nextParser;
  }

  @Override
  public void parse(TextComponent component, String text) {
    if (text != null) {
      String[] parts = text.split(PUNCTUATION_SPLIT_REGEX);
      int length = parts.length;
      logger.debug("Parsed lexeme into words and punctuation. Count: " + length);

      for (String part : parts) {
        if (!part.isBlank()) {
          boolean isWord = part.matches(HAS_WORD_REGEX);
          ComponentType type = isWord ? ComponentType.WORD : ComponentType.PUNCTUATION;

          TextComposite partComponent = new TextComposite(type);
          component.addChild(partComponent);

          if (nextParser != null) {
            nextParser.parse(partComponent, part);
          }
        }
      }
    }
  }
}
