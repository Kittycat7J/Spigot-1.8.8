package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.minecraft.server.v1_8_R3.JsonListEntry;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;

public class JsonList<K, V extends JsonListEntry<K>> {
   protected static final Logger a = LogManager.getLogger();
   protected final Gson b;
   private final File c;
   private final Map<String, V> d = Maps.<String, V>newHashMap();
   private boolean e = true;
   private static final ParameterizedType f = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{JsonListEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };

   public JsonList(File p_i275_1_) {
      this.c = p_i275_1_;
      GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
      gsonbuilder.registerTypeHierarchyAdapter(JsonListEntry.class, new JsonList.JsonListEntrySerializer((Object)null));
      this.b = gsonbuilder.create();
   }

   public boolean isEnabled() {
      return this.e;
   }

   public void a(boolean p_a_1_) {
      this.e = p_a_1_;
   }

   public File c() {
      return this.c;
   }

   public void add(V p_add_1_) {
      this.d.put(this.a(p_add_1_.getKey()), p_add_1_);

      try {
         this.save();
      } catch (IOException ioexception) {
         a.warn((String)"Could not save the list after adding a user.", (Throwable)ioexception);
      }

   }

   public V get(K p_get_1_) {
      this.h();
      return (V)((JsonListEntry)this.d.get(this.a(p_get_1_)));
   }

   public void remove(K p_remove_1_) {
      this.d.remove(this.a(p_remove_1_));

      try {
         this.save();
      } catch (IOException ioexception) {
         a.warn((String)"Could not save the list after removing a user.", (Throwable)ioexception);
      }

   }

   public String[] getEntries() {
      return (String[])this.d.keySet().toArray(new String[this.d.size()]);
   }

   public Collection<V> getValues() {
      return this.d.values();
   }

   public boolean isEmpty() {
      return this.d.size() < 1;
   }

   protected String a(K p_a_1_) {
      return p_a_1_.toString();
   }

   protected boolean d(K p_d_1_) {
      return this.d.containsKey(this.a(p_d_1_));
   }

   private void h() {
      ArrayList arraylist = Lists.newArrayList();

      for(JsonListEntry jsonlistentry : this.d.values()) {
         if(jsonlistentry.hasExpired()) {
            arraylist.add(jsonlistentry.getKey());
         }
      }

      for(Object object : arraylist) {
         this.d.remove(object);
      }

   }

   protected JsonListEntry<K> a(JsonObject p_a_1_) {
      return new JsonListEntry((Object)null, p_a_1_);
   }

   protected Map<String, V> e() {
      return this.d;
   }

   public void save() throws IOException {
      Collection collection = this.d.values();
      String s = this.b.toJson((Object)collection);
      BufferedWriter bufferedwriter = null;

      try {
         bufferedwriter = Files.newWriter(this.c, Charsets.UTF_8);
         bufferedwriter.write(s);
      } finally {
         IOUtils.closeQuietly((Writer)bufferedwriter);
      }

   }

   public void load() throws FileNotFoundException {
      Collection collection = null;
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = Files.newReader(this.c, Charsets.UTF_8);
         collection = (Collection)this.b.fromJson((Reader)bufferedreader, f);
      } catch (FileNotFoundException var8) {
         Bukkit.getLogger().log(Level.INFO, "Unable to find file {0}, creating it.", this.c);
      } catch (JsonSyntaxException var9) {
         Bukkit.getLogger().log(Level.WARNING, "Unable to read file {0}, backing it up to {0}.backup and creating new copy.", this.c);
         File file1 = new File(this.c + ".backup");
         this.c.renameTo(file1);
         this.c.delete();
      } finally {
         IOUtils.closeQuietly((Reader)bufferedreader);
      }

      if(collection != null) {
         this.d.clear();

         for(JsonListEntry jsonlistentry : collection) {
            if(jsonlistentry.getKey() != null) {
               this.d.put(this.a(jsonlistentry.getKey()), jsonlistentry);
            }
         }
      }

   }

   class JsonListEntrySerializer implements JsonDeserializer<JsonListEntry<K>>, JsonSerializer<JsonListEntry<K>> {
      private JsonListEntrySerializer() {
      }

      public JsonElement a(JsonListEntry<K> p_a_1_, Type p_a_2_, JsonSerializationContext p_a_3_) {
         JsonObject jsonobject = new JsonObject();
         p_a_1_.a(jsonobject);
         return jsonobject;
      }

      public JsonListEntry<K> a(JsonElement p_a_1_, Type p_a_2_, JsonDeserializationContext p_a_3_) throws JsonParseException {
         if(p_a_1_.isJsonObject()) {
            JsonObject jsonobject = p_a_1_.getAsJsonObject();
            JsonListEntry jsonlistentry = JsonList.this.a(jsonobject);
            return jsonlistentry;
         } else {
            return null;
         }
      }

      public JsonElement serialize(JsonListEntry<K> p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         return this.a(p_serialize_1_, p_serialize_2_, p_serialize_3_);
      }

      public JsonListEntry<K> deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         return this.a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
      }

      JsonListEntrySerializer(Object p_i215_2_) {
         this();
      }
   }
}
