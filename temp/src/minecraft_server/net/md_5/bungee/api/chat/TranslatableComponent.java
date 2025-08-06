package net.md_5.bungee.api.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class TranslatableComponent extends BaseComponent {
   private final ResourceBundle locales = ResourceBundle.getBundle("mojang-translations/en_US");
   private final Pattern format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
   private String translate;
   private List<BaseComponent> with;

   public TranslatableComponent(TranslatableComponent original) {
      super(original);
      this.setTranslate(original.getTranslate());
      if(original.getWith() != null) {
         List<BaseComponent> list = new ArrayList();

         for(BaseComponent basecomponent : original.getWith()) {
            list.add(basecomponent.duplicate());
         }

         this.setWith(list);
      }

   }

   public TranslatableComponent(String translate, Object... with) {
      this.setTranslate(translate);
      List<BaseComponent> list = new ArrayList();

      for(Object object : with) {
         if(object instanceof String) {
            list.add(new TextComponent((String)object));
         } else {
            list.add((BaseComponent)object);
         }
      }

      this.setWith(list);
   }

   public BaseComponent duplicate() {
      return new TranslatableComponent(this);
   }

   public void setWith(List<BaseComponent> components) {
      for(BaseComponent basecomponent : components) {
         basecomponent.parent = this;
      }

      this.with = components;
   }

   public void addWith(String text) {
      this.addWith((BaseComponent)(new TextComponent(text)));
   }

   public void addWith(BaseComponent component) {
      if(this.with == null) {
         this.with = new ArrayList();
      }

      component.parent = this;
      this.with.add(component);
   }

   protected void toPlainText(StringBuilder builder) {
      String s;
      try {
         s = this.locales.getString(this.translate);
      } catch (MissingResourceException var9) {
         s = this.translate;
      }

      Matcher matcher = this.format.matcher(s);
      int i = 0;
      int j = 0;

      while(matcher.find(i)) {
         int k = matcher.start();
         if(k != i) {
            builder.append(s.substring(i, k));
         }

         i = matcher.end();
         String s1 = matcher.group(2);
         switch(s1.charAt(0)) {
         case '%':
            builder.append('%');
            break;
         case 'd':
         case 's':
            String s2 = matcher.group(1);
            ((BaseComponent)this.with.get(s2 != null?Integer.parseInt(s2) - 1:j++)).toPlainText(builder);
         }
      }

      if(s.length() != i) {
         builder.append(s.substring(i, s.length()));
      }

      super.toPlainText(builder);
   }

   protected void toLegacyText(StringBuilder builder) {
      String s;
      try {
         s = this.locales.getString(this.translate);
      } catch (MissingResourceException var9) {
         s = this.translate;
      }

      Matcher matcher = this.format.matcher(s);
      int i = 0;
      int j = 0;

      while(matcher.find(i)) {
         int k = matcher.start();
         if(k != i) {
            this.addFormat(builder);
            builder.append(s.substring(i, k));
         }

         i = matcher.end();
         String s1 = matcher.group(2);
         switch(s1.charAt(0)) {
         case '%':
            this.addFormat(builder);
            builder.append('%');
            break;
         case 'd':
         case 's':
            String s2 = matcher.group(1);
            ((BaseComponent)this.with.get(s2 != null?Integer.parseInt(s2) - 1:j++)).toLegacyText(builder);
         }
      }

      if(s.length() != i) {
         this.addFormat(builder);
         builder.append(s.substring(i, s.length()));
      }

      super.toLegacyText(builder);
   }

   private void addFormat(StringBuilder builder) {
      builder.append(this.getColor());
      if(this.isBold()) {
         builder.append(ChatColor.BOLD);
      }

      if(this.isItalic()) {
         builder.append(ChatColor.ITALIC);
      }

      if(this.isUnderlined()) {
         builder.append(ChatColor.UNDERLINE);
      }

      if(this.isStrikethrough()) {
         builder.append(ChatColor.STRIKETHROUGH);
      }

      if(this.isObfuscated()) {
         builder.append(ChatColor.MAGIC);
      }

   }

   public ResourceBundle getLocales() {
      return this.locales;
   }

   public Pattern getFormat() {
      return this.format;
   }

   public String getTranslate() {
      return this.translate;
   }

   public List<BaseComponent> getWith() {
      return this.with;
   }

   public void setTranslate(String translate) {
      this.translate = translate;
   }

   public String toString() {
      return "TranslatableComponent(locales=" + this.getLocales() + ", format=" + this.getFormat() + ", translate=" + this.getTranslate() + ", with=" + this.getWith() + ")";
   }

   public TranslatableComponent() {
   }
}
