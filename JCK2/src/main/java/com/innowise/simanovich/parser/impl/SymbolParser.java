package com.innowise.simanovich.parser.impl;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.SymbolLeaf;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.parser.TextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SymbolParser implements TextParser {
  private static final Logger logger = LogManager.getLogger(SymbolParser.class);

  @Override
  public void parse(TextComponent component, String text) {
      if (text != null) {
        char[] chars = text.toCharArray();
        int length = chars.length;
        logger.debug("Parsed string into symbols. Count: " + length);

        for (char symbol : chars) {
          SymbolLeaf leaf = new SymbolLeaf(symbol, ComponentType.SYMBOL);
          component.addChild(leaf);
        }
      }
  }
}
