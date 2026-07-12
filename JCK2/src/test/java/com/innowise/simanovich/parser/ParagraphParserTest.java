package com.innowise.simanovich.parser;

import com.innowise.simanovich.entity.ComponentType;
import com.innowise.simanovich.entity.TextComponent;
import com.innowise.simanovich.entity.TextComposite;
import com.innowise.simanovich.parser.impl.ParagraphParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParagraphParserTest {

  private ParagraphParser paragraphParser;
  private StubNextParser stubNextParser;
  private TextComponent parentComponent;

  @BeforeEach
  public void setUp() {
    stubNextParser = new StubNextParser();
    paragraphParser = new ParagraphParser(stubNextParser);
    parentComponent = new TextComposite(ComponentType.TEXT);
  }

  @Test
  public void testParseMultipleParagraphs() {
    String text = "First paragraph.\nSecond paragraph.\nThird paragraph.";

    paragraphParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(3, children.size(), "Должно быть создано 3 абзаца");
  }

  @Test
  public void testParseSingleParagraph() {
    String text = "Single paragraph without newlines.";

    paragraphParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(1, children.size(), "Должен быть создан 1 абзац");
  }

  @Test
  public void testParseNullText() {
    assertDoesNotThrow(() -> paragraphParser.parse(parentComponent, null));
    assertEquals(0, parentComponent.getChildComponents().size(),
            "При null тексте не должно создаваться компонентов");
  }

  @Test
  public void testParseEmptyString() {
    paragraphParser.parse(parentComponent, "");

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(1, children.size());
  }

  @Test
  public void testParagraphComponentType() {
    String text = "First paragraph.\nSecond paragraph.";

    paragraphParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    for (TextComponent child : children) {
      assertEquals(ComponentType.PARAGRAPH, child.getComponentType(),
              "Каждый созданный компонент должен иметь тип PARAGRAPH");
    }
  }

  @Test
  public void testNextParserIsCalledForEachParagraph() {
    String text = "First paragraph.\nSecond paragraph.\nThird paragraph.";

    paragraphParser.parse(parentComponent, text);

    assertEquals(3, stubNextParser.callCount,
            "Следующий парсер должен быть вызван для каждого абзаца");
  }

  @Test
  public void testNextParserReceivesCorrectText() {
    String text = "First paragraph.\nSecond paragraph.";

    paragraphParser.parse(parentComponent, text);

    assertEquals(2, stubNextParser.receivedTexts.size());
    assertEquals("First paragraph.", stubNextParser.receivedTexts.get(0));
    assertEquals("Second paragraph.", stubNextParser.receivedTexts.get(1));
  }

  @Test
  public void testNextParserReceivesCorrectComponents() {
    String text = "First paragraph.\nSecond paragraph.";

    paragraphParser.parse(parentComponent, text);

    assertEquals(2, stubNextParser.receivedComponents.size());
    for (TextComponent component : stubNextParser.receivedComponents) {
      assertEquals(ComponentType.PARAGRAPH, component.getComponentType(),
              "Следующий парсер должен получать компоненты типа PARAGRAPH");
    }
  }

  @Test
  public void testParseWithEmptyParagraphs() {
    String text = "First paragraph.\n\nThird paragraph.";

    paragraphParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(3, children.size(), "Должно быть создано 3 компонента (включая пустой)");
  }

  @Test
  public void testParagraphsAddedToParent() {
    String text = "First paragraph.\nSecond paragraph.";

    paragraphParser.parse(parentComponent, text);

    List<TextComponent> children = parentComponent.getChildComponents();
    assertTrue(children.get(0) instanceof TextComposite);
    assertTrue(children.get(1) instanceof TextComposite);
  }

  @Test
  public void testParseWithNullNextParser() {
    ParagraphParser parserWithoutNext = new ParagraphParser(null);
    String text = "First paragraph.\nSecond paragraph.";

    assertDoesNotThrow(() -> parserWithoutNext.parse(parentComponent, text));

    List<TextComponent> children = parentComponent.getChildComponents();
    assertEquals(2, children.size(), "Абзацы должны быть созданы даже без nextParser");
  }


  private static class StubNextParser implements TextParser {
    int callCount = 0;
    List<String> receivedTexts = new ArrayList<>();
    List<TextComponent> receivedComponents = new ArrayList<>();

    @Override
    public void parse(TextComponent component, String text) {
      callCount++;
      receivedTexts.add(text);
      receivedComponents.add(component);
    }
  }
}