package net.minecraft.world.biome;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeEndDecorator extends BiomeDecorator {
   protected WorldGenerator spikeGen = new WorldGenSpikes(Blocks.end_stone);
    private static final Logger logger = LogManager.getLogger();
   protected void genDecorations(BiomeGenBase biomeGenBaseIn) {
      this.generateOres();
      logger.info("custom logger BiomeEndDecorator: calling if(this.randomGenerator.nextInt(5) == 0)");
      if(this.randomGenerator.nextInt(5) == 0) {
          logger.info("custom logger BiomeEndDecorator: calling int i = this.randomGenerator.nextInt(16) + 8;   j = this.randomGenerator.nextInt(16) + 8;");
         int i = this.randomGenerator.nextInt(16) + 8;
         int j = this.randomGenerator.nextInt(16) + 8;
         this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i, 0, j)));
      }

      if(this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
         EntityDragon entitydragon = new EntityDragon(this.currentWorld);
         entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
         this.currentWorld.spawnEntityInWorld(entitydragon);
      }
   }
}
