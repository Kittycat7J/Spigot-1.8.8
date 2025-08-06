package net.minecraft.server.v1_8_R3;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.InsensitiveStringMap;

public abstract class AttributeMapBase {
   protected final Map<IAttribute, AttributeInstance> a = Maps.<IAttribute, AttributeInstance>newHashMap();
   protected final Map<String, AttributeInstance> b = new InsensitiveStringMap();
   protected final Multimap<IAttribute, IAttribute> c = HashMultimap.<IAttribute, IAttribute>create();

   public AttributeInstance a(IAttribute p_a_1_) {
      return (AttributeInstance)this.a.get(p_a_1_);
   }

   public AttributeInstance a(String p_a_1_) {
      return (AttributeInstance)this.b.get(p_a_1_);
   }

   public AttributeInstance b(IAttribute p_b_1_) {
      if(this.b.containsKey(p_b_1_.getName())) {
         throw new IllegalArgumentException("Attribute is already registered!");
      } else {
         AttributeInstance attributeinstance = this.c(p_b_1_);
         this.b.put(p_b_1_.getName(), attributeinstance);
         this.a.put(p_b_1_, attributeinstance);

         for(IAttribute iattribute = p_b_1_.d(); iattribute != null; iattribute = iattribute.d()) {
            this.c.put(iattribute, p_b_1_);
         }

         return attributeinstance;
      }
   }

   protected abstract AttributeInstance c(IAttribute var1);

   public Collection<AttributeInstance> a() {
      return this.b.values();
   }

   public void a(AttributeInstance p_a_1_) {
   }

   public void a(Multimap<String, AttributeModifier> p_a_1_) {
      for(Entry entry : p_a_1_.entries()) {
         AttributeInstance attributeinstance = this.a((String)entry.getKey());
         if(attributeinstance != null) {
            attributeinstance.c((AttributeModifier)entry.getValue());
         }
      }

   }

   public void b(Multimap<String, AttributeModifier> p_b_1_) {
      for(Entry entry : p_b_1_.entries()) {
         AttributeInstance attributeinstance = this.a((String)entry.getKey());
         if(attributeinstance != null) {
            attributeinstance.c((AttributeModifier)entry.getValue());
            attributeinstance.b((AttributeModifier)entry.getValue());
         }
      }

   }
}
