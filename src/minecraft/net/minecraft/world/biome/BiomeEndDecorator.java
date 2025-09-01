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
      this.randomGenerator.setSeed(9716264376862L);
//      this.randomGenerator.setSeed(9716264376862L);
      this.generateOres();
//      logger.info("nextInt(1):{}",this.randomGenerator.nextInt(1));
      logger.info("custom logger BiomeEndDecorator: calling if(this.randomGenerator.nextInt(5) == 0)");
      int chance = this.randomGenerator.nextInt(5);
      logger.info("custom logger BiomeEndDecorator: this.randomGenerator.nextInt(5) = {}",chance);
      if(chance == 0) {
          logger.info("custom logger BiomeEndDecorator: calling int x = this.randomGenerator.nextInt(16) + 8;\nz = this.randomGenerator.nextInt(16) + 8;");
         int x = this.randomGenerator.nextInt(16) + 8;
         int z = this.randomGenerator.nextInt(16) + 8;
         logger.info("custom logger BiomeEndDecorator: x:{}, z:{}",x,z);
         this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(x, 0, z)));
      }

      if(this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
         EntityDragon entitydragon = new EntityDragon(this.currentWorld);
         logger.info("custom logger BiomeEndDecorator: this.randomGenerator.nextFloat()");
         entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
         this.currentWorld.spawnEntityInWorld(entitydragon);
      }
   }
}
