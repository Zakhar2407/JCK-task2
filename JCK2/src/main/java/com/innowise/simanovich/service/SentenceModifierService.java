package com.innowise.simanovich.service;

import com.innowise.simanovich.entity.TextComponent;

public interface SentenceModifierService {
  void swapFirstAndLastLexemes(TextComponent root);
}
