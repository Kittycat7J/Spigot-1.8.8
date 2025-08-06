package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;

public class EntityPainting extends EntityHanging
{
    public EntityPainting.EnumArt art;

    public EntityPainting(World p_i414_1_)
    {
        super(p_i414_1_);
        this.art = EntityPainting.EnumArt.values()[this.random.nextInt(EntityPainting.EnumArt.values().length)];
    }

    public EntityPainting(World p_i415_1_, BlockPosition p_i415_2_, EnumDirection p_i415_3_)
    {
        super(p_i415_1_, p_i415_2_);
        ArrayList arraylist = Lists.newArrayList();

        for (EntityPainting.EnumArt entitypainting$enumart : EntityPainting.EnumArt.values())
        {
            this.art = entitypainting$enumart;
            this.setDirection(p_i415_3_);

            if (this.survives())
            {
                arraylist.add(entitypainting$enumart);
            }
        }

        if (!arraylist.isEmpty())
        {
            this.art = (EntityPainting.EnumArt)arraylist.get(this.random.nextInt(arraylist.size()));
        }

        this.setDirection(p_i415_3_);
    }

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setString("Motive", this.art.B);
        super.b(p_b_1_);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        String s = p_a_1_.getString("Motive");

        for (EntityPainting.EnumArt entitypainting$enumart : EntityPainting.EnumArt.values())
        {
            if (entitypainting$enumart.B.equals(s))
            {
                this.art = entitypainting$enumart;
            }
        }

        if (this.art == null)
        {
            this.art = EntityPainting.EnumArt.KEBAB;
        }

        super.a(p_a_1_);
    }

    public int l()
    {
        return this.art.C;
    }

    public int m()
    {
        return this.art.D;
    }

    public void b(Entity p_b_1_)
    {
        if (this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            if (p_b_1_ instanceof EntityHuman)
            {
                EntityHuman entityhuman = (EntityHuman)p_b_1_;

                if (entityhuman.abilities.canInstantlyBuild)
                {
                    return;
                }
            }

            this.a(new ItemStack(Items.PAINTING), 0.0F);
        }
    }

    public void setPositionRotation(double p_setPositionRotation_1_, double p_setPositionRotation_3_, double p_setPositionRotation_5_, float p_setPositionRotation_7_, float p_setPositionRotation_8_)
    {
        BlockPosition blockposition = this.blockPosition.a(p_setPositionRotation_1_ - this.locX, p_setPositionRotation_3_ - this.locY, p_setPositionRotation_5_ - this.locZ);
        this.setPosition((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ());
    }

    public static enum EnumArt
    {
        KEBAB("Kebab", 16, 16, 0, 0),
        AZTEC("Aztec", 16, 16, 16, 0),
        ALBAN("Alban", 16, 16, 32, 0),
        AZTEC_2("Aztec2", 16, 16, 48, 0),
        BOMB("Bomb", 16, 16, 64, 0),
        PLANT("Plant", 16, 16, 80, 0),
        WASTELAND("Wasteland", 16, 16, 96, 0),
        POOL("Pool", 32, 16, 0, 32),
        COURBET("Courbet", 32, 16, 32, 32),
        SEA("Sea", 32, 16, 64, 32),
        SUNSET("Sunset", 32, 16, 96, 32),
        CREEBET("Creebet", 32, 16, 128, 32),
        WANDERER("Wanderer", 16, 32, 0, 64),
        GRAHAM("Graham", 16, 32, 16, 64),
        MATCH("Match", 32, 32, 0, 128),
        BUST("Bust", 32, 32, 32, 128),
        STAGE("Stage", 32, 32, 64, 128),
        VOID("Void", 32, 32, 96, 128),
        SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
        WITHER("Wither", 32, 32, 160, 128),
        FIGHTERS("Fighters", 64, 32, 0, 96),
        POINTER("Pointer", 64, 64, 0, 192),
        PIGSCENE("Pigscene", 64, 64, 64, 192),
        BURNING_SKULL("BurningSkull", 64, 64, 128, 192),
        SKELETON("Skeleton", 64, 48, 192, 64),
        DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);

        public static final int A = "SkullAndRoses".length();
        public final String B;
        public final int C;
        public final int D;
        public final int E;
        public final int F;

        private EnumArt(String p_i397_3_, int p_i397_4_, int p_i397_5_, int p_i397_6_, int p_i397_7_)
        {
            this.B = p_i397_3_;
            this.C = p_i397_4_;
            this.D = p_i397_5_;
            this.E = p_i397_6_;
            this.F = p_i397_7_;
        }
    }
}
