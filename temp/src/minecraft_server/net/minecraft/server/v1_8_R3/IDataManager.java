package net.minecraft.server.v1_8_R3;

import java.io.File;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_8_R3.IChunkLoader;
import net.minecraft.server.v1_8_R3.IPlayerFileData;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.WorldData;
import net.minecraft.server.v1_8_R3.WorldProvider;

public interface IDataManager {
   WorldData getWorldData();

   void checkSession() throws ExceptionWorldConflict;

   IChunkLoader createChunkLoader(WorldProvider var1);

   void saveWorldData(WorldData var1, NBTTagCompound var2);

   void saveWorldData(WorldData var1);

   IPlayerFileData getPlayerFileData();

   void a();

   File getDirectory();

   File getDataFile(String var1);

   String g();

   UUID getUUID();
}
