package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.DedicatedServer;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ExpirableListEntry;
import net.minecraft.server.v1_8_R3.GameProfileBanEntry;
import net.minecraft.server.v1_8_R3.GameProfileBanList;
import net.minecraft.server.v1_8_R3.IpBanEntry;
import net.minecraft.server.v1_8_R3.IpBanList;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.OpList;
import net.minecraft.server.v1_8_R3.OpListEntry;
import net.minecraft.server.v1_8_R3.PlayerList;
import net.minecraft.server.v1_8_R3.PropertyManager;
import net.minecraft.server.v1_8_R3.UtilColor;
import net.minecraft.server.v1_8_R3.WhiteList;
import net.minecraft.server.v1_8_R3.WhiteListEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.SpigotConfig;

public class NameReferencingFileConverter {
   private static final Logger e = LogManager.getLogger();
   public static final File a = new File("banned-ips.txt");
   public static final File b = new File("banned-players.txt");
   public static final File c = new File("ops.txt");
   public static final File d = new File("white-list.txt");

   static List<String> a(File p_a_0_, Map<String, String[]> p_a_1_) throws IOException {
      List list = Files.readLines(p_a_0_, Charsets.UTF_8);

      for(String s : list) {
         s = s.trim();
         if(!s.startsWith("#") && s.length() >= 1) {
            String[] astring = s.split("\\|");
            p_a_1_.put(astring[0].toLowerCase(Locale.ROOT), astring);
         }
      }

      return list;
   }

   private static void a(MinecraftServer p_a_0_, Collection<String> p_a_1_, ProfileLookupCallback p_a_2_) {
      String[] astring = (String[])Iterators.toArray(Iterators.filter(p_a_1_.iterator(), new Predicate() {
         public boolean a(String p_a_1_) {
            return !UtilColor.b(p_a_1_);
         }

         public boolean apply(Object p_apply_1_) {
            return this.a((String)p_apply_1_);
         }
      }), String.class);
      if(!p_a_0_.getOnlineMode() && !SpigotConfig.bungee) {
         for(String s : astring) {
            UUID uuid = EntityHuman.a(new GameProfile((UUID)null, s));
            GameProfile gameprofile = new GameProfile(uuid, s);
            p_a_2_.onProfileLookupSucceeded(gameprofile);
         }
      } else {
         p_a_0_.getGameProfileRepository().findProfilesByNames(astring, Agent.MINECRAFT, p_a_2_);
      }

   }

