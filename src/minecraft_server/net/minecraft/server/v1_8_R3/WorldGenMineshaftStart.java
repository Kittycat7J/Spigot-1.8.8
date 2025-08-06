package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenMineshaftStart extends StructureStart
{
    public WorldGenMineshaftStart()
    {
    }

    public WorldGenMineshaftStart(World p_i727_1_, Random p_i727_2_, int p_i727_3_, int p_i727_4_)
    {
        super(p_i727_3_, p_i727_4_);
        WorldGenMineshaftPieces.WorldGenMineshaftRoom worldgenmineshaftpieces$worldgenmineshaftroom = new WorldGenMineshaftPieces.WorldGenMineshaftRoom(0, p_i727_2_, (p_i727_3_ << 4) + 2, (p_i727_4_ << 4) + 2);
        this.a.add(worldgenmineshaftpieces$worldgenmineshaftroom);
        worldgenmineshaftpieces$worldgenmineshaftroom.a((StructurePiece)worldgenmineshaftpieces$worldgenmineshaftroom, this.a, (Random)p_i727_2_);
        this.c();
        this.a(p_i727_1_, p_i727_2_, 10);
    }
}
