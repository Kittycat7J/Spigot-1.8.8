package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeDecorator;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.WorldGenEnder;
import net.minecraft.server.v1_8_R3.WorldGenerator;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BiomeTheEndDecorator extends BiomeDecorator {
   protected WorldGenerator M = new WorldGenEnder(Blocks.END_STONE);

   protected void a(BiomeBase p_a_1_) {
      this.a();
      if(this.b.nextInt(5) == 0) {
         int i = this.b.nextInt(16) + 8;
         int j = this.b.nextInt(16) + 8;
         this.M.generate(this.a, this.b, this.a.r(this.c.a(i, 0, j)));
      }

      if(this.c.getX() == 0 && this.c.getZ() == 0) {
         EntityEnderDragon entityenderdragon = new EntityEnderDragon(this.a);
         entityenderdragon.setPositionRotation(0.0D, 128.0D, 0.0D, this.b.nextFloat() * 360.0F, 0.0F);
         this.a.addEntity(entityenderdragon, SpawnReason.CHUNK_GEN);
      }

   }
}
