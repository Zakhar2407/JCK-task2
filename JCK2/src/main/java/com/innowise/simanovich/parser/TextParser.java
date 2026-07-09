package com.innowise.simanovich.parser;

import com.innowise.simanovich.entity.TextComponent;

public interface TextParser {
  static final String LEXEME_SPLIT_REGEX = "\\s+";
  static final String PARAGRAPH_SPLIT_REGEX = "\\n";
  static final String SENTENCE_SPLIT_REGEX = "(?<=[.!?…])\\s+";
  static final String PUNCTUATION_SPLIT_REGEX = "(?=[\\p{Punct}])|(?<=[\\p{Punct}])";
  static final String HAS_WORD_REGEX = ".*\\w+.*";

  void parse(TextComponent component, String text);
}
