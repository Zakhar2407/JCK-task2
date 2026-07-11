package com.innowise.simanovich.service.Impl;


import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.service.SentenceModifierService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SentenceModifierServiceImpl implements SentenceModifierService {
  private static final Logger logger = LogManager.getLogger(SentenceModifierServiceImpl.class);

  @Override
  public void swapFirstAndLastLexemes(TextComponent root) {

    if (root != null) {
      ComponentType type = root.getComponentType();

      if (type == ComponentType.SENTENCE) {
        List<TextComponent> children = root.getChildComponents();
        int size = children.size();

        if (size > 1) {
          int lastIndex = size - 1;
          TextComponent first = children.getFirst();
          TextComponent last = children.get(lastIndex);

          root.removeChild(first);
          root.removeChild(last);

          List<TextComponent> allChildren = root.getChildComponents();
          for (TextComponent c : allChildren) {
            root.removeChild(c);
          }

          root.addChild(last);
          for (int i = 1; i < lastIndex; i++) {
            TextComponent middle = children.get(i);
            root.addChild(middle);
          }
          root.addChild(first);
        }
      } else {
        List<TextComponent> children = root.getChildComponents();
        for (TextComponent child : children) {
          swapFirstAndLastLexemes(child);
        }
      }
      logger.info("Successfully swapped lexemes");
    }
  }
}
