package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class ShapeDetectorBlock {
   private final World a;
   private final BlockPosition b;
   private final boolean c;
   private IBlockData d;
   private TileEntity e;
   private boolean f;

   public ShapeDetectorBlock(World p_i666_1_, BlockPosition p_i666_2_, boolean p_i666_3_) {
      this.a = p_i666_1_;
      this.b = p_i666_2_;
      this.c = p_i666_3_;
   }

   public IBlockData a() {
      if(this.d == null && (this.c || this.a.isLoaded(this.b))) {
         this.d = this.a.getType(this.b);
      }

      return this.d;
   }

   public TileEntity b() {
      if(this.e == null && !this.f) {
         this.e = this.a.getTileEntity(this.b);
         this.f = true;
      }

      return this.e;
   }

   public BlockPosition getPosition() {
      return this.b;
   }

   public static Predicate<ShapeDetectorBlock> a(final Predicate<IBlockData> p_a_0_) {
      return new Predicate<ShapeDetectorBlock>() {
         public boolean a(ShapeDetectorBlock p_a_1_) {
            return p_a_1_ != null && p_a_0_.apply(p_a_1_.a());
         }
      };
   }
}
