package com.innowise.simanovich.service.Impl;

import com.innowise.simanovich.comparator.TextLetterComparator;
import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.service.TextSortingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TextSortingServiceImpl implements TextSortingService {

  private static final Logger logger = LogManager.getLogger(TextSortingServiceImpl.class);

  @Override
  public void sortParagraphsByLetter(TextComponent root, char letter) {

    if (root != null) {
      ComponentType type = root.getComponentType();

      if (type == ComponentType.TEXT) {
        TextLetterComparator comparator = new TextLetterComparator(letter);
        List<TextComponent> paragraphs = root.getChildComponents();
        paragraphs.sort(comparator);


        if (root instanceof TextComposite) {
          ((TextComposite) root).replaceChildren(paragraphs);
        }
        logger.info("Sorted paragraphs by letter: " + letter);
      }
    }
  }
}
