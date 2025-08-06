package net.minecraft.server.v1_8_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemBoat extends Item
{
    public ItemBoat()
    {
        this.maxStackSize = 1;
        this.a(CreativeModeTab.e);
    }

    public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_)
    {
        float f = 1.0F;
        float f1 = p_a_3_.lastPitch + (p_a_3_.pitch - p_a_3_.lastPitch) * f;
        float f2 = p_a_3_.lastYaw + (p_a_3_.yaw - p_a_3_.lastYaw) * f;
        double d0 = p_a_3_.lastX + (p_a_3_.locX - p_a_3_.lastX) * (double)f;
        double d1 = p_a_3_.lastY + (p_a_3_.locY - p_a_3_.lastY) * (double)f + (double)p_a_3_.getHeadHeight();
        double d2 = p_a_3_.lastZ + (p_a_3_.locZ - p_a_3_.lastZ) * (double)f;
        Vec3D vec3d = new Vec3D(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3D vec3d1 = vec3d.add((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        MovingObjectPosition movingobjectposition = p_a_2_.rayTrace(vec3d, vec3d1, true);

        if (movingobjectposition == null)
        {
            return p_a_1_;
        }
        else
        {
            Vec3D vec3d2 = p_a_3_.d(f);
            boolean flag = false;
            float f9 = 1.0F;
            List list = p_a_2_.getEntities(p_a_3_, p_a_3_.getBoundingBox().a(vec3d2.a * d3, vec3d2.b * d3, vec3d2.c * d3).grow((double)f9, (double)f9, (double)f9));

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.ad())
                {
                    float f10 = entity.ao();
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow((double)f10, (double)f10, (double)f10);

                    if (axisalignedbb.a(vec3d))
                    {
                        flag = true;
                    }
                }
            }

            if (flag)
            {
                return p_a_1_;
            }
            else
            {
                if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK)
                {
                    BlockPosition blockposition = movingobjectposition.a();
                    PlayerInteractEvent playerinteractevent = CraftEventFactory.callPlayerInteractEvent(p_a_3_, Action.RIGHT_CLICK_BLOCK, blockposition, movingobjectposition.direction, p_a_1_);

                    if (playerinteractevent.isCancelled())
                    {
                        return p_a_1_;
                    }

                    if (p_a_2_.getType(blockposition).getBlock() == Blocks.SNOW_LAYER)
                    {
                        blockposition = blockposition.down();
                    }

                    EntityBoat entityboat = new EntityBoat(p_a_2_, (double)((float)blockposition.getX() + 0.5F), (double)((float)blockposition.getY() + 1.0F), (double)((float)blockposition.getZ() + 0.5F));
                    entityboat.yaw = (float)(((MathHelper.floor((double)(p_a_3_.yaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);

                    if (!p_a_2_.getCubes(entityboat, entityboat.getBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty())
                    {
                        return p_a_1_;
                    }

                    if (!p_a_2_.isClientSide)
                    {
                        p_a_2_.addEntity(entityboat);
                    }

                    if (!p_a_3_.abilities.canInstantlyBuild)
                    {
                        --p_a_1_.count;
                    }

                    p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
                }

                return p_a_1_;
            }
        }
    }
}
