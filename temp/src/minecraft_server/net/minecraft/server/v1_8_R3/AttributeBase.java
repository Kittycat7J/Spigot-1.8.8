package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IAttribute;

public abstract class AttributeBase implements IAttribute {
   private final IAttribute a;
   private final String b;
   private final double c;
   private boolean d;

   protected AttributeBase(IAttribute p_i1152_1_, String p_i1152_2_, double p_i1152_3_) {
      this.a = p_i1152_1_;
      this.b = p_i1152_2_;
      this.c = p_i1152_3_;
      if(p_i1152_2_ == null) {
         throw new IllegalArgumentException("Name cannot be null!");
      }
   }

   public String getName() {
      return this.b;
   }

   public double b() {
      return this.c;
   }

   public boolean c() {
      return this.d;
   }

   public AttributeBase a(boolean p_a_1_) {
      this.d = p_a_1_;
      return this;
   }

   public IAttribute d() {
      return this.a;
   }

   public int hashCode() {
      return this.b.hashCode();
   }

   public boolean equals(Object p_equals_1_) {
      return p_equals_1_ instanceof IAttribute && this.b.equals(((IAttribute)p_equals_1_).getName());
   }
}
