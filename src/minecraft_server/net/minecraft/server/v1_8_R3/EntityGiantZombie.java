package net.minecraft.server.v1_8_R3;

public class EntityGiantZombie extends EntityMonster
{
    public EntityGiantZombie(World p_i1234_1_)
    {
        super(p_i1234_1_);
        this.setSize(this.width * 6.0F, this.length * 6.0F);
    }

    public float getHeadHeight()
    {
        return 10.440001F;
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(50.0D);
    }

    public float a(BlockPosition p_a_1_)
    {
        return this.world.o(p_a_1_) - 0.5F;
    }
}
