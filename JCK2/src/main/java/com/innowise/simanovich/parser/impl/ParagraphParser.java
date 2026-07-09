package com.innowise.simanovich.parser.impl;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.parser.TextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParagraphParser implements TextParser {

  private static final Logger logger = LogManager.getLogger(ParagraphParser.class);
  private final TextParser nextParser;

  public ParagraphParser(TextParser nextParser) {
    this.nextParser = nextParser;
  }

  @Override
  public void parse(TextComponent component, String text) {
    if (text != null) {
      String[] paragraphs = text.split(PARAGRAPH_SPLIT_REGEX);
      int length = paragraphs.length;
      logger.info("Parsed text into paragraphs. Count: " + length);

      for (String paragraph : paragraphs) {
        TextComposite paragraphComponent = new TextComposite(ComponentType.PARAGRAPH);
        component.addChild(paragraphComponent);

        if (nextParser != null) {
          nextParser.parse(paragraphComponent, paragraph);
        }
      }
    }
  }
}
