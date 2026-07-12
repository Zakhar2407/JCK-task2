package com.innowise.simanovich.parser;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.parser.impl.LexemeParser;
import com.innowise.simanovich.parser.impl.SymbolParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LexemeParserTest {

  private LexemeParser lexemeParser;
  private TextComponent parentComponent;

  @BeforeEach
  public void setUp() {
    SymbolParser symbolParser = new SymbolParser();
    lexemeParser = new LexemeParser(symbolParser);
    parentComponent = new TextComposite(ComponentType.SENTENCE);
  }

  @Test
  public void testParseSimpleText() {
    String text = "Hello world again";

    lexemeParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(3, children.size(), "Должно быть создано 3 лексемы");

    assertEquals("Hello", children.get(0).toOriginalString());
    assertEquals("world", children.get(1).toOriginalString());
    assertEquals("again", children.get(2).toOriginalString());
  }

  @Test
  public void testParseWithPunctuation() {
    String text = "Hello, world!";

    lexemeParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(2, children.size());
    assertEquals("Hello,", children.get(0).toOriginalString());
    assertEquals("world!", children.get(1).toOriginalString());
  }

  @Test
  public void testParseTextWithMultipleSpaces() {
    String text = "Hello   world    again";

    lexemeParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(3, children.size(), "Множественные пробелы должны игнорироваться");
  }

  @Test
  public void testParseNullText() {
    assertDoesNotThrow(() -> lexemeParser.parse(parentComponent, null));
    assertEquals(0, parentComponent.getChildComponents().size(),
            "При null тексте не должно создаваться компонентов");
  }

  @Test
  public void testLexemeComponentType() {
    String text = "Hello world";

    lexemeParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    for (TextComponent child : children) {
      assertEquals(ComponentType.LEXEME, child.getComponentType(),
              "Каждый созданный компонент должен иметь тип LEXEME");
    }
  }

  @Test
  public void testParseWithMathExpressions() {
    String text = "13<<2 electronic";

    lexemeParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(2, children.size());
    assertEquals("13<<2", children.get(0).toOriginalString());
    assertEquals("electronic", children.get(1).toOriginalString());
  }
}