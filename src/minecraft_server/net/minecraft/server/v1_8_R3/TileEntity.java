package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
import org.bukkit.inventory.InventoryHolder;
import org.spigotmc.CustomTimingsHandler;

public abstract class TileEntity
{
    public CustomTimingsHandler tickTimer = SpigotTimings.getTileEntityTimings(this);
    private static final Logger a = LogManager.getLogger();
    private static Map < String, Class <? extends TileEntity >> f = Maps. < String, Class <? extends TileEntity >> newHashMap();
    private static Map < Class <? extends TileEntity > , String > g = Maps. < Class <? extends TileEntity > , String > newHashMap();
    protected World world;
    protected BlockPosition position = BlockPosition.ZERO;
    protected boolean d;
    private int h = -1;
    protected Block e;

    static
    {
        a(TileEntityFurnace.class, "Furnace");
        a(TileEntityChest.class, "Chest");
        a(TileEntityEnderChest.class, "EnderChest");
        a(BlockJukeBox.TileEntityRecordPlayer.class, "RecordPlayer");
        a(TileEntityDispenser.class, "Trap");
        a(TileEntityDropper.class, "Dropper");
        a(TileEntitySign.class, "Sign");
        a(TileEntityMobSpawner.class, "MobSpawner");
        a(TileEntityNote.class, "Music");
        a(TileEntityPiston.class, "Piston");
        a(TileEntityBrewingStand.class, "Cauldron");
        a(TileEntityEnchantTable.class, "EnchantTable");
        a(TileEntityEnderPortal.class, "Airportal");
        a(TileEntityCommand.class, "Control");
        a(TileEntityBeacon.class, "Beacon");
        a(TileEntitySkull.class, "Skull");
        a(TileEntityLightDetector.class, "DLDetector");
        a(TileEntityHopper.class, "Hopper");
        a(TileEntityComparator.class, "Comparator");
        a(TileEntityFlowerPot.class, "FlowerPot");
        a(TileEntityBanner.class, "Banner");
    }

    private static void a(Class <? extends TileEntity > p_a_0_, String p_a_1_)
    {
        if (f.containsKey(p_a_1_))
        {
            throw new IllegalArgumentException("Duplicate id: " + p_a_1_);
        }
        else
        {
            f.put(p_a_1_, p_a_0_);
            g.put(p_a_0_, p_a_1_);
        }
    }

    public World getWorld()
    {
        return this.world;
    }

    public void a(World p_a_1_)
    {
        this.world = p_a_1_;
    }

    public boolean t()
    {
        return this.world != null;
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.position = new BlockPosition(p_a_1_.getInt("x"), p_a_1_.getInt("y"), p_a_1_.getInt("z"));
    }

    public void b(NBTTagCompound p_b_1_)
    {
        String s = (String)g.get(this.getClass());

        if (s == null)
        {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        else
        {
            p_b_1_.setString("id", s);
            p_b_1_.setInt("x", this.position.getX());
            p_b_1_.setInt("y", this.position.getY());
            p_b_1_.setInt("z", this.position.getZ());
        }
    }

    public static TileEntity c(NBTTagCompound p_c_0_)
    {
        TileEntity tileentity = null;

        try
        {
            Class oclass = (Class)f.get(p_c_0_.getString("id"));

            if (oclass != null)
            {
                tileentity = (TileEntity)oclass.newInstance();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (tileentity != null)
        {
            tileentity.a(p_c_0_);
        }
        else
        {
            a.warn("Skipping BlockEntity with id " + p_c_0_.getString("id"));
        }

        return tileentity;
    }

    public int u()
    {
        if (this.h == -1)
        {
            IBlockData iblockdata = this.world.getType(this.position);
            this.h = iblockdata.getBlock().toLegacyData(iblockdata);
        }

        return this.h;
    }

    public void update()
    {
        if (this.world != null)
        {
            IBlockData iblockdata = this.world.getType(this.position);
            this.h = iblockdata.getBlock().toLegacyData(iblockdata);
            this.world.b(this.position, this);

            if (this.w() != Blocks.AIR)
            {
                this.world.updateAdjacentComparators(this.position, this.w());
            }
        }
    }

    public BlockPosition getPosition()
    {
        return this.position;
    }

    public Block w()
    {
        if (this.e == null)
        {
            this.e = this.world.getType(this.position).getBlock();
        }

        return this.e;
    }

    public Packet getUpdatePacket()
    {
        return null;
    }

    public boolean x()
    {
        return this.d;
    }

    public void y()
    {
        this.d = true;
    }

    public void D()
    {
        this.d = false;
    }

    public boolean c(int p_c_1_, int p_c_2_)
    {
        return false;
    }

    public void E()
    {
        this.e = null;
        this.h = -1;
    }

    public void a(CrashReportSystemDetails p_a_1_)
    {
        p_a_1_.a("Name", new Callable()
        {
            public String a() throws Exception
            {
                return (String)TileEntity.g.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
            }
            public Object call() throws Exception
            {
                return this.a();
            }
        });

        if (this.world != null)
        {
            CrashReportSystemDetails.a(p_a_1_, this.position, this.w(), this.u());
            p_a_1_.a("Actual block type", new Callable()
            {
                public String a() throws Exception
                {
                    int i = Block.getId(TileEntity.this.world.getType(TileEntity.this.position).getBlock());

                    try
                    {
                        return String.format("ID #%d (%s // %s)", new Object[] {Integer.valueOf(i), Block.getById(i).a(), Block.getById(i).getClass().getCanonicalName()});
                    }
                    catch (Throwable var2)
                    {
                        return "ID #" + i;
                    }
                }
                public Object call() throws Exception
                {
                    return this.a();
                }
            });
            p_a_1_.a("Actual block data value", new Callable()
            {
                public String a() throws Exception
                {
                    IBlockData iblockdata = TileEntity.this.world.getType(TileEntity.this.position);
                    int i = iblockdata.getBlock().toLegacyData(iblockdata);

                    if (i < 0)
                    {
                        return "Unknown? (Got " + i + ")";
                    }
                    else
                    {
                        String s = String.format("%4s", new Object[] {Integer.toBinaryString(i)}).replace(" ", "0");
                        return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] {Integer.valueOf(i), s});
                    }
                }
                public Object call() throws Exception
                {
                    return this.a();
                }
            });
        }
    }

    public void a(BlockPosition p_a_1_)
    {
        this.position = p_a_1_;
    }

    public boolean F()
    {
        return false;
    }

    public InventoryHolder getOwner()
    {
        if (this.world == null)
        {
            return null;
        }
        else
        {
            org.bukkit.block.Block block = this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ());

            if (block == null)
            {
                Bukkit.getLogger().log(Level.WARNING, "No block for owner at %s %d %d %d", new Object[] {this.world.getWorld(), Integer.valueOf(this.position.getX()), Integer.valueOf(this.position.getY()), Integer.valueOf(this.position.getZ())});
                return null;
            }
            else
            {
                org.bukkit.block.BlockState blockstate = block.getState();
                return blockstate instanceof InventoryHolder ? (InventoryHolder)blockstate : null;
            }
        }
    }
}
