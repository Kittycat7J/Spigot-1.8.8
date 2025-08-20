package net.minecraft.server.v1_8_R3;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BiomeTheEndDecorator extends BiomeDecorator
{
    protected WorldGenerator spikeGen = new WorldGenEnder(Blocks.END_STONE);

    protected void a(BiomeBase p_a_1_)
    {
        this.a();
        logger.debug("BiomeTheEndDecorator.java if (this.randomGenerator.nextInt(5) == 0)");
        if (this.randomGenerator.nextInt(5) == 0)

        {
            int i = this.randomGenerator.nextInt(16) + 8;
            int j = this.randomGenerator.nextInt(16) + 8;
            logger.debug("BiomeTheEndDecorator.java i", i, "j", j);
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.c.a(i, 0, j)));
            logger.debug("BiomeTheEndDecorator.java Spike generated at", this.c.a(i, 0, j));
        }

        if (this.c.getX() == 0 && this.c.getZ() == 0)
        {
            EntityEnderDragon entityenderdragon = new EntityEnderDragon(this.currentWorld);
            entityenderdragon.setPositionRotation(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
            this.currentWorld.addEntity(entityenderdragon, SpawnReason.CHUNK_GEN);
        }
    }
}
