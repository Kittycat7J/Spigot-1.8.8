package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.World;

public interface IComplex {
   World a();

   boolean a(EntityComplexPart var1, DamageSource var2, float var3);
}
