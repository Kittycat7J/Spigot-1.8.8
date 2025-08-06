package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterators;
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
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.JsonList;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.apache.commons.io.IOUtils;
import org.spigotmc.SpigotConfig;

public class UserCache {
   public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   private final Map<String, UserCache.UserCacheEntry> c = Maps.<String, UserCache.UserCacheEntry>newHashMap();
   private final Map<UUID, UserCache.UserCacheEntry> d = Maps.<UUID, UserCache.UserCacheEntry>newHashMap();
   private final Deque<GameProfile> e = new LinkedBlockingDeque();
   private final MinecraftServer f;
   protected final Gson b;
   private final File g;
   private static final ParameterizedType h = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{UserCache.UserCacheEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };

   public UserCache(MinecraftServer p_i61_1_, File p_i61_2_) {
      this.f = p_i61_1_;
      this.g = p_i61_2_;
      GsonBuilder gsonbuilder = new GsonBuilder();
      gsonbuilder.registerTypeHierarchyAdapter(UserCache.UserCacheEntry.class, new UserCache.BanEntrySerializer((Object)null));
      this.b = gsonbuilder.create();
      this.b();
   }

   private static GameProfile a(MinecraftServer p_a_0_, String p_a_1_) {
      final GameProfile[] agameprofile = new GameProfile[1];
      ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
         public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
            agameprofile[0] = p_onProfileLookupSucceeded_1_;
         }

