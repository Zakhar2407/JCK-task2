package com.innowise.simanovich.parser.impl;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.parser.TextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LexemeParser implements TextParser {

  private static final Logger logger = LogManager.getLogger(LexemeParser.class);
  private final TextParser nextParser;

  public LexemeParser(TextParser nextParser) {
    this.nextParser = nextParser;
  }

  @Override
  public void parse(TextComponent component, String text) {

    if (text != null) {
      String[] lexemes = text.split(LEXEME_SPLIT_REGEX);
      int length = lexemes.length;
      logger.debug("Parsed sentence into lexemes. Count: " + length);

      for (String lexeme : lexemes) {
        TextComposite lexemeComponent = new TextComposite(ComponentType.LEXEME);
        component.addChild(lexemeComponent);

        if (nextParser != null) {
          nextParser.parse(lexemeComponent, lexeme);
        }
      }
    }

  }
}
