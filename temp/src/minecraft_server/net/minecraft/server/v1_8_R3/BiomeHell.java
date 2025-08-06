package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityPigZombie;

public class BiomeHell extends BiomeBase {
   public BiomeHell(int p_i577_1_) {
      super(p_i577_1_);
      this.at.clear();
      this.au.clear();
      this.av.clear();
      this.aw.clear();
      this.at.add(new BiomeBase.BiomeMeta(EntityGhast.class, 50, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntityPigZombie.class, 100, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntityMagmaCube.class, 1, 4, 4));
   }
}
