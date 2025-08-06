package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IDataManager;
import net.minecraft.server.v1_8_R3.MethodProfiler;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PersistentVillage;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldData;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;

public class SecondaryWorldServer extends WorldServer {
   private WorldServer a;

   public SecondaryWorldServer(MinecraftServer p_i403_1_, IDataManager p_i403_2_, int p_i403_3_, WorldServer p_i403_4_, MethodProfiler p_i403_5_, WorldData p_i403_6_, Environment p_i403_7_, ChunkGenerator p_i403_8_) {
      super(p_i403_1_, p_i403_2_, p_i403_6_, p_i403_3_, p_i403_5_, p_i403_7_, p_i403_8_);
      this.a = p_i403_4_;
   }

   public World b() {
      this.worldMaps = this.a.T();
      String s = PersistentVillage.a(this.worldProvider);
      PersistentVillage persistentvillage = (PersistentVillage)this.worldMaps.get(PersistentVillage.class, s);
      if(persistentvillage == null) {
         this.villages = new PersistentVillage(this);
         this.worldMaps.a(s, this.villages);
      } else {
         this.villages = persistentvillage;
         this.villages.a((World)this);
      }

      return super.b();
   }
}