         public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
            agameprofile[0] = null;
         }
      };
      p_a_0_.getGameProfileRepository().findProfilesByNames(new String[]{p_a_1_}, Agent.MINECRAFT, profilelookupcallback);
      if(!p_a_0_.getOnlineMode() && agameprofile[0] == null) {
         UUID uuid = EntityHuman.a(new GameProfile((UUID)null, p_a_1_));
         GameProfile gameprofile = new GameProfile(uuid, p_a_1_);
         profilelookupcallback.onProfileLookupSucceeded(gameprofile);
      }

      return agameprofile[0];
   }

   public void a(GameProfile p_a_1_) {
      this.a((GameProfile)p_a_1_, (Date)null);
   }

   private void a(GameProfile p_a_1_, Date p_a_2_) {
      UUID uuid = p_a_1_.getId();
      if(p_a_2_ == null) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(new Date());
         calendar.add(2, 1);
         p_a_2_ = calendar.getTime();
      }

      p_a_1_.getName().toLowerCase(Locale.ROOT);
      UserCache.UserCacheEntry usercache$usercacheentry1 = new UserCache.UserCacheEntry(p_a_1_, p_a_2_, (Object)null);
      if(this.d.containsKey(uuid)) {
         UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.d.get(uuid);
         this.c.remove(usercache$usercacheentry.a().getName().toLowerCase(Locale.ROOT));
         this.e.remove(p_a_1_);
      }

      this.c.put(p_a_1_.getName().toLowerCase(Locale.ROOT), usercache$usercacheentry1);
      this.d.put(uuid, usercache$usercacheentry1);
      this.e.addFirst(p_a_1_);
      if(!SpigotConfig.saveUserCacheOnStopOnly) {
         this.c();
      }

   }

   public GameProfile getProfile(String p_getProfile_1_) {
      String s = p_getProfile_1_.toLowerCase(Locale.ROOT);
      UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.c.get(s);
      if(usercache$usercacheentry != null && (new Date()).getTime() >= usercache$usercacheentry.c.getTime()) {
         this.d.remove(usercache$usercacheentry.a().getId());
         this.c.remove(usercache$usercacheentry.a().getName().toLowerCase(Locale.ROOT));
         this.e.remove(usercache$usercacheentry.a());
         usercache$usercacheentry = null;
      }

      if(usercache$usercacheentry != null) {
         GameProfile gameprofile = usercache$usercacheentry.a();
         this.e.remove(gameprofile);
         this.e.addFirst(gameprofile);
      } else {
         GameProfile gameprofile1 = a(this.f, p_getProfile_1_);
         if(gameprofile1 != null) {
            this.a(gameprofile1);
            usercache$usercacheentry = (UserCache.UserCacheEntry)this.c.get(s);
         }
      }

      if(!SpigotConfig.saveUserCacheOnStopOnly) {
         this.c();
      }

      return usercache$usercacheentry == null?null:usercache$usercacheentry.a();
   }

   public String[] a() {
      ArrayList arraylist = Lists.newArrayList(this.c.keySet());
      return (String[])arraylist.toArray(new String[arraylist.size()]);
   }

   public GameProfile a(UUID p_a_1_) {
      UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.d.get(p_a_1_);
      return usercache$usercacheentry == null?null:usercache$usercacheentry.a();
   }

   private UserCache.UserCacheEntry b(UUID p_b_1_) {
      UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.d.get(p_b_1_);
      if(usercache$usercacheentry != null) {
         GameProfile gameprofile = usercache$usercacheentry.a();
         this.e.remove(gameprofile);
         this.e.addFirst(gameprofile);
      }

      return usercache$usercacheentry;
   }

   public void b() {
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = Files.newReader(this.g, Charsets.UTF_8);
         List list = (List)this.b.fromJson((Reader)bufferedreader, h);
         this.c.clear();
         this.d.clear();
         this.e.clear();

         for(UserCache.UserCacheEntry usercache$usercacheentry : Lists.reverse(list)) {
            if(usercache$usercacheentry != null) {
               this.a(usercache$usercacheentry.a(), usercache$usercacheentry.b());
            }
         }
      } catch (FileNotFoundException var10) {
         ;
      } catch (JsonSyntaxException var11) {
         JsonList.a.warn("Usercache.json is corrupted or has bad formatting. Deleting it to prevent further issues.");
         this.g.delete();
      } catch (JsonParseException var12) {
         ;
      } finally {
         IOUtils.closeQuietly((Reader)bufferedreader);
      }

   }

   public void c() {
      String s = this.b.toJson((Object)this.a(SpigotConfig.userCacheCap));
      BufferedWriter bufferedwriter = null;

      try {
         bufferedwriter = Files.newWriter(this.g, Charsets.UTF_8);
         bufferedwriter.write(s);
         return;
      } catch (FileNotFoundException var7) {
         ;
      } catch (IOException var8) {
         return;
      } finally {
         IOUtils.closeQuietly((Writer)bufferedwriter);
      }

   }

   private List<UserCache.UserCacheEntry> a(int p_a_1_) {
      ArrayList arraylist = Lists.newArrayList();

      for(GameProfile gameprofile : Lists.newArrayList(Iterators.limit(this.e.iterator(), p_a_1_))) {
         UserCache.UserCacheEntry usercache$usercacheentry = this.b(gameprofile.getId());
         if(usercache$usercacheentry != null) {
            arraylist.add(usercache$usercacheentry);
         }
      }

      return arraylist;
   }

   class BanEntrySerializer implements JsonDeserializer<UserCache.UserCacheEntry>, JsonSerializer<UserCache.UserCacheEntry> {
      private BanEntrySerializer() {
      }

      public JsonElement a(UserCache.UserCacheEntry p_a_1_, Type p_a_2_, JsonSerializationContext p_a_3_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("name", p_a_1_.a().getName());
         UUID uuid = p_a_1_.a().getId();
         jsonobject.addProperty("uuid", uuid == null?"":uuid.toString());
         jsonobject.addProperty("expiresOn", UserCache.a.format(p_a_1_.b()));
         return jsonobject;
      }

      public UserCache.UserCacheEntry a(JsonElement p_a_1_, Type p_a_2_, JsonDeserializationContext p_a_3_) throws JsonParseException {
         if(p_a_1_.isJsonObject()) {
            JsonObject jsonobject = p_a_1_.getAsJsonObject();
            JsonElement jsonelement = jsonobject.get("name");
            JsonElement jsonelement1 = jsonobject.get("uuid");
            JsonElement jsonelement2 = jsonobject.get("expiresOn");
            if(jsonelement != null && jsonelement1 != null) {
               String s = jsonelement1.getAsString();
               String s1 = jsonelement.getAsString();
               Date date = null;
               if(jsonelement2 != null) {
                  try {
                     date = UserCache.a.parse(jsonelement2.getAsString());
                  } catch (ParseException var14) {
                     date = null;
                  }
               }

               if(s1 != null && s != null) {
                  UUID uuid;
                  try {
                     uuid = UUID.fromString(s);
                  } catch (Throwable var13) {
                     return null;
                  }

                  UserCache usercache = UserCache.this;
                  UserCache.this.getClass();
                  UserCache.UserCacheEntry usercache$usercacheentry = usercache.new UserCacheEntry(new GameProfile(uuid, s1), date, (Object)null);
                  return usercache$usercacheentry;
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }

      public JsonElement serialize(UserCache.UserCacheEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         return this.a(p_serialize_1_, p_serialize_2_, p_serialize_3_);
      }

      public UserCache.UserCacheEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         return this.a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
      }

      BanEntrySerializer(Object p_i197_2_) {
         this();
      }
   }

   class UserCacheEntry {
      private final GameProfile b;
      private final Date c;

      private UserCacheEntry(GameProfile p_i489_2_, Date p_i489_3_) {
         this.b = p_i489_2_;
         this.c = p_i489_3_;
      }

      public GameProfile a() {
         return this.b;
      }

      public Date b() {
         return this.c;
      }

      UserCacheEntry(GameProfile p_i490_2_, Date p_i490_3_, Object p_i490_4_) {
         this(p_i490_2_, p_i490_3_);
      }
   }
}