   public static boolean a(final MinecraftServer p_a_0_) {
      final GameProfileBanList gameprofilebanlist = new GameProfileBanList(PlayerList.a);
      if(b.exists() && b.isFile()) {
         if(gameprofilebanlist.c().exists()) {
            try {
               gameprofilebanlist.load();
            } catch (IOException var6) {
               e.warn("Could not load existing file " + gameprofilebanlist.c().getName());
            }
         }

         try {
            final HashMap hashmap = Maps.newHashMap();
            a((File)b, hashmap);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
                  p_a_0_.getUserCache().a(p_onProfileLookupSucceeded_1_);
                  String[] astring = (String[])hashmap.get(p_onProfileLookupSucceeded_1_.getName().toLowerCase(Locale.ROOT));
                  if(astring == null) {
                     NameReferencingFileConverter.e.warn("Could not convert user banlist entry for " + p_onProfileLookupSucceeded_1_.getName());
                     throw new NameReferencingFileConverter.FileConversionException("Profile not in the conversionlist", (Throwable)null, (NameReferencingFileConverter.FileConversionException)null);
                  } else {
                     Date date = astring.length > 1?NameReferencingFileConverter.b(astring[1], (Date)null):null;
                     String s = astring.length > 2?astring[2]:null;
                     Date date1 = astring.length > 3?NameReferencingFileConverter.b(astring[3], (Date)null):null;
                     String s1 = astring.length > 4?astring[4]:null;
                     gameprofilebanlist.add(new GameProfileBanEntry(p_onProfileLookupSucceeded_1_, date, s, date1, s1));
                  }
               }

               public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
                  NameReferencingFileConverter.e.warn((String)("Could not lookup user banlist entry for " + p_onProfileLookupFailed_1_.getName()), (Throwable)p_onProfileLookupFailed_2_);
                  if(!(p_onProfileLookupFailed_2_ instanceof ProfileNotFoundException)) {
                     throw new NameReferencingFileConverter.FileConversionException("Could not request user " + p_onProfileLookupFailed_1_.getName() + " from backend systems", p_onProfileLookupFailed_2_, (Object)null);
                  }
               }
            };
            a(p_a_0_, hashmap.keySet(), profilelookupcallback);
            gameprofilebanlist.save();
            c(b);
            return true;
         } catch (IOException ioexception) {
            e.warn((String)"Could not read old user banlist to convert it!", (Throwable)ioexception);
            return false;
         } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
            e.error((String)"Conversion failed, please try again later", (Throwable)namereferencingfileconverter$fileconversionexception);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean b(MinecraftServer p_b_0_) {
      IpBanList ipbanlist = new IpBanList(PlayerList.b);
      if(a.exists() && a.isFile()) {
         if(ipbanlist.c().exists()) {
            try {
               ipbanlist.load();
            } catch (IOException var11) {
               e.warn("Could not load existing file " + ipbanlist.c().getName());
            }
         }

         try {
            HashMap hashmap = Maps.newHashMap();
            a((File)a, hashmap);

            for(String s : hashmap.keySet()) {
               String[] astring = (String[])hashmap.get(s);
               Date date = astring.length > 1?b(astring[1], (Date)null):null;
               String s1 = astring.length > 2?astring[2]:null;
               Date date1 = astring.length > 3?b(astring[3], (Date)null):null;
               String s2 = astring.length > 4?astring[4]:null;
               ipbanlist.add(new IpBanEntry(s, date, s1, date1, s2));
            }

            ipbanlist.save();
            c(a);
            return true;
         } catch (IOException ioexception) {
            e.warn((String)"Could not parse old ip banlist to convert it!", (Throwable)ioexception);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean c(final MinecraftServer p_c_0_) {
      final OpList oplist = new OpList(PlayerList.c);
      if(c.exists() && c.isFile()) {
         if(oplist.c().exists()) {
            try {
               oplist.load();
            } catch (IOException var6) {
               e.warn("Could not load existing file " + oplist.c().getName());
            }
         }

         try {
            List list = Files.readLines(c, Charsets.UTF_8);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
                  p_c_0_.getUserCache().a(p_onProfileLookupSucceeded_1_);
                  oplist.add(new OpListEntry(p_onProfileLookupSucceeded_1_, p_c_0_.p(), false));
               }

               public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
                  NameReferencingFileConverter.e.warn((String)("Could not lookup oplist entry for " + p_onProfileLookupFailed_1_.getName()), (Throwable)p_onProfileLookupFailed_2_);
                  if(!(p_onProfileLookupFailed_2_ instanceof ProfileNotFoundException)) {
                     throw new NameReferencingFileConverter.FileConversionException("Could not request user " + p_onProfileLookupFailed_1_.getName() + " from backend systems", p_onProfileLookupFailed_2_, (Object)null);
                  }
               }
            };
            a(p_c_0_, list, profilelookupcallback);
            oplist.save();
            c(c);
            return true;
         } catch (IOException ioexception) {
            e.warn((String)"Could not read old oplist to convert it!", (Throwable)ioexception);
            return false;
         } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
            e.error((String)"Conversion failed, please try again later", (Throwable)namereferencingfileconverter$fileconversionexception);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean d(final MinecraftServer p_d_0_) {
      final WhiteList whitelist = new WhiteList(PlayerList.d);
      if(d.exists() && d.isFile()) {
         if(whitelist.c().exists()) {
            try {
               whitelist.load();
            } catch (IOException var6) {
               e.warn("Could not load existing file " + whitelist.c().getName());
            }
         }

         try {
            List list = Files.readLines(d, Charsets.UTF_8);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
                  p_d_0_.getUserCache().a(p_onProfileLookupSucceeded_1_);
                  whitelist.add(new WhiteListEntry(p_onProfileLookupSucceeded_1_));
               }

               public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
                  NameReferencingFileConverter.e.warn((String)("Could not lookup user whitelist entry for " + p_onProfileLookupFailed_1_.getName()), (Throwable)p_onProfileLookupFailed_2_);
                  if(!(p_onProfileLookupFailed_2_ instanceof ProfileNotFoundException)) {
                     throw new NameReferencingFileConverter.FileConversionException("Could not request user " + p_onProfileLookupFailed_1_.getName() + " from backend systems", p_onProfileLookupFailed_2_, (Object)null);
                  }
               }
            };
            a(p_d_0_, list, profilelookupcallback);
            whitelist.save();
            c(d);
            return true;
         } catch (IOException ioexception) {
            e.warn((String)"Could not read old whitelist to convert it!", (Throwable)ioexception);
            return false;
         } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
            e.error((String)"Conversion failed, please try again later", (Throwable)namereferencingfileconverter$fileconversionexception);
            return false;
         }
      } else {
         return true;
      }
   }

   public static String a(String p_a_0_) {
      if(!UtilColor.b(p_a_0_) && p_a_0_.length() <= 16) {
         final MinecraftServer minecraftserver = MinecraftServer.getServer();
         GameProfile gameprofile = minecraftserver.getUserCache().getProfile(p_a_0_);
         if(gameprofile != null && gameprofile.getId() != null) {
            return gameprofile.getId().toString();
         } else if(!minecraftserver.T() && minecraftserver.getOnlineMode()) {
            final ArrayList arraylist = Lists.newArrayList();
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
                  minecraftserver.getUserCache().a(p_onProfileLookupSucceeded_1_);
                  arraylist.add(p_onProfileLookupSucceeded_1_);
               }

               public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
                  NameReferencingFileConverter.e.warn((String)("Could not lookup user whitelist entry for " + p_onProfileLookupFailed_1_.getName()), (Throwable)p_onProfileLookupFailed_2_);
               }
            };
            a(minecraftserver, Lists.newArrayList(new String[]{p_a_0_}), profilelookupcallback);
            return arraylist.size() > 0 && ((GameProfile)arraylist.get(0)).getId() != null?((GameProfile)arraylist.get(0)).getId().toString():"";
         } else {
            return EntityHuman.a(new GameProfile((UUID)null, p_a_0_)).toString();
         }
      } else {
         return p_a_0_;
      }
   }

   public static boolean a(final DedicatedServer p_a_0_, PropertyManager p_a_1_) {
      final File file1 = d(p_a_1_);
      new File(file1.getParentFile(), "playerdata");
      final File file2 = new File(file1.getParentFile(), "unknownplayers");
      if(file1.exists() && file1.isDirectory()) {
         File[] afile = file1.listFiles();
         ArrayList arraylist = Lists.newArrayList();

         for(File file3 : afile) {
            String s = file3.getName();
            if(s.toLowerCase(Locale.ROOT).endsWith(".dat")) {
               String s1 = s.substring(0, s.length() - ".dat".length());
               if(s1.length() > 0) {
                  arraylist.add(s1);
               }
            }
         }

         try {
            final String[] astring = (String[])arraylist.toArray(new String[arraylist.size()]);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
                  p_a_0_.getUserCache().a(p_onProfileLookupSucceeded_1_);
                  UUID uuid = p_onProfileLookupSucceeded_1_.getId();
                  if(uuid == null) {
                     throw new NameReferencingFileConverter.FileConversionException("Missing UUID for user profile " + p_onProfileLookupSucceeded_1_.getName(), (Throwable)null, (NameReferencingFileConverter.FileConversionException)null);
                  } else {
                     this.a(file1, this.a(p_onProfileLookupSucceeded_1_), uuid.toString());
                  }
               }

               public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
                  NameReferencingFileConverter.e.warn((String)("Could not lookup user uuid for " + p_onProfileLookupFailed_1_.getName()), (Throwable)p_onProfileLookupFailed_2_);
                  if(p_onProfileLookupFailed_2_ instanceof ProfileNotFoundException) {
                     String s2 = this.a(p_onProfileLookupFailed_1_);
                     this.a(file1, s2, s2);
                  } else {
                     throw new NameReferencingFileConverter.FileConversionException("Could not request user " + p_onProfileLookupFailed_1_.getName() + " from backend systems", p_onProfileLookupFailed_2_, (Object)null);
                  }
               }

               private void a(File p_a_1_, String p_a_2_, String p_a_3_) {
                  File file4 = new File(file2, p_a_2_ + ".dat");
                  File file5 = new File(p_a_1_, p_a_3_ + ".dat");
                  NBTTagCompound nbttagcompound = null;

                  try {
                     nbttagcompound = NBTCompressedStreamTools.a((InputStream)(new FileInputStream(file4)));
                  } catch (Exception exception1x) {
                     exception1x.printStackTrace();
                  }

                  if(nbttagcompound != null) {
                     if(!nbttagcompound.hasKey("bukkit")) {
                        nbttagcompound.set("bukkit", new NBTTagCompound());
                     }

                     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("bukkit");
                     nbttagcompound1.setString("lastKnownName", p_a_2_);

                     try {
                        NBTCompressedStreamTools.a((NBTTagCompound)nbttagcompound, (OutputStream)(new FileOutputStream(file2)));
                     } catch (Exception exception1) {
                        exception1.printStackTrace();
                     }
                  }

                  NameReferencingFileConverter.b(p_a_1_);
                  if(!file4.renameTo(file5)) {
                     throw new NameReferencingFileConverter.FileConversionException("Could not convert file for " + p_a_2_, (Throwable)null, (NameReferencingFileConverter.FileConversionException)null);
                  }
               }

               private String a(GameProfile p_a_1_) {
                  String s2 = null;

                  for(int i = 0; i < astring.length; ++i) {
                     if(astring[i] != null && astring[i].equalsIgnoreCase(p_a_1_.getName())) {
                        s2 = astring[i];
                        break;
                     }
                  }

                  if(s2 == null) {
                     throw new NameReferencingFileConverter.FileConversionException("Could not find the filename for " + p_a_1_.getName() + " anymore", (Throwable)null, (NameReferencingFileConverter.FileConversionException)null);
                  } else {
                     return s2;
                  }
               }
            };
            a(p_a_0_, Lists.newArrayList(astring), profilelookupcallback);
            return true;
         } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
            e.error((String)"Conversion failed, please try again later", (Throwable)namereferencingfileconverter$fileconversionexception);
            return false;
         }
      } else {
         return true;
      }
   }

   private static void b(File p_b_0_) {
      if(p_b_0_.exists()) {
         if(!p_b_0_.isDirectory()) {
            throw new NameReferencingFileConverter.FileConversionException("Can\'t create directory " + p_b_0_.getName() + " in world save directory.", (Throwable)null, (NameReferencingFileConverter.FileConversionException)null);
         }
      } else if(!p_b_0_.mkdirs()) {
         throw new NameReferencingFileConverter.FileConversionException("Can\'t create directory " + p_b_0_.getName() + " in world save directory.", (Throwable)null, (NameReferencingFileConverter.FileConversionException)null);
      }

   }

   public static boolean a(PropertyManager p_a_0_) {
      boolean flag = b(p_a_0_);
      flag = flag && c(p_a_0_);
      return flag;
   }

   private static boolean b(PropertyManager p_b_0_) {
      boolean flag = false;
      if(b.exists() && b.isFile()) {
         flag = true;
      }

      boolean flag1 = false;
      if(a.exists() && a.isFile()) {
         flag1 = true;
      }

      boolean flag2 = false;
      if(c.exists() && c.isFile()) {
         flag2 = true;
      }

      boolean flag3 = false;
      if(d.exists() && d.isFile()) {
         flag3 = true;
      }

      if(!flag && !flag1 && !flag2 && !flag3) {
         return true;
      } else {
         e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
         e.warn("** please remove the following files and restart the server:");
         if(flag) {
            e.warn("* " + b.getName());
         }

         if(flag1) {
            e.warn("* " + a.getName());
         }

         if(flag2) {
            e.warn("* " + c.getName());
         }

         if(flag3) {
            e.warn("* " + d.getName());
         }

         return false;
      }
   }

   private static boolean c(PropertyManager p_c_0_) {
      File file1 = d(p_c_0_);
      if(!file1.exists() || !file1.isDirectory() || file1.list().length <= 0 && file1.delete()) {
         return true;
      } else {
         e.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
         e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
         e.warn("** please restart the server and if the problem persists, remove the directory \'{}\'", new Object[]{file1.getPath()});
         return false;
      }
   }

   private static File d(PropertyManager p_d_0_) {
      String s = p_d_0_.getString("level-name", "world");
      File file1 = new File(MinecraftServer.getServer().server.getWorldContainer(), s);
      return new File(file1, "players");
   }

   private static void c(File p_c_0_) {
      File file1 = new File(p_c_0_.getName() + ".converted");
      p_c_0_.renameTo(file1);
   }

   private static Date b(String p_b_0_, Date p_b_1_) {
      Date date;
      try {
         date = ExpirableListEntry.a.parse(p_b_0_);
      } catch (ParseException var3) {
         date = p_b_1_;
      }

      return date;
   }

   static class FileConversionException extends RuntimeException {
      private FileConversionException(String p_i89_1_, Throwable p_i89_2_) {
         super(p_i89_1_, p_i89_2_);
      }

      private FileConversionException(String p_i90_1_) {
         super(p_i90_1_);
      }

      FileConversionException(String p_i91_1_, Object p_i91_2_) {
         this(p_i91_1_);
      }

      FileConversionException(String p_i92_1_, Throwable p_i92_2_, Object p_i92_3_) {
         this(p_i92_1_, p_i92_2_);
      }
   }
}
