package net.md_5.bungee.api.chat;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;

public class TextComponent extends BaseComponent {
   private static final Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
   private String text;

   public static BaseComponent[] fromLegacyText(String message) {
      ArrayList<BaseComponent> arraylist = new ArrayList();
      StringBuilder stringbuilder = new StringBuilder();
      TextComponent textcomponent = new TextComponent();
      Matcher matcher = url.matcher(message);

      for(int i = 0; i < message.length(); ++i) {
         char c0 = message.charAt(i);
         if(c0 == 167) {
            ++i;
            c0 = message.charAt(i);
            if(c0 >= 65 && c0 <= 90) {
               c0 = (char)(c0 + 32);
            }

            ChatColor chatcolor = ChatColor.getByChar(c0);
            if(chatcolor != null) {
               if(stringbuilder.length() > 0) {
                  TextComponent textcomponent1 = textcomponent;
                  textcomponent = new TextComponent(textcomponent);
                  textcomponent1.setText(stringbuilder.toString());
                  stringbuilder = new StringBuilder();
                  arraylist.add(textcomponent1);
               }

               switch(chatcolor) {
               case BOLD:
                  textcomponent.setBold(Boolean.valueOf(true));
                  break;
               case ITALIC:
                  textcomponent.setItalic(Boolean.valueOf(true));
                  break;
               case UNDERLINE:
                  textcomponent.setUnderlined(Boolean.valueOf(true));
                  break;
               case STRIKETHROUGH:
                  textcomponent.setStrikethrough(Boolean.valueOf(true));
                  break;
               case MAGIC:
                  textcomponent.setObfuscated(Boolean.valueOf(true));
                  break;
               case RESET:
                  chatcolor = ChatColor.WHITE;
               default:
                  textcomponent = new TextComponent();
                  textcomponent.setColor(chatcolor);
               }
            }
         } else {
            int j = message.indexOf(32, i);
            if(j == -1) {
               j = message.length();
            }

            if(matcher.region(i, j).find()) {
               if(stringbuilder.length() > 0) {
                  TextComponent textcomponent2 = textcomponent;
                  textcomponent = new TextComponent(textcomponent);
                  textcomponent2.setText(stringbuilder.toString());
                  stringbuilder = new StringBuilder();
                  arraylist.add(textcomponent2);
               }

               textcomponent = new TextComponent(textcomponent);
               String s = message.substring(i, j);
               textcomponent.setText(s);
               textcomponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, s.startsWith("http")?s:"http://" + s));
               arraylist.add(textcomponent);
               i += j - i - 1;
               textcomponent = textcomponent;
            } else {
               stringbuilder.append(c0);
            }
         }
      }

      if(stringbuilder.length() > 0) {
         textcomponent.setText(stringbuilder.toString());
         arraylist.add(textcomponent);
      }

      if(arraylist.isEmpty()) {
         arraylist.add(new TextComponent(""));
      }

      return (BaseComponent[])arraylist.toArray(new BaseComponent[arraylist.size()]);
   }

   public TextComponent(TextComponent textComponent) {
      super(textComponent);
      this.setText(textComponent.getText());
   }

   public TextComponent(BaseComponent... extras) {
      this.setText("");
      this.setExtra(new ArrayList(Arrays.asList(extras)));
   }

   public BaseComponent duplicate() {
      return new TextComponent(this);
   }

   protected void toPlainText(StringBuilder builder) {
      builder.append(this.text);
      super.toPlainText(builder);
   }

   protected void toLegacyText(StringBuilder builder) {
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

      builder.append(this.text);
      super.toLegacyText(builder);
   }

   public String toString() {
      return String.format("TextComponent{text=%s, %s}", new Object[]{this.text, super.toString()});
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   @ConstructorProperties({"text"})
   public TextComponent(String text) {
      this.text = text;
   }

   public TextComponent() {
   }
}
