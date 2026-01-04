package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;

public class JsonListEntry<T> {
   private final T a;

   public JsonListEntry(T p_i1085_1_) {
      this.a = p_i1085_1_;
   }

   protected JsonListEntry(T p_i1086_1_, JsonObject p_i1086_2_) {
      this.a = p_i1086_1_;
   }

   public T getKey() {
      return this.a;
   }

   boolean hasExpired() {
      return false;
   }

   protected void a(JsonObject p_a_1_) {
   }
}
