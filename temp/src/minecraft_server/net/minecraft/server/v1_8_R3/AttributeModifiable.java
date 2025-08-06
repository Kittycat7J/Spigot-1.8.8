package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeMapBase;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.IAttribute;

public class AttributeModifiable implements AttributeInstance {
   private final AttributeMapBase a;
   private final IAttribute b;
   private final Map<Integer, Set<AttributeModifier>> c = Maps.<Integer, Set<AttributeModifier>>newHashMap();
   private final Map<String, Set<AttributeModifier>> d = Maps.<String, Set<AttributeModifier>>newHashMap();
   private final Map<UUID, AttributeModifier> e = Maps.<UUID, AttributeModifier>newHashMap();
   private double f;
   private boolean g = true;
   private double h;

   public AttributeModifiable(AttributeMapBase p_i1153_1_, IAttribute p_i1153_2_) {
      this.a = p_i1153_1_;
      this.b = p_i1153_2_;
      this.f = p_i1153_2_.b();

      for(int i = 0; i < 3; ++i) {
         this.c.put(Integer.valueOf(i), Sets.newHashSet());
      }

   }

   public IAttribute getAttribute() {
      return this.b;
   }

   public double b() {
      return this.f;
   }

   public void setValue(double p_setValue_1_) {
      if(p_setValue_1_ != this.b()) {
         this.f = p_setValue_1_;
         this.f();
      }
   }

   public Collection<AttributeModifier> a(int p_a_1_) {
      return (Collection)this.c.get(Integer.valueOf(p_a_1_));
   }

   public Collection<AttributeModifier> c() {
      HashSet hashset = Sets.newHashSet();

      for(int i = 0; i < 3; ++i) {
         hashset.addAll(this.a(i));
      }

      return hashset;
   }

   public AttributeModifier a(UUID p_a_1_) {
      return (AttributeModifier)this.e.get(p_a_1_);
   }

   public boolean a(AttributeModifier p_a_1_) {
      return this.e.get(p_a_1_.a()) != null;
   }

   public void b(AttributeModifier p_b_1_) {
      if(this.a(p_b_1_.a()) != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         Object object = (Set)this.d.get(p_b_1_.b());
         if(object == null) {
            object = Sets.newHashSet();
            this.d.put(p_b_1_.b(), object);
         }

         ((Set)this.c.get(Integer.valueOf(p_b_1_.c()))).add(p_b_1_);
         ((Set)object).add(p_b_1_);
         this.e.put(p_b_1_.a(), p_b_1_);
         this.f();
      }
   }

   protected void f() {
      this.g = true;
      this.a.a((AttributeInstance)this);
   }

   public void c(AttributeModifier p_c_1_) {
      for(int i = 0; i < 3; ++i) {
         Set set = (Set)this.c.get(Integer.valueOf(i));
         set.remove(p_c_1_);
      }

      Set set1 = (Set)this.d.get(p_c_1_.b());
      if(set1 != null) {
         set1.remove(p_c_1_);
         if(set1.isEmpty()) {
            this.d.remove(p_c_1_.b());
         }
      }

      this.e.remove(p_c_1_.a());
      this.f();
   }

   public double getValue() {
      if(this.g) {
         this.h = this.g();
         this.g = false;
      }

      return this.h;
   }

   private double g() {
      double d0 = this.b();

      for(AttributeModifier attributemodifier : this.b(0)) {
         d0 += attributemodifier.d();
      }

      double d1 = d0;

      for(AttributeModifier attributemodifier1 : this.b(1)) {
         d1 += d0 * attributemodifier1.d();
      }

      for(AttributeModifier attributemodifier2 : this.b(2)) {
         d1 *= 1.0D + attributemodifier2.d();
      }

      return this.b.a(d1);
   }

   private Collection<AttributeModifier> b(int p_b_1_) {
      HashSet hashset = Sets.newHashSet(this.a(p_b_1_));

      for(IAttribute iattribute = this.b.d(); iattribute != null; iattribute = iattribute.d()) {
         AttributeInstance attributeinstance = this.a.a(iattribute);
         if(attributeinstance != null) {
            hashset.addAll(attributeinstance.a(p_b_1_));
         }
      }

      return hashset;
   }
}
