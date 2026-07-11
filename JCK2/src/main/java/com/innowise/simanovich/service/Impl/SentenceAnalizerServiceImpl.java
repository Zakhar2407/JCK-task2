package com.innowise.simanovich.service.Impl;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.service.SentenceAnalizerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SentenceAnalizerServiceImpl implements SentenceAnalizerService {

  private static final Logger logger = LogManager.getLogger(SentenceAnalizerServiceImpl.class);

  @Override
  public List<TextComponent> findSentencesWithIdenticalWords(TextComponent root) {
    List<TextComponent> result = new ArrayList<>();

    if (root != null) {
      List<TextComponent> sentences = findSentences(root);

      for (TextComponent sentence : sentences) {
        if (hasIdenticalWords(sentence)) {
          result.add(sentence);
        }
      }
      int size = result.size();
      logger.info("Found sentences with identical words: {}", size);
    }
    return result;
  }

  private List<TextComponent> findSentences(TextComponent component) {
    List<TextComponent> sentences = new ArrayList<>();
    ComponentType type = component.getComponentType();

    if (type == ComponentType.SENTENCE) {
      sentences.add(component);
    } else {
      List<TextComponent> children = component.getChildComponents();
      for (TextComponent child : children) {
        List<TextComponent> childSentences = findSentences(child);
        sentences.addAll(childSentences);
      }
    }
    return sentences;
  }

  private boolean hasIdenticalWords(TextComponent sentence) {
    if (sentence instanceof TextComposite) {
      TextComposite composite = (TextComposite) sentence;
      List<TextComponent> words = composite.getWords();
      Set<String> uniqueWords = new HashSet<>();

      for (TextComponent word : words) {
        String wordStr = word.toOriginalString();
        String lowerWord = wordStr.toLowerCase();
        boolean isAdded = uniqueWords.add(lowerWord);

        if (!isAdded) {
          return true;
        }
      }
    }
    return false;
  }
}

