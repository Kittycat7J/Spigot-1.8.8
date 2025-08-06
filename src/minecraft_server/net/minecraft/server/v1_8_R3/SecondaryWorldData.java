package net.minecraft.server.v1_8_R3;

public class SecondaryWorldData extends WorldData
{
    private final WorldData b;

    public SecondaryWorldData(WorldData p_i841_1_)
    {
        this.b = p_i841_1_;
    }

    public NBTTagCompound a()
    {
        return this.b.a();
    }

    public NBTTagCompound a(NBTTagCompound p_a_1_)
    {
        return this.b.a(p_a_1_);
    }

    public long getSeed()
    {
        return this.b.getSeed();
    }

    public int c()
    {
        return this.b.c();
    }

    public int d()
    {
        return this.b.d();
    }

    public int e()
    {
        return this.b.e();
    }

    public long getTime()
    {
        return this.b.getTime();
    }

    public long getDayTime()
    {
        return this.b.getDayTime();
    }

    public NBTTagCompound i()
    {
        return this.b.i();
    }

    public String getName()
    {
        return this.b.getName();
    }

    public int l()
    {
        return this.b.l();
    }

    public boolean isThundering()
    {
        return this.b.isThundering();
    }

    public int getThunderDuration()
    {
        return this.b.getThunderDuration();
    }

    public boolean hasStorm()
    {
        return this.b.hasStorm();
    }

    public int getWeatherDuration()
    {
        return this.b.getWeatherDuration();
    }

    public WorldSettings.EnumGamemode getGameType()
    {
        return this.b.getGameType();
    }

    public void setTime(long p_setTime_1_)
    {
    }

    public void setDayTime(long p_setDayTime_1_)
    {
    }

    public void setSpawn(BlockPosition p_setSpawn_1_)
    {
    }

    public void a(String p_a_1_)
    {
    }

    public void e(int p_e_1_)
    {
    }

    public void setThundering(boolean p_setThundering_1_)
    {
    }

    public void setThunderDuration(int p_setThunderDuration_1_)
    {
    }

    public void setStorm(boolean p_setStorm_1_)
    {
    }

    public void setWeatherDuration(int p_setWeatherDuration_1_)
    {
    }

    public boolean shouldGenerateMapFeatures()
    {
        return this.b.shouldGenerateMapFeatures();
    }

    public boolean isHardcore()
    {
        return this.b.isHardcore();
    }

    public WorldType getType()
    {
        return this.b.getType();
    }

    public void a(WorldType p_a_1_)
    {
    }

    public boolean v()
    {
        return this.b.v();
    }

    public void c(boolean p_c_1_)
    {
    }

    public boolean w()
    {
        return this.b.w();
    }

    public void d(boolean p_d_1_)
    {
    }

    public GameRules x()
    {
        return this.b.x();
    }

    public EnumDifficulty getDifficulty()
    {
        return this.b.getDifficulty();
    }

    public void setDifficulty(EnumDifficulty p_setDifficulty_1_)
    {
    }

    public boolean isDifficultyLocked()
    {
        return this.b.isDifficultyLocked();
    }

    public void e(boolean p_e_1_)
    {
    }
}
