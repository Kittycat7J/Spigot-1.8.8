package net.minecraft.server.v1_8_R3;

final class DispenserRegistry$17 extends DispenseBehaviorProjectile
{
    protected IProjectile a(World p_a_1_, IPosition p_a_2_)
    {
        return new EntityEgg(p_a_1_, p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
    }
}
