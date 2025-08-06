package net.md_5.bungee.api.chat;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class BaseComponent {
   BaseComponent parent;
   private ChatColor color;
   private Boolean bold;
   private Boolean italic;
   private Boolean underlined;
   private Boolean strikethrough;
   private Boolean obfuscated;
   private String insertion;
   private List<BaseComponent> extra;
   private ClickEvent clickEvent;
   private HoverEvent hoverEvent;

   BaseComponent(BaseComponent old) {
      this.setColor(old.getColorRaw());
      this.setBold(old.isBoldRaw());
      this.setItalic(old.isItalicRaw());
      this.setUnderlined(old.isUnderlinedRaw());
      this.setStrikethrough(old.isStrikethroughRaw());
      this.setObfuscated(old.isObfuscatedRaw());
      this.setInsertion(old.getInsertion());
      this.setClickEvent(old.getClickEvent());
      this.setHoverEvent(old.getHoverEvent());
      if(old.getExtra() != null) {
         for(BaseComponent basecomponent : old.getExtra()) {
            this.addExtra(basecomponent.duplicate());
         }
      }

   }

   public abstract BaseComponent duplicate();

   public static String toLegacyText(BaseComponent... components) {
      StringBuilder stringbuilder = new StringBuilder();

      for(BaseComponent basecomponent : components) {
         stringbuilder.append(basecomponent.toLegacyText());
      }

      return stringbuilder.toString();
   }

   public static String toPlainText(BaseComponent... components) {
      StringBuilder stringbuilder = new StringBuilder();

      for(BaseComponent basecomponent : components) {
         stringbuilder.append(basecomponent.toPlainText());
      }

      return stringbuilder.toString();
   }

   public ChatColor getColor() {
      return this.color == null?(this.parent == null?ChatColor.WHITE:this.parent.getColor()):this.color;
   }

   public ChatColor getColorRaw() {
      return this.color;
   }

   public boolean isBold() {
      return this.bold != null?this.bold.booleanValue():this.parent != null && this.parent.isBold();
   }

   public Boolean isBoldRaw() {
      return this.bold;
   }

   public boolean isItalic() {
      return this.italic != null?this.italic.booleanValue():this.parent != null && this.parent.isItalic();
   }

   public Boolean isItalicRaw() {
      return this.italic;
   }

   public boolean isUnderlined() {
      return this.underlined != null?this.underlined.booleanValue():this.parent != null && this.parent.isUnderlined();
   }

   public Boolean isUnderlinedRaw() {
      return this.underlined;
   }

   public boolean isStrikethrough() {
      return this.strikethrough != null?this.strikethrough.booleanValue():this.parent != null && this.parent.isStrikethrough();
   }

   public Boolean isStrikethroughRaw() {
      return this.strikethrough;
   }

   public boolean isObfuscated() {
      return this.obfuscated != null?this.obfuscated.booleanValue():this.parent != null && this.parent.isObfuscated();
   }

   public Boolean isObfuscatedRaw() {
      return this.obfuscated;
   }

   public void setExtra(List<BaseComponent> components) {
      for(BaseComponent basecomponent : components) {
         basecomponent.parent = this;
      }

      this.extra = components;
   }

   public void addExtra(String text) {
      this.addExtra((BaseComponent)(new TextComponent(text)));
   }

   public void addExtra(BaseComponent component) {
      if(this.extra == null) {
         this.extra = new ArrayList();
      }

      component.parent = this;
      this.extra.add(component);
   }

   public boolean hasFormatting() {
      return this.color != null || this.bold != null || this.italic != null || this.underlined != null || this.strikethrough != null || this.obfuscated != null || this.hoverEvent != null || this.clickEvent != null;
   }

   public String toPlainText() {
      StringBuilder stringbuilder = new StringBuilder();
      this.toPlainText(stringbuilder);
      return stringbuilder.toString();
   }

   void toPlainText(StringBuilder builder) {
      if(this.extra != null) {
         for(BaseComponent basecomponent : this.extra) {
            basecomponent.toPlainText(builder);
         }
      }

   }

   public String toLegacyText() {
      StringBuilder stringbuilder = new StringBuilder();
      this.toLegacyText(stringbuilder);
      return stringbuilder.toString();
   }

   void toLegacyText(StringBuilder builder) {
      if(this.extra != null) {
         for(BaseComponent basecomponent : this.extra) {
            basecomponent.toLegacyText(builder);
         }
      }

   }

   public void setColor(ChatColor color) {
      this.color = color;
   }

   public void setBold(Boolean bold) {
      this.bold = bold;
   }

   public void setItalic(Boolean italic) {
      this.italic = italic;
   }

   public void setUnderlined(Boolean underlined) {
      this.underlined = underlined;
   }

   public void setStrikethrough(Boolean strikethrough) {
      this.strikethrough = strikethrough;
   }

   public void setObfuscated(Boolean obfuscated) {
      this.obfuscated = obfuscated;
   }

   public void setInsertion(String insertion) {
      this.insertion = insertion;
   }

   public void setClickEvent(ClickEvent clickEvent) {
      this.clickEvent = clickEvent;
   }

   public void setHoverEvent(HoverEvent hoverEvent) {
      this.hoverEvent = hoverEvent;
   }

   public String toString() {
      return "BaseComponent(color=" + this.getColor() + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough + ", obfuscated=" + this.obfuscated + ", insertion=" + this.getInsertion() + ", extra=" + this.getExtra() + ", clickEvent=" + this.getClickEvent() + ", hoverEvent=" + this.getHoverEvent() + ")";
   }

   public BaseComponent() {
   }

   public String getInsertion() {
      return this.insertion;
   }

   public List<BaseComponent> getExtra() {
      return this.extra;
   }

   public ClickEvent getClickEvent() {
      return this.clickEvent;
   }

   public HoverEvent getHoverEvent() {
      return this.hoverEvent;
   }
}
