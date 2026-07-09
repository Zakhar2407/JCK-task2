package com.innowise.simanovich.entity;

import java.util.List;

public class SymbolLeaf implements TextComponent{

  private final char value;
  private final ComponentType type;

  public SymbolLeaf(char value, ComponentType type) {
    this.value = value;
    this.type = type;
  }

  @Override
  public void addChild(TextComponent component) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeChild(TextComponent component) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<TextComponent> getChildComponents() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ComponentType getComponentType() {
    return type;
  }

  @Override
  public String toOriginalString() {
    return String.valueOf(value);
  }


@Override
public boolean equals(Object obj) {
  if (this == obj) {
    return true;
  }
  if (obj == null) {
    return false;
  }

  Class<?> thisClass = getClass();
  Class<?> objClass = obj.getClass();
  if (thisClass != objClass) {
    return false;
  }

  SymbolLeaf that = (SymbolLeaf) obj;
  if (this.type != that.type) {
    return false;
  }

  return this.value == that.value;
}

@Override
public int hashCode() {
  int result = 1;
  int typeHash = type != null ? type.hashCode() : 0;
  result = 31 * result + typeHash;
  result = 31 * result + Character.hashCode(value);
  return result;
}

@Override
public String toString() {
  StringBuilder stringBuilder = new StringBuilder();

  stringBuilder.append("SymbolLeaf{type=");
  stringBuilder.append(type);
  stringBuilder.append(", value='");
  stringBuilder.append(value);
  stringBuilder.append("'}");

  return stringBuilder.toString();
}
}