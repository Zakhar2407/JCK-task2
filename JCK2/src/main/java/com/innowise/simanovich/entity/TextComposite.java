package com.innowise.simanovich.entity;

import java.util.List;
import java.util.ArrayList;

public class TextComposite implements TextComponent {

  private final List<TextComponent> components = new ArrayList<>();
  private final ComponentType type;

  public TextComposite(ComponentType type) {
    this.type = type;
  }

  @Override
  public void addChild(TextComponent component) {
    if (component != null) {
      components.add(component);
    }
  }

  @Override
  public void removeChild(TextComponent component) {
    if (component != null) {
      components.remove(component);
    }
  }

  @Override
  public List<TextComponent> getChildComponents() {
    return new ArrayList<>(components);
  }

  @Override
  public ComponentType getComponentType() {
    return type;
  }

  public void replaceChildren(List<TextComponent> newChildren) {
    components.clear();
    components.addAll(newChildren);
  }

  public List<TextComponent> getWords() {
    List<TextComponent> words = new ArrayList<>();
    boolean isWord = type == ComponentType.WORD;

    if (isWord) {
      words.add(this);
    } else {
      for (TextComponent child : components) {
        if (child instanceof TextComposite) {
          TextComposite compositeChild = (TextComposite) child;
          List<TextComponent> childWords = compositeChild.getWords();
          words.addAll(childWords);
        }
      }
    }
    return words;
  }

  @Override
  public String toOriginalString() {
    StringBuilder stringBuilder = new StringBuilder();
    int size = components.size();

    for (int i = 0; i < size; i++) {
      TextComponent child = components.get(i);
      String childText = child.toOriginalString();
      stringBuilder.append(childText);

      if (i < size - 1) {
        if (type == ComponentType.TEXT) {
          stringBuilder.append("\n");
        } else if (type == ComponentType.PARAGRAPH) {
          stringBuilder.append("\t");
        } else if (type == ComponentType.SENTENCE) {
          stringBuilder.append(" ");
        }
      }
    }

    return stringBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    TextComposite that = (TextComposite) obj;

    if (type != that.type) {
      return false;
    }

    int thisSize = components.size();
    int thatSize = that.components.size();
    if (thisSize != thatSize) {
      return false;
    }

    for (int i = 0; i < thisSize; i++) {
      TextComponent thisChild = components.get(i);
      TextComponent thatChild = that.components.get(i);

      if (thisChild == null) {
        if (thatChild != null) {
          return false;
        }
      } else if (!thisChild.equals(thatChild)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    for (TextComponent component : components) {
      result = 31 * result + (component != null ? component.hashCode() : 0);
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("TextComposite{type=");
    sb.append(type);
    sb.append(", childrenCount=");
    sb.append(components.size());
    sb.append(", text='");
    sb.append(toOriginalString());
    sb.append("'}");
    return sb.toString();
  }
}