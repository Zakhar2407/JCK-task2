package com.innowise.simanovich.entity;

import java.util.List;

public interface TextComponent {
    void addChild(TextComponent component);

    void removeChild(TextComponent component);

    List<TextComponent> getChildComponents();

    ComponentType getComponentType();

    String toOriginalString();
}
