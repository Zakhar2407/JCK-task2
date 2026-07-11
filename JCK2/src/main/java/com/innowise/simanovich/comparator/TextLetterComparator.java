package com.innowise.simanovich.comparator;

import com.innowise.simanovich.entity.TextComponent;

import java.util.Comparator;

public class TextLetterComparator implements Comparator<TextComponent> {
  private final char targetLetter;

  public TextLetterComparator(char targetLetter) {
    this.targetLetter = Character.toLowerCase(targetLetter);
  }

  @Override
  public int compare(TextComponent o1, TextComponent o2) {
    int count1 = countLetter(o1);
    int count2 = countLetter(o2);

    int result = Integer.compare(count1, count2);

    if (result == 0) {
      String str1 = o1.toOriginalString();
      String str2 = o2.toOriginalString();
      result = str1.compareToIgnoreCase(str2);
    }

    return result;
  }

  private int countLetter(TextComponent component) {
    String text = component.toOriginalString();
    String lowerText = text.toLowerCase();
    char[] chars = lowerText.toCharArray();
    int count = 0;

    for (char c : chars) {
      if (c == targetLetter) {
        count++;
      }
    }
    return count;
  }
}
