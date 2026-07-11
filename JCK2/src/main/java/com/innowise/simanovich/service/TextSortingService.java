package com.innowise.simanovich.service;

import com.innowise.simanovich.entity.TextComponent;

public interface TextSortingService {

  void sortParagraphsByLetter(TextComponent root, char letter);
}
