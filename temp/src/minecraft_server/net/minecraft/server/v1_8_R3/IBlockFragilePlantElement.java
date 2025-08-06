package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;

public interface IBlockFragilePlantElement {
   boolean a(World var1, BlockPosition var2, IBlockData var3, boolean var4);

   boolean a(World var1, Random var2, BlockPosition var3, IBlockData var4);

   void b(World var1, Random var2, BlockPosition var3, IBlockData var4);
}
