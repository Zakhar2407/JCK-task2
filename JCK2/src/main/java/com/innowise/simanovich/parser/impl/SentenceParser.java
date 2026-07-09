package com.innowise.simanovich.parser.impl;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.parser.TextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SentenceParser implements TextParser {

  private static final Logger logger = LogManager.getLogger(SentenceParser.class);
  private final TextParser nextParser;

  public SentenceParser(TextParser nextParser) {
    this.nextParser = nextParser;
  }

  @Override
  public void parse(TextComponent component, String text) {
    if (text != null) {
      String[] sentences = text.split(SENTENCE_SPLIT_REGEX);
      int length = sentences.length;
      logger.debug("Parsed paragraph into sentences. Count: " + length);

      for (String sentence : sentences) {
        TextComposite sentenceComponent = new TextComposite(ComponentType.SENTENCE);
        component.addChild(sentenceComponent);

        if (nextParser != null) {
          nextParser.parse(sentenceComponent, sentence);
        }
      }
    }
  }
}
