package net.minecraft.server.v1_8_R3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_8_R3.IChunkLoader;
import net.minecraft.server.v1_8_R3.IDataManager;
import net.minecraft.server.v1_8_R3.IPlayerFileData;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.WorldData;
import net.minecraft.server.v1_8_R3.WorldProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public class WorldNBTStorage implements IDataManager, IPlayerFileData {
   private static final Logger a = LogManager.getLogger();
   private final File baseDir;
   private final File playerDir;
   private final File dataDir;
   private final long sessionId = MinecraftServer.az();
   private final String f;
   private UUID uuid = null;

   public WorldNBTStorage(File p_i359_1_, String p_i359_2_, boolean p_i359_3_) {
      this.baseDir = new File(p_i359_1_, p_i359_2_);
      this.baseDir.mkdirs();
      this.playerDir = new File(this.baseDir, "playerdata");
      this.dataDir = new File(this.baseDir, "data");
      this.dataDir.mkdirs();
      this.f = p_i359_2_;
      if(p_i359_3_) {
         this.playerDir.mkdirs();
      }

      this.h();
   }

   private void h() {
      try {
         File file1 = new File(this.baseDir, "session.lock");
         DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));

         try {
            dataoutputstream.writeLong(this.sessionId);
         } finally {
            dataoutputstream.close();
         }

      } catch (IOException ioexception) {
         ioexception.printStackTrace();
         throw new RuntimeException("Failed to check session lock for world located at " + this.baseDir + ", aborting. Stop the server and delete the session.lock in this world to prevent further issues.");
      }
   }

   public File getDirectory() {
      return this.baseDir;
   }

   public void checkSession() throws ExceptionWorldConflict {
      try {
         File file1 = new File(this.baseDir, "session.lock");
         DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));

         try {
            if(datainputstream.readLong() != this.sessionId) {
               throw new ExceptionWorldConflict("The save for world located at " + this.baseDir + " is being accessed from another location, aborting");
            }
         } finally {
            datainputstream.close();
         }

      } catch (IOException var7) {
         throw new ExceptionWorldConflict("Failed to check session lock for world located at " + this.baseDir + ", aborting. Stop the server and delete the session.lock in this world to prevent further issues.");
      }
   }

   public IChunkLoader createChunkLoader(WorldProvider p_createChunkLoader_1_) {
      throw new RuntimeException("Old Chunk Storage is no longer supported.");
   }

   public WorldData getWorldData() {
      File file1 = new File(this.baseDir, "level.dat");
      if(file1.exists()) {
         try {
            NBTTagCompound nbttagcompound2 = NBTCompressedStreamTools.a((InputStream)(new FileInputStream(file1)));
            NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompound("Data");
            return new WorldData(nbttagcompound3);
         } catch (Exception exception1) {
            exception1.printStackTrace();
         }
      }

      file1 = new File(this.baseDir, "level.dat_old");
      if(file1.exists()) {
         try {
            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a((InputStream)(new FileInputStream(file1)));
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Data");
            return new WorldData(nbttagcompound1);
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }

      return null;
   }

   public void saveWorldData(WorldData p_saveWorldData_1_, NBTTagCompound p_saveWorldData_2_) {
      NBTTagCompound nbttagcompound = p_saveWorldData_1_.a(p_saveWorldData_2_);
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      nbttagcompound1.set("Data", nbttagcompound);

      try {
         File file1 = new File(this.baseDir, "level.dat_new");
         File file2 = new File(this.baseDir, "level.dat_old");
         File file3 = new File(this.baseDir, "level.dat");
         NBTCompressedStreamTools.a((NBTTagCompound)nbttagcompound1, (OutputStream)(new FileOutputStream(file1)));
         if(file2.exists()) {
            file2.delete();
         }

         file3.renameTo(file2);
         if(file3.exists()) {
            file3.delete();
         }

         file1.renameTo(file3);
         if(file1.exists()) {
            file1.delete();
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }

   }

   public void saveWorldData(WorldData p_saveWorldData_1_) {
      NBTTagCompound nbttagcompound = p_saveWorldData_1_.a();
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      nbttagcompound1.set("Data", nbttagcompound);

      try {
         File file1 = new File(this.baseDir, "level.dat_new");
         File file2 = new File(this.baseDir, "level.dat_old");
         File file3 = new File(this.baseDir, "level.dat");
         NBTCompressedStreamTools.a((NBTTagCompound)nbttagcompound1, (OutputStream)(new FileOutputStream(file1)));
         if(file2.exists()) {
            file2.delete();
         }

         file3.renameTo(file2);
         if(file3.exists()) {
            file3.delete();
         }

         file1.renameTo(file3);
         if(file1.exists()) {
            file1.delete();
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }

   }

   public void save(EntityHuman p_save_1_) {
      try {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         p_save_1_.e(nbttagcompound);
         File file1 = new File(this.playerDir, p_save_1_.getUniqueID().toString() + ".dat.tmp");
         File file2 = new File(this.playerDir, p_save_1_.getUniqueID().toString() + ".dat");
         NBTCompressedStreamTools.a((NBTTagCompound)nbttagcompound, (OutputStream)(new FileOutputStream(file1)));
         if(file2.exists()) {
            file2.delete();
         }

         file1.renameTo(file2);
      } catch (Exception var5) {
         a.warn("Failed to save player data for " + p_save_1_.getName());
      }

   }

   public NBTTagCompound load(EntityHuman p_load_1_) {
      NBTTagCompound nbttagcompound = null;

      try {
         File file1 = new File(this.playerDir, p_load_1_.getUniqueID().toString() + ".dat");
         boolean flag = false;
         if(!file1.exists()) {
            file1 = new File(this.playerDir, UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_load_1_.getName()).getBytes("UTF-8")).toString() + ".dat");
            if(file1.exists()) {
               flag = true;
               Bukkit.getServer().getLogger().warning("Using offline mode UUID file for player " + p_load_1_.getName() + " as it is the only copy we can find.");
            }
         }

         if(file1.exists() && file1.isFile()) {
            nbttagcompound = NBTCompressedStreamTools.a((InputStream)(new FileInputStream(file1)));
         }

         if(flag) {
            file1.renameTo(new File(file1.getPath() + ".offline-read"));
         }
      } catch (Exception var7) {
         a.warn("Failed to load player data for " + p_load_1_.getName());
      }

      if(nbttagcompound != null) {
         if(p_load_1_ instanceof EntityPlayer) {
            CraftPlayer craftplayer = (CraftPlayer)p_load_1_.getBukkitEntity();
            long i = (new File(this.playerDir, p_load_1_.getUniqueID().toString() + ".dat")).lastModified();
            if(i < craftplayer.getFirstPlayed()) {
               craftplayer.setFirstPlayed(i);
            }
         }

         p_load_1_.f(nbttagcompound);
      }

      return nbttagcompound;
   }

   public NBTTagCompound getPlayerData(String p_getPlayerData_1_) {
      try {
         File file1 = new File(this.playerDir, p_getPlayerData_1_ + ".dat");
         if(file1.exists()) {
            return NBTCompressedStreamTools.a((InputStream)(new FileInputStream(file1)));
         }
      } catch (Exception var3) {
         a.warn("Failed to load player data for " + p_getPlayerData_1_);
      }

      return null;
   }

   public IPlayerFileData getPlayerFileData() {
      return this;
   }

   public String[] getSeenPlayers() {
      String[] astring = this.playerDir.list();
      if(astring == null) {
         astring = new String[0];
      }

      for(int i = 0; i < astring.length; ++i) {
         if(astring[i].endsWith(".dat")) {
            astring[i] = astring[i].substring(0, astring[i].length() - 4);
         }
      }

      return astring;
   }

   public void a() {
   }

   public File getDataFile(String p_getDataFile_1_) {
      return new File(this.dataDir, p_getDataFile_1_ + ".dat");
   }

   public String g() {
      return this.f;
   }

   public UUID getUUID() {
      if(this.uuid != null) {
         return this.uuid;
      } else {
         File file1 = new File(this.baseDir, "uid.dat");
         if(file1.exists()) {
            label34: {
               DataInputStream datainputstream = null;

               UUID uuidx;
               try {
                  datainputstream = new DataInputStream(new FileInputStream(file1));
                  uuidx = this.uuid = new UUID(datainputstream.readLong(), datainputstream.readLong());
               } catch (IOException ioexception1) {
                  a.warn((String)("Failed to read " + file1 + ", generating new random UUID"), (Throwable)ioexception1);
                  break label34;
               } finally {
                  if(datainputstream != null) {
                     try {
                        datainputstream.close();
                     } catch (IOException var25) {
                        ;
                     }
                  }

               }

               return uuidx;
            }
         }

         this.uuid = UUID.randomUUID();
         DataOutputStream dataoutputstream = null;

         try {
            dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
            dataoutputstream.writeLong(this.uuid.getMostSignificantBits());
            dataoutputstream.writeLong(this.uuid.getLeastSignificantBits());
         } catch (IOException ioexception) {
            a.warn((String)("Failed to write " + file1), (Throwable)ioexception);
         } finally {
            if(dataoutputstream != null) {
               try {
                  dataoutputstream.close();
               } catch (IOException var24) {
                  ;
               }
            }

         }

         return this.uuid;
      }
   }

   public File getPlayerDir() {
      return this.playerDir;
   }
}
