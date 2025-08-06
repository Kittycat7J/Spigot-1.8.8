package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class Vector3f {
   protected final float x;
   protected final float y;
   protected final float z;

   public Vector3f(float p_i902_1_, float p_i902_2_, float p_i902_3_) {
      this.x = p_i902_1_;
      this.y = p_i902_2_;
      this.z = p_i902_3_;
   }

   public Vector3f(NBTTagList p_i903_1_) {
      this.x = p_i903_1_.e(0);
      this.y = p_i903_1_.e(1);
      this.z = p_i903_1_.e(2);
   }

   public NBTTagList a() {
      NBTTagList nbttaglist = new NBTTagList();
      nbttaglist.add(new NBTTagFloat(this.x));
      nbttaglist.add(new NBTTagFloat(this.y));
      nbttaglist.add(new NBTTagFloat(this.z));
      return nbttaglist;
   }

   public boolean equals(Object p_equals_1_) {
      if(!(p_equals_1_ instanceof Vector3f)) {
         return false;
      } else {
         Vector3f vector3f = (Vector3f)p_equals_1_;
         return this.x == vector3f.x && this.y == vector3f.y && this.z == vector3f.z;
      }
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getZ() {
      return this.z;
   }
}
