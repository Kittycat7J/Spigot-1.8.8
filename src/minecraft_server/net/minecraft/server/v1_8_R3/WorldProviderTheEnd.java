package net.minecraft.server.v1_8_R3;

public class WorldProviderTheEnd extends WorldProvider
{
    public void b()
    {
        this.c = new WorldChunkManagerHell(BiomeBase.SKY, 0.0F);
        this.dimension = 1;
        this.e = true;
    }

    public IChunkProvider getChunkProvider()
    {
        return new ChunkProviderTheEnd(this.b, this.b.getSeed());
    }

    public float a(long p_a_1_, float p_a_3_)
    {
        return 0.0F;
    }

    public boolean e()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public boolean canSpawn(int p_canSpawn_1_, int p_canSpawn_2_)
    {
        return this.b.c(new BlockPosition(p_canSpawn_1_, 0, p_canSpawn_2_)).getMaterial().isSolid();
    }

    public BlockPosition h()
    {
        return new BlockPosition(100, 50, 0);
    }

    public int getSeaLevel()
    {
        return 50;
    }

    public String getName()
    {
        return "The End";
    }

    public String getSuffix()
    {
        return "_end";
    }
}
