package net.md_5.bungee.api.chat;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ComponentBuilder {
   private TextComponent current;
   private final List<BaseComponent> parts = new ArrayList();

   public ComponentBuilder(ComponentBuilder original) {
      this.current = new TextComponent(original.current);

      for(BaseComponent basecomponent : original.parts) {
         this.parts.add(basecomponent.duplicate());
      }

   }

   public ComponentBuilder(String text) {
      this.current = new TextComponent(text);
   }

   public ComponentBuilder append(String text) {
      return this.append(text, ComponentBuilder.FormatRetention.ALL);
   }

   public ComponentBuilder append(String text, ComponentBuilder.FormatRetention retention) {
      this.parts.add(this.current);
      this.current = new TextComponent(this.current);
      this.current.setText(text);
      this.retain(retention);
      return this;
   }

   public ComponentBuilder color(ChatColor color) {
      this.current.setColor(color);
      return this;
   }

   public ComponentBuilder bold(boolean bold) {
      this.current.setBold(Boolean.valueOf(bold));
      return this;
   }

   public ComponentBuilder italic(boolean italic) {
      this.current.setItalic(Boolean.valueOf(italic));
      return this;
   }

   public ComponentBuilder underlined(boolean underlined) {
      this.current.setUnderlined(Boolean.valueOf(underlined));
      return this;
   }

   public ComponentBuilder strikethrough(boolean strikethrough) {
      this.current.setStrikethrough(Boolean.valueOf(strikethrough));
      return this;
   }

   public ComponentBuilder obfuscated(boolean obfuscated) {
      this.current.setObfuscated(Boolean.valueOf(obfuscated));
      return this;
   }

   public ComponentBuilder insertion(String insertion) {
      this.current.setInsertion(insertion);
      return this;
   }

   public ComponentBuilder event(ClickEvent clickEvent) {
      this.current.setClickEvent(clickEvent);
      return this;
   }

   public ComponentBuilder event(HoverEvent hoverEvent) {
      this.current.setHoverEvent(hoverEvent);
      return this;
   }

   public ComponentBuilder reset() {
      return this.retain(ComponentBuilder.FormatRetention.NONE);
   }

   public ComponentBuilder retain(ComponentBuilder.FormatRetention retention) {
      BaseComponent basecomponent = this.current;
      switch(retention) {
      case NONE:
         this.current = new TextComponent(this.current.getText());
      case ALL:
      default:
         break;
      case EVENTS:
         this.current = new TextComponent(this.current.getText());
         this.current.setInsertion(basecomponent.getInsertion());
         this.current.setClickEvent(basecomponent.getClickEvent());
         this.current.setHoverEvent(basecomponent.getHoverEvent());
         break;
      case FORMATTING:
         this.current.setClickEvent((ClickEvent)null);
         this.current.setHoverEvent((HoverEvent)null);
      }

      return this;
   }

   public BaseComponent[] create() {
      this.parts.add(this.current);
      return (BaseComponent[])this.parts.toArray(new BaseComponent[this.parts.size()]);
   }

   public static enum FormatRetention {
      NONE,
      FORMATTING,
      EVENTS,
      ALL;
   }
}
