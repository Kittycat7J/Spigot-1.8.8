package net.minecraft.server.v1_8_R3;

public final class WorldSettings
{
    private final long a;
    private final WorldSettings.EnumGamemode b;
    private final boolean c;
    private final boolean d;
    private final WorldType e;
    private boolean f;
    private boolean g;
    private String h;

    public WorldSettings(long p_i556_1_, WorldSettings.EnumGamemode p_i556_3_, boolean p_i556_4_, boolean p_i556_5_, WorldType p_i556_6_)
    {
        this.h = "";
        this.a = p_i556_1_;
        this.b = p_i556_3_;
        this.c = p_i556_4_;
        this.d = p_i556_5_;
        this.e = p_i556_6_;
    }

    public WorldSettings(WorldData p_i557_1_)
    {
        this(p_i557_1_.getSeed(), p_i557_1_.getGameType(), p_i557_1_.shouldGenerateMapFeatures(), p_i557_1_.isHardcore(), p_i557_1_.getType());
    }

    public WorldSettings a()
    {
        this.g = true;
        return this;
    }

    public WorldSettings setGeneratorSettings(String p_setGeneratorSettings_1_)
    {
        this.h = p_setGeneratorSettings_1_;
        return this;
    }

    public boolean c()
    {
        return this.g;
    }

    public long d()
    {
        return this.a;
    }

    public WorldSettings.EnumGamemode e()
    {
        return this.b;
    }

    public boolean f()
    {
        return this.d;
    }

    public boolean g()
    {
        return this.c;
    }

    public WorldType h()
    {
        return this.e;
    }

    public boolean i()
    {
        return this.f;
    }

    public static WorldSettings.EnumGamemode a(int p_a_0_)
    {
        return WorldSettings.EnumGamemode.getById(p_a_0_);
    }

    public String j()
    {
        return this.h;
    }

    public static enum EnumGamemode
    {
        NOT_SET(-1, ""),
        SURVIVAL(0, "survival"),
        CREATIVE(1, "creative"),
        ADVENTURE(2, "adventure"),
        SPECTATOR(3, "spectator");

        int f;
        String g;

        private EnumGamemode(int p_i555_3_, String p_i555_4_)
        {
            this.f = p_i555_3_;
            this.g = p_i555_4_;
        }

        public int getId()
        {
            return this.f;
        }

        public String b()
        {
            return this.g;
        }

        public void a(PlayerAbilities p_a_1_)
        {
            if (this == CREATIVE)
            {
                p_a_1_.canFly = true;
                p_a_1_.canInstantlyBuild = true;
                p_a_1_.isInvulnerable = true;
            }
            else if (this == SPECTATOR)
            {
                p_a_1_.canFly = true;
                p_a_1_.canInstantlyBuild = false;
                p_a_1_.isInvulnerable = true;
                p_a_1_.isFlying = true;
            }
            else
            {
                p_a_1_.canFly = false;
                p_a_1_.canInstantlyBuild = false;
                p_a_1_.isInvulnerable = false;
                p_a_1_.isFlying = false;
            }

            p_a_1_.mayBuild = !this.c();
        }

        public boolean c()
        {
            return this == ADVENTURE || this == SPECTATOR;
        }

        public boolean d()
        {
            return this == CREATIVE;
        }

        public boolean e()
        {
            return this == SURVIVAL || this == ADVENTURE;
        }

        public static WorldSettings.EnumGamemode getById(int p_getById_0_)
        {
            for (WorldSettings.EnumGamemode worldsettings$enumgamemode : values())
            {
                if (worldsettings$enumgamemode.f == p_getById_0_)
                {
                    return worldsettings$enumgamemode;
                }
            }

            return SURVIVAL;
        }
    }
}
