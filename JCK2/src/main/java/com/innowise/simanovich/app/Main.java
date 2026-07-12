package com.innowise.simanovich.app;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.exception.TextCustomException;
import com.innowise.simanovich.parser.TextParser;
import com.innowise.simanovich.parser.impl.ParagraphParser;
import com.innowise.simanovich.parser.impl.SentenceParser;
import com.innowise.simanovich.parser.impl.LexemeParser;
import com.innowise.simanovich.parser.impl.WordParser;
import com.innowise.simanovich.parser.impl.SymbolParser;
import com.innowise.simanovich.reader.TextReader;
import com.innowise.simanovich.reader.impl.TextReaderImpl;
import com.innowise.simanovich.service.SentenceAnalizerService;
import com.innowise.simanovich.service.TextSortingService;
import com.innowise.simanovich.service.SentenceModifierService;
import com.innowise.simanovich.service.Impl.SentenceAnalizerServiceImpl;
import com.innowise.simanovich.service.Impl.TextSortingServiceImpl;
import com.innowise.simanovich.service.Impl.SentenceModifierServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

class TextProcessorApp {

  private static final Logger logger = LogManager.getLogger(TextProcessorApp.class);
  private static final String FILE_PATH = "data/text.txt";

  static void main(String[] args) {
    logger.info("=== Text Processor Application Started ===");

    try {
      TextReader reader = new TextReaderImpl();
      String originalText = reader.readText(FILE_PATH);

      if (originalText == null || originalText.isBlank()) {
        logger.warn("File is empty or not found. Exiting.");
        return;
      }
      logger.info("Successfully read text from file. Length: {} characters", originalText.length());

      TextComponent textRoot = new TextComposite(ComponentType.TEXT);

      TextParser symbolParser = new SymbolParser();
      TextParser wordParser = new WordParser(symbolParser);
      TextParser lexemeParser = new LexemeParser(wordParser);
      TextParser sentenceParser = new SentenceParser(lexemeParser);
      TextParser paragraphParser = new ParagraphParser(sentenceParser);

      logger.info("Starting text parsing...");
      paragraphParser.parse(textRoot, originalText);
      logger.info("Text parsing completed successfully.");

      String restoredText = textRoot.toOriginalString();
      logger.info("\n=== Restored Text ===\n{}\n=====================", restoredText);

      int letterCount = countLetters(textRoot);
      int charCount = countCharacters(textRoot);
      logger.info("\n=== Statistics ===");
      logger.info("Total characters: {}", charCount);
      logger.info("Total letters: {}", letterCount);

      SentenceAnalizerService analyzer = new SentenceAnalizerServiceImpl();
      TextSortingService sortingService = new TextSortingServiceImpl();
      SentenceModifierService modifierService = new SentenceModifierServiceImpl();

      logger.info("\n=== Task 1: Find sentences with identical words ===");
      List<TextComponent> sentencesWithDuplicates = analyzer.findSentencesWithIdenticalWords(textRoot);
      logger.info("Found {} sentences with identical words", sentencesWithDuplicates.size());
      for (TextComponent sentence : sentencesWithDuplicates) {
        logger.info("Sentence: {}", sentence.toOriginalString());
      }

      logger.info("\n=== Task 2: Sort sentences by letter count ===");
      char targetLetter = 'e';
      logger.info("Sorting sentences by count of letter '{}'", targetLetter);
      sortingService.sortParagraphsByLetter(textRoot, targetLetter);
      logger.info("\n=== Sorted Text ===\n{}\n=====================", textRoot.toOriginalString());

      logger.info("\n=== Task 3: Swap first and last lexeme in sentences ===");
      modifierService.swapFirstAndLastLexemes(textRoot);
      logger.info("\n=== Text with Swapped Lexemes ===\n{}\n=====================", textRoot.toOriginalString());

      logger.info("\n=== Application Finished Successfully ===");

    } catch (TextCustomException e) {
      logger.error("Text processing error: {}", e.getMessage(), e);
    } catch (Exception e) {
      logger.error("Unexpected error: {}", e.getMessage(), e);
    }
  }

  private static int countLetters(TextComponent component) {
    int count = 0;
    if (component.getComponentType() == ComponentType.SYMBOL) {
      String value = component.toOriginalString();
      if (value.length() == 1 && Character.isLetter(value.charAt(0))) {
        return 1;
      }
      return 0;
    }
    for (TextComponent child : component.getChildComponents()) {
      count += countLetters(child);
    }
    return count;
  }

  private static int countCharacters(TextComponent component) {
    int count = 0;
    if (component.getComponentType() == ComponentType.SYMBOL) {
      return 1;
    }
    for (TextComponent child : component.getChildComponents()) {
      count += countCharacters(child);
    }
    return count;
  }
}