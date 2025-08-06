package net.minecraft.server.v1_8_R3;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MojangsonParser {
   private static final Logger a = LogManager.getLogger();
   private static final Pattern b = Pattern.compile("\\[[-+\\d|,\\s]+\\]");

   public static NBTTagCompound parse(String p_parse_0_) throws MojangsonParseException {
      p_parse_0_ = p_parse_0_.trim();
      if(!p_parse_0_.startsWith("{")) {
         throw new MojangsonParseException("Invalid tag encountered, expected \'{\' as first char.");
      } else if(b(p_parse_0_) != 1) {
         throw new MojangsonParseException("Encountered multiple top tags, only one expected");
      } else {
         return (NBTTagCompound)a("tag", p_parse_0_).a();
      }
   }

   static int b(String p_b_0_) throws MojangsonParseException {
      int i = 0;
      boolean flag = false;
      Stack stack = new Stack();

      for(int j = 0; j < p_b_0_.length(); ++j) {
         char c0 = p_b_0_.charAt(j);
         if(c0 == 34) {
            if(b(p_b_0_, j)) {
               if(!flag) {
                  throw new MojangsonParseException("Illegal use of \\\": " + p_b_0_);
               }
            } else {
               flag = !flag;
            }
         } else if(!flag) {
            if(c0 != 123 && c0 != 91) {
               if(c0 == 125 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 123)) {
                  throw new MojangsonParseException("Unbalanced curly brackets {}: " + p_b_0_);
               }

               if(c0 == 93 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 91)) {
                  throw new MojangsonParseException("Unbalanced square brackets []: " + p_b_0_);
               }
            } else {
               if(stack.isEmpty()) {
                  ++i;
               }

               stack.push(Character.valueOf(c0));
            }
         }
      }

      if(flag) {
         throw new MojangsonParseException("Unbalanced quotation: " + p_b_0_);
      } else if(!stack.isEmpty()) {
         throw new MojangsonParseException("Unbalanced brackets: " + p_b_0_);
      } else {
         if(i == 0 && !p_b_0_.isEmpty()) {
            i = 1;
         }

         return i;
      }
   }

   static MojangsonParser.MojangsonTypeParser a(String... p_a_0_) throws MojangsonParseException {
      return a(p_a_0_[0], p_a_0_[1]);
   }

   static MojangsonParser.MojangsonTypeParser a(String p_a_0_, String p_a_1_) throws MojangsonParseException {
      p_a_1_ = p_a_1_.trim();
      if(p_a_1_.startsWith("{")) {
         p_a_1_ = p_a_1_.substring(1, p_a_1_.length() - 1);

         MojangsonParser.MojangsonCompoundParser mojangsonparser$mojangsoncompoundparser;
         String s1;
         for(mojangsonparser$mojangsoncompoundparser = new MojangsonParser.MojangsonCompoundParser(p_a_0_); p_a_1_.length() > 0; p_a_1_ = p_a_1_.substring(s1.length() + 1)) {
            s1 = b(p_a_1_, true);
            if(s1.length() > 0) {
               boolean flag1 = false;
               mojangsonparser$mojangsoncompoundparser.b.add(a(s1, flag1));
            }

            if(p_a_1_.length() < s1.length() + 1) {
               break;
            }

            char c1 = p_a_1_.charAt(s1.length());
            if(c1 != 44 && c1 != 123 && c1 != 125 && c1 != 91 && c1 != 93) {
               throw new MojangsonParseException("Unexpected token \'" + c1 + "\' at: " + p_a_1_.substring(s1.length()));
            }
         }

         return mojangsonparser$mojangsoncompoundparser;
      } else if(p_a_1_.startsWith("[") && !b.matcher(p_a_1_).matches()) {
         p_a_1_ = p_a_1_.substring(1, p_a_1_.length() - 1);

         MojangsonParser.MojangsonListParser mojangsonparser$mojangsonlistparser;
         String s;
         for(mojangsonparser$mojangsonlistparser = new MojangsonParser.MojangsonListParser(p_a_0_); p_a_1_.length() > 0; p_a_1_ = p_a_1_.substring(s.length() + 1)) {
            s = b(p_a_1_, false);
            if(s.length() > 0) {
               boolean flag = true;
               mojangsonparser$mojangsonlistparser.b.add(a(s, flag));
            }

            if(p_a_1_.length() < s.length() + 1) {
               break;
            }

            char c0 = p_a_1_.charAt(s.length());
            if(c0 != 44 && c0 != 123 && c0 != 125 && c0 != 91 && c0 != 93) {
               throw new MojangsonParseException("Unexpected token \'" + c0 + "\' at: " + p_a_1_.substring(s.length()));
            }
         }

         return mojangsonparser$mojangsonlistparser;
      } else {
         return new MojangsonParser.MojangsonPrimitiveParser(p_a_0_, p_a_1_);
      }
   }

   private static MojangsonParser.MojangsonTypeParser a(String p_a_0_, boolean p_a_1_) throws MojangsonParseException {
      String s = c(p_a_0_, p_a_1_);
      String s1 = d(p_a_0_, p_a_1_);
      return a(new String[]{s, s1});
   }

   private static String b(String p_b_0_, boolean p_b_1_) throws MojangsonParseException {
      int i = a(p_b_0_, ':');
      int j = a(p_b_0_, ',');
      if(p_b_1_) {
         if(i == -1) {
            throw new MojangsonParseException("Unable to locate name/value separator for string: " + p_b_0_);
         }

         if(j != -1 && j < i) {
            throw new MojangsonParseException("Name error at: " + p_b_0_);
         }
      } else if(i == -1 || i > j) {
         i = -1;
      }

      return a(p_b_0_, i);
   }

   private static String a(String p_a_0_, int p_a_1_) throws MojangsonParseException {
      Stack stack = new Stack();
      int i = p_a_1_ + 1;
      boolean flag = false;
      boolean flag1 = false;
      boolean flag2 = false;

      for(int j = 0; i < p_a_0_.length(); ++i) {
         char c0 = p_a_0_.charAt(i);
         if(c0 == 34) {
            if(b(p_a_0_, i)) {
               if(!flag) {
                  throw new MojangsonParseException("Illegal use of \\\": " + p_a_0_);
               }
            } else {
               flag = !flag;
               if(flag && !flag2) {
                  flag1 = true;
               }

               if(!flag) {
                  j = i;
               }
            }
         } else if(!flag) {
            if(c0 != 123 && c0 != 91) {
               if(c0 == 125 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 123)) {
                  throw new MojangsonParseException("Unbalanced curly brackets {}: " + p_a_0_);
               }

               if(c0 == 93 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 91)) {
                  throw new MojangsonParseException("Unbalanced square brackets []: " + p_a_0_);
               }

               if(c0 == 44 && stack.isEmpty()) {
                  return p_a_0_.substring(0, i);
               }
            } else {
               stack.push(Character.valueOf(c0));
            }
         }

         if(!Character.isWhitespace(c0)) {
            if(!flag && flag1 && j != i) {
               return p_a_0_.substring(0, j + 1);
            }

            flag2 = true;
         }
      }

      return p_a_0_.substring(0, i);
   }

   private static String c(String p_c_0_, boolean p_c_1_) throws MojangsonParseException {
      if(p_c_1_) {
         p_c_0_ = p_c_0_.trim();
         if(p_c_0_.startsWith("{") || p_c_0_.startsWith("[")) {
            return "";
         }
      }

      int i = a(p_c_0_, ':');
      if(i == -1) {
         if(p_c_1_) {
            return "";
         } else {
            throw new MojangsonParseException("Unable to locate name/value separator for string: " + p_c_0_);
         }
      } else {
         return p_c_0_.substring(0, i).trim();
      }
   }

   private static String d(String p_d_0_, boolean p_d_1_) throws MojangsonParseException {
      if(p_d_1_) {
         p_d_0_ = p_d_0_.trim();
         if(p_d_0_.startsWith("{") || p_d_0_.startsWith("[")) {
            return p_d_0_;
         }
      }

      int i = a(p_d_0_, ':');
      if(i == -1) {
         if(p_d_1_) {
            return p_d_0_;
         } else {
            throw new MojangsonParseException("Unable to locate name/value separator for string: " + p_d_0_);
         }
      } else {
         return p_d_0_.substring(i + 1).trim();
      }
   }

   private static int a(String p_a_0_, char p_a_1_) {
      int i = 0;

      for(boolean flag = true; i < p_a_0_.length(); ++i) {
         char c0 = p_a_0_.charAt(i);
         if(c0 == 34) {
            if(!b(p_a_0_, i)) {
               flag = !flag;
            }
         } else if(flag) {
            if(c0 == p_a_1_) {
               return i;
            }

            if(c0 == 123 || c0 == 91) {
               return -1;
            }
         }
      }

      return -1;
   }

   private static boolean b(String p_b_0_, int p_b_1_) {
      return p_b_1_ > 0 && p_b_0_.charAt(p_b_1_ - 1) == 92 && !b(p_b_0_, p_b_1_ - 1);
   }

   static class MojangsonCompoundParser extends MojangsonParser.MojangsonTypeParser {
      protected List<MojangsonParser.MojangsonTypeParser> b = Lists.<MojangsonParser.MojangsonTypeParser>newArrayList();

      public MojangsonCompoundParser(String p_i919_1_) {
         this.a = p_i919_1_;
      }

      public NBTBase a() throws MojangsonParseException {
         NBTTagCompound nbttagcompound = new NBTTagCompound();

         for(MojangsonParser.MojangsonTypeParser mojangsonparser$mojangsontypeparser : this.b) {
            nbttagcompound.set(mojangsonparser$mojangsontypeparser.a, mojangsonparser$mojangsontypeparser.a());
         }

         return nbttagcompound;
      }
   }

   static class MojangsonListParser extends MojangsonParser.MojangsonTypeParser {
      protected List<MojangsonParser.MojangsonTypeParser> b = Lists.<MojangsonParser.MojangsonTypeParser>newArrayList();

      public MojangsonListParser(String p_i920_1_) {
         this.a = p_i920_1_;
      }

      public NBTBase a() throws MojangsonParseException {
         NBTTagList nbttaglist = new NBTTagList();

         for(MojangsonParser.MojangsonTypeParser mojangsonparser$mojangsontypeparser : this.b) {
            nbttaglist.add(mojangsonparser$mojangsontypeparser.a());
         }

         return nbttaglist;
      }
   }

   static class MojangsonPrimitiveParser extends MojangsonParser.MojangsonTypeParser {
      private static final Pattern c = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
      private static final Pattern d = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
      private static final Pattern e = Pattern.compile("[-+]?[0-9]+[b|B]");
      private static final Pattern f = Pattern.compile("[-+]?[0-9]+[l|L]");
      private static final Pattern g = Pattern.compile("[-+]?[0-9]+[s|S]");
      private static final Pattern h = Pattern.compile("[-+]?[0-9]+");
      private static final Pattern i = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
      private static final Splitter j = Splitter.on(',').omitEmptyStrings();
      protected String b;

      public MojangsonPrimitiveParser(String p_i921_1_, String p_i921_2_) {
         this.a = p_i921_1_;
         this.b = p_i921_2_;
      }

      public NBTBase a() throws MojangsonParseException {
         try {
            if(c.matcher(this.b).matches()) {
               return new NBTTagDouble(Double.parseDouble(this.b.substring(0, this.b.length() - 1)));
            }

            if(d.matcher(this.b).matches()) {
               return new NBTTagFloat(Float.parseFloat(this.b.substring(0, this.b.length() - 1)));
            }

            if(e.matcher(this.b).matches()) {
               return new NBTTagByte(Byte.parseByte(this.b.substring(0, this.b.length() - 1)));
            }

            if(f.matcher(this.b).matches()) {
               return new NBTTagLong(Long.parseLong(this.b.substring(0, this.b.length() - 1)));
            }

            if(g.matcher(this.b).matches()) {
               return new NBTTagShort(Short.parseShort(this.b.substring(0, this.b.length() - 1)));
            }

            if(h.matcher(this.b).matches()) {
               return new NBTTagInt(Integer.parseInt(this.b));
            }

            if(i.matcher(this.b).matches()) {
               return new NBTTagDouble(Double.parseDouble(this.b));
            }

            if(this.b.equalsIgnoreCase("true") || this.b.equalsIgnoreCase("false")) {
               return new NBTTagByte((byte)(Boolean.parseBoolean(this.b)?1:0));
            }
         } catch (NumberFormatException var6) {
            this.b = this.b.replaceAll("\\\\\"", "\"");
            return new NBTTagString(this.b);
         }

         if(this.b.startsWith("[") && this.b.endsWith("]")) {
            String s = this.b.substring(1, this.b.length() - 1);
            String[] astring = (String[])Iterables.toArray(j.split(s), String.class);

            try {
               int[] aint = new int[astring.length];

               for(int jx = 0; jx < astring.length; ++jx) {
                  aint[jx] = Integer.parseInt(astring[jx].trim());
               }

               return new NBTTagIntArray(aint);
            } catch (NumberFormatException var5) {
               return new NBTTagString(this.b);
            }
         } else {
            if(this.b.startsWith("\"") && this.b.endsWith("\"")) {
               this.b = this.b.substring(1, this.b.length() - 1);
            }

            this.b = this.b.replaceAll("\\\\\"", "\"");
            StringBuilder stringbuilder = new StringBuilder();

            for(int ix = 0; ix < this.b.length(); ++ix) {
               if(ix < this.b.length() - 1 && this.b.charAt(ix) == 92 && this.b.charAt(ix + 1) == 92) {
                  stringbuilder.append('\\');
                  ++ix;
               } else {
                  stringbuilder.append(this.b.charAt(ix));
               }
            }

            return new NBTTagString(stringbuilder.toString());
         }
      }
   }

   abstract static class MojangsonTypeParser {
      protected String a;

      public abstract NBTBase a() throws MojangsonParseException;
   }
}
