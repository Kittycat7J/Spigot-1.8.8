package net.minecraft.world.biome;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.custom.DBLogger;

public class BiomeEndDecorator extends BiomeDecorator {
   protected WorldGenerator spikeGen = new WorldGenSpikes(Blocks.end_stone);
    private static final Logger logger = LogManager.getLogger();
   protected void genDecorations(BiomeGenBase biomeGenBaseIn) {

      this.generateOres();
//      logger.info("nextInt(1):{}",this.randomGenerator.nextInt(1));
      // this.randomGenerator.setSeed(119861556137096L);
      // logger.info("custom logger BiomeEndDecorator: calling if(this.randomGenerator.nextInt(5) == 0)");
      DBLogger.log("BiomeEndDecorator.java", this.randomGenerator, "nextInt", 5, "spike generation chance");
      int chance = this.randomGenerator.nextInt(5);
      // logger.info("custom logger BiomeEndDecorator: this.randomGenerator.nextInt(5) = {}",chance);
      // logger.info("random gen is:{}",this.randomGenerator);
      if(chance == 0) {
         //  logger.info("custom logger BiomeEndDecorator: calling int x = this.randomGenerator.nextInt(16) + 8;\nz = this.randomGenerator.nextInt(16) + 8;");
          DBLogger.log("BiomeEndDecorator.java", this.randomGenerator, "nextInt", 16, "spike generation x");
         int x = this.randomGenerator.nextInt(16) + 8;
         DBLogger.log("BiomeEndDecorator.java", this.randomGenerator, "nextInt", 16, "spike generation z");
         int z = this.randomGenerator.nextInt(16) + 8;
         // logger.info("custom logger BiomeEndDecorator: x:{}, z:{}",x,z);
         this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(x, 0, z)));
      }

      if(this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
         EntityDragon entitydragon = new EntityDragon(this.currentWorld);
         // logger.info("custom logger BiomeEndDecorator: this.randomGenerator.nextFloat()");
         DBLogger.log("BiomeEndDecorator.java", this.randomGenerator, "nextFloat", -1, "entitydragon.setLocationAndAngles");
         entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
         this.currentWorld.spawnEntityInWorld(entitydragon);
      }
   }
}
