package net.minecraft.server.v1_8_R3;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class LocaleLanguage {
   private static final Pattern a = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   private static final Splitter b = Splitter.on('=').limit(2);
   private static LocaleLanguage c = new LocaleLanguage();
   private final Map<String, String> d = Maps.<String, String>newHashMap();
   private long e;

   public LocaleLanguage() {
      try {
         InputStream inputstream = LocaleLanguage.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");

         for(String s : IOUtils.readLines(inputstream, Charsets.UTF_8)) {
            if(!s.isEmpty() && s.charAt(0) != 35) {
               String[] astring = (String[])Iterables.toArray(b.split(s), String.class);
               if(astring != null && astring.length == 2) {
                  String s1 = astring[0];
                  String s2 = a.matcher(astring[1]).replaceAll("%$1s");
                  this.d.put(s1, s2);
               }
            }
         }

         this.e = System.currentTimeMillis();
      } catch (IOException var7) {
         ;
      }

   }

   static LocaleLanguage a() {
      return c;
   }

   public synchronized String a(String p_a_1_) {
      return this.c(p_a_1_);
   }

   public synchronized String a(String p_a_1_, Object... p_a_2_) {
      String s = this.c(p_a_1_);

      try {
         return String.format(s, p_a_2_);
      } catch (IllegalFormatException var5) {
         return "Format error: " + s;
      }
   }

   private String c(String p_c_1_) {
      String s = (String)this.d.get(p_c_1_);
      return s == null?p_c_1_:s;
   }

   public synchronized boolean b(String p_b_1_) {
      return this.d.containsKey(p_b_1_);
   }

   public long c() {
      return this.e;
   }
}
