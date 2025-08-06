package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenFactory
{
    private static final Logger a = LogManager.getLogger();
    private static Map < String, Class <? extends StructureStart >> b = Maps. < String, Class <? extends StructureStart >> newHashMap();
    private static Map < Class <? extends StructureStart > , String > c = Maps. < Class <? extends StructureStart > , String > newHashMap();
    private static Map < String, Class <? extends StructurePiece >> d = Maps. < String, Class <? extends StructurePiece >> newHashMap();
    private static Map < Class <? extends StructurePiece > , String > e = Maps. < Class <? extends StructurePiece > , String > newHashMap();

    private static void b(Class <? extends StructureStart > p_b_0_, String p_b_1_)
    {
        b.put(p_b_1_, p_b_0_);
        c.put(p_b_0_, p_b_1_);
    }

    static void a(Class <? extends StructurePiece > p_a_0_, String p_a_1_)
    {
        d.put(p_a_1_, p_a_0_);
        e.put(p_a_0_, p_a_1_);
    }

    public static String a(StructureStart p_a_0_)
    {
        return (String)c.get(p_a_0_.getClass());
    }

    public static String a(StructurePiece p_a_0_)
    {
        return (String)e.get(p_a_0_.getClass());
    }

    public static StructureStart a(NBTTagCompound p_a_0_, World p_a_1_)
    {
        StructureStart structurestart = null;

        try
        {
            Class oclass = (Class)b.get(p_a_0_.getString("id"));

            if (oclass != null)
            {
                structurestart = (StructureStart)oclass.newInstance();
            }
        }
        catch (Exception exception)
        {
            a.warn("Failed Start with id " + p_a_0_.getString("id"));
            exception.printStackTrace();
        }

        if (structurestart != null)
        {
            structurestart.a(p_a_1_, p_a_0_);
        }
        else
        {
            a.warn("Skipping Structure with id " + p_a_0_.getString("id"));
        }

        return structurestart;
    }

    public static StructurePiece b(NBTTagCompound p_b_0_, World p_b_1_)
    {
        StructurePiece structurepiece = null;

        try
        {
            Class oclass = (Class)d.get(p_b_0_.getString("id"));

            if (oclass != null)
            {
                structurepiece = (StructurePiece)oclass.newInstance();
            }
        }
        catch (Exception exception)
        {
            a.warn("Failed Piece with id " + p_b_0_.getString("id"));
            exception.printStackTrace();
        }

        if (structurepiece != null)
        {
            structurepiece.a(p_b_1_, p_b_0_);
        }
        else
        {
            a.warn("Skipping Piece with id " + p_b_0_.getString("id"));
        }

        return structurepiece;
    }

    static
    {
        b(WorldGenMineshaftStart.class, "Mineshaft");
        b(WorldGenVillage.WorldGenVillageStart.class, "Village");
        b(WorldGenNether.WorldGenNetherStart.class, "Fortress");
        b(WorldGenStronghold.WorldGenStronghold2Start.class, "Stronghold");
        b(WorldGenLargeFeature.WorldGenLargeFeatureStart.class, "Temple");
        b(WorldGenMonument.WorldGenMonumentStart.class, "Monument");
        WorldGenMineshaftPieces.a();
        WorldGenVillagePieces.a();
        WorldGenNetherPieces.a();
        WorldGenStrongholdPieces.a();
        WorldGenRegistration.a();
        WorldGenMonumentPieces.a();
    }
}
