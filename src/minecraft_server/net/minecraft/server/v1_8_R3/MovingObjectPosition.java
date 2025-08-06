package net.minecraft.server.v1_8_R3;

public class MovingObjectPosition
{
    private BlockPosition e;
    public MovingObjectPosition.EnumMovingObjectType type;
    public EnumDirection direction;
    public Vec3D pos;
    public Entity entity;

    public MovingObjectPosition(Vec3D p_i846_1_, EnumDirection p_i846_2_, BlockPosition p_i846_3_)
    {
        this(MovingObjectPosition.EnumMovingObjectType.BLOCK, p_i846_1_, p_i846_2_, p_i846_3_);
    }

    public MovingObjectPosition(Vec3D p_i847_1_, EnumDirection p_i847_2_)
    {
        this(MovingObjectPosition.EnumMovingObjectType.BLOCK, p_i847_1_, p_i847_2_, BlockPosition.ZERO);
    }

    public MovingObjectPosition(Entity p_i848_1_)
    {
        this(p_i848_1_, new Vec3D(p_i848_1_.locX, p_i848_1_.locY, p_i848_1_.locZ));
    }

    public MovingObjectPosition(MovingObjectPosition.EnumMovingObjectType p_i849_1_, Vec3D p_i849_2_, EnumDirection p_i849_3_, BlockPosition p_i849_4_)
    {
        this.type = p_i849_1_;
        this.e = p_i849_4_;
        this.direction = p_i849_3_;
        this.pos = new Vec3D(p_i849_2_.a, p_i849_2_.b, p_i849_2_.c);
    }

    public MovingObjectPosition(Entity p_i850_1_, Vec3D p_i850_2_)
    {
        this.type = MovingObjectPosition.EnumMovingObjectType.ENTITY;
        this.entity = p_i850_1_;
        this.pos = p_i850_2_;
    }

    public BlockPosition a()
    {
        return this.e;
    }

    public String toString()
    {
        return "HitResult{type=" + this.type + ", blockpos=" + this.e + ", f=" + this.direction + ", pos=" + this.pos + ", entity=" + this.entity + '}';
    }

    public static enum EnumMovingObjectType
    {
        MISS,
        BLOCK,
        ENTITY;
    }
}
