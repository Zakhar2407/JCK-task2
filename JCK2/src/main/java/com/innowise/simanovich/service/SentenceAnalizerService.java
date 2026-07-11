package com.innowise.simanovich.service;

import com.innowise.simanovich.entity.TextComponent;

import java.util.List;

public interface SentenceAnalizerService {
  List<TextComponent> findSentencesWithIdenticalWords(TextComponent root);
}
