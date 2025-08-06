package net.minecraft.server.v1_8_R3;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.ShapeDetector;
import net.minecraft.server.v1_8_R3.ShapeDetectorBlock;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ShapeDetectorBuilder {
   private static final Joiner a = Joiner.on(",");
   private final List<String[]> b = Lists.<String[]>newArrayList();
   private final Map<Character, Predicate<ShapeDetectorBlock>> c = Maps.<Character, Predicate<ShapeDetectorBlock>>newHashMap();
   private int d;
   private int e;

   private ShapeDetectorBuilder() {
      this.c.put(Character.valueOf(' '), Predicates.alwaysTrue());
   }

   public ShapeDetectorBuilder a(String... p_a_1_) {
      if(!ArrayUtils.isEmpty((Object[])p_a_1_) && !StringUtils.isEmpty(p_a_1_[0])) {
         if(this.b.isEmpty()) {
            this.d = p_a_1_.length;
            this.e = p_a_1_[0].length();
         }

         if(p_a_1_.length != this.d) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.d + ", but was given one with a height of " + p_a_1_.length + ")");
         } else {
            for(String s : p_a_1_) {
               if(s.length() != this.e) {
                  throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.e + ", found one with " + s.length() + ")");
               }

               for(char c0 : s.toCharArray()) {
                  if(!this.c.containsKey(Character.valueOf(c0))) {
                     this.c.put(Character.valueOf(c0), (Object)null);
                  }
               }
            }

            this.b.add(p_a_1_);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static ShapeDetectorBuilder a() {
      return new ShapeDetectorBuilder();
   }

   public ShapeDetectorBuilder a(char p_a_1_, Predicate<ShapeDetectorBlock> p_a_2_) {
      this.c.put(Character.valueOf(p_a_1_), p_a_2_);
      return this;
   }

   public ShapeDetector b() {
      return new ShapeDetector(this.c());
   }

   private Predicate<ShapeDetectorBlock>[][][] c() {
      this.d();
      Predicate[][][] apredicate = (Predicate[][][])((Predicate[][][])Array.newInstance(Predicate.class, new int[]{this.b.size(), this.d, this.e}));

      for(int i = 0; i < this.b.size(); ++i) {
         for(int j = 0; j < this.d; ++j) {
            for(int k = 0; k < this.e; ++k) {
               apredicate[i][j][k] = (Predicate)this.c.get(Character.valueOf(((String[])this.b.get(i))[j].charAt(k)));
            }
         }
      }

      return apredicate;
   }

   private void d() {
      ArrayList arraylist = Lists.newArrayList();

      for(Entry entry : this.c.entrySet()) {
         if(entry.getValue() == null) {
            arraylist.add(entry.getKey());
         }
      }

      if(!arraylist.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + a.join(arraylist) + " are missing");
      }
   }
}
