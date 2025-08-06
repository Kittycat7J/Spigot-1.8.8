package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityPortalEnterEvent;

public class BlockEnderPortal extends BlockContainer
{
    protected BlockEnderPortal(Material p_i158_1_)
    {
        super(p_i158_1_);
        this.a(1.0F);
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityEnderPortal();
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        float f = 0.0625F;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_)
    {
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_)
    {
        if (p_a_4_.vehicle == null && p_a_4_.passenger == null && !p_a_1_.isClientSide)
        {
            EntityPortalEnterEvent entityportalenterevent = new EntityPortalEnterEvent(p_a_4_.getBukkitEntity(), new Location(p_a_1_.getWorld(), (double)p_a_2_.getX(), (double)p_a_2_.getY(), (double)p_a_2_.getZ()));
            p_a_1_.getServer().getPluginManager().callEvent(entityportalenterevent);
            p_a_4_.c(1);
        }
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return MaterialMapColor.E;
    }
}
