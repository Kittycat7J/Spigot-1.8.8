package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.server.v1_8_R3.JsonListEntry;

public abstract class ExpirableListEntry<T> extends JsonListEntry<T> {
   public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   protected final Date b;
   protected final String c;
   protected final Date d;
   protected final String e;

   public ExpirableListEntry(T p_i484_1_, Date p_i484_2_, String p_i484_3_, Date p_i484_4_, String p_i484_5_) {
      super(p_i484_1_);
      this.b = p_i484_2_ == null?new Date():p_i484_2_;
      this.c = p_i484_3_ == null?"(Unknown)":p_i484_3_;
      this.d = p_i484_4_;
      this.e = p_i484_5_ == null?"Banned by an operator.":p_i484_5_;
   }

   protected ExpirableListEntry(T p_i485_1_, JsonObject p_i485_2_) {
      super(checkExpiry(p_i485_1_, p_i485_2_), p_i485_2_);

      Date date;
      try {
         date = p_i485_2_.has("created")?a.parse(p_i485_2_.get("created").getAsString()):new Date();
      } catch (ParseException var6) {
         date = new Date();
      }

      this.b = date;
      this.c = p_i485_2_.has("source")?p_i485_2_.get("source").getAsString():"(Unknown)";

      Date date1;
      try {
         date1 = p_i485_2_.has("expires")?a.parse(p_i485_2_.get("expires").getAsString()):null;
      } catch (ParseException var5) {
         date1 = null;
      }

      this.d = date1;
      this.e = p_i485_2_.has("reason")?p_i485_2_.get("reason").getAsString():"Banned by an operator.";
   }

   public Date getExpires() {
      return this.d;
   }

   public String getReason() {
      return this.e;
   }

   boolean hasExpired() {
      return this.d == null?false:this.d.before(new Date());
   }

   protected void a(JsonObject p_a_1_) {
      p_a_1_.addProperty("created", a.format(this.b));
      p_a_1_.addProperty("source", this.c);
      p_a_1_.addProperty("expires", this.d == null?"forever":a.format(this.d));
      p_a_1_.addProperty("reason", this.e);
   }

   public String getSource() {
      return this.c;
   }

   public Date getCreated() {
      return this.b;
   }

   private static <T> T checkExpiry(T p_checkExpiry_0_, JsonObject p_checkExpiry_1_) {
      Date date = null;

      try {
         date = p_checkExpiry_1_.has("expires")?a.parse(p_checkExpiry_1_.get("expires").getAsString()):null;
      } catch (ParseException var3) {
         ;
      }

      return (T)(date != null && !date.after(new Date())?null:p_checkExpiry_0_);
   }
}
