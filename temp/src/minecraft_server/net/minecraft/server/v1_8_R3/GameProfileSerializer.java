package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.UtilColor;

public final class GameProfileSerializer {
   public static GameProfile deserialize(NBTTagCompound p_deserialize_0_) {
      String s = null;
      String s1 = null;
      if(p_deserialize_0_.hasKeyOfType("Name", 8)) {
         s = p_deserialize_0_.getString("Name");
      }

      if(p_deserialize_0_.hasKeyOfType("Id", 8)) {
         s1 = p_deserialize_0_.getString("Id");
      }

      if(UtilColor.b(s) && UtilColor.b(s1)) {
         return null;
      } else {
         UUID uuid;
         try {
            uuid = UUID.fromString(s1);
         } catch (Throwable var12) {
            uuid = null;
         }

         GameProfile gameprofile = new GameProfile(uuid, s);
         if(p_deserialize_0_.hasKeyOfType("Properties", 10)) {
            NBTTagCompound nbttagcompound = p_deserialize_0_.getCompound("Properties");

            for(String s2 : nbttagcompound.c()) {
               NBTTagList nbttaglist = nbttagcompound.getList(s2, 10);

               for(int i = 0; i < nbttaglist.size(); ++i) {
                  NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
                  String s3 = nbttagcompound1.getString("Value");
                  if(nbttagcompound1.hasKeyOfType("Signature", 8)) {
                     gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
                  } else {
                     gameprofile.getProperties().put(s2, new Property(s2, s3));
                  }
               }
            }
         }

         return gameprofile;
      }
   }

   public static NBTTagCompound serialize(NBTTagCompound p_serialize_0_, GameProfile p_serialize_1_) {
      if(!UtilColor.b(p_serialize_1_.getName())) {
         p_serialize_0_.setString("Name", p_serialize_1_.getName());
      }

      if(p_serialize_1_.getId() != null) {
         p_serialize_0_.setString("Id", p_serialize_1_.getId().toString());
      }

      if(!p_serialize_1_.getProperties().isEmpty()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();

         for(String s : p_serialize_1_.getProperties().keySet()) {
            NBTTagList nbttaglist = new NBTTagList();

            for(Property property : p_serialize_1_.getProperties().get(s)) {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               nbttagcompound1.setString("Value", property.getValue());
               if(property.hasSignature()) {
                  nbttagcompound1.setString("Signature", property.getSignature());
               }

               nbttaglist.add(nbttagcompound1);
            }

            nbttagcompound.set(s, nbttaglist);
         }

         p_serialize_0_.set("Properties", nbttagcompound);
      }

      return p_serialize_0_;
   }

   public static boolean a(NBTBase p_a_0_, NBTBase p_a_1_, boolean p_a_2_) {
      if(p_a_0_ == p_a_1_) {
         return true;
      } else if(p_a_0_ == null) {
         return true;
      } else if(p_a_1_ == null) {
         return false;
      } else if(!p_a_0_.getClass().equals(p_a_1_.getClass())) {
         return false;
      } else if(p_a_0_ instanceof NBTTagCompound) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)p_a_0_;
         NBTTagCompound nbttagcompound1 = (NBTTagCompound)p_a_1_;

         for(String s : nbttagcompound.c()) {
            NBTBase nbtbase1 = nbttagcompound.get(s);
            if(!a(nbtbase1, nbttagcompound1.get(s), p_a_2_)) {
               return false;
            }
         }

         return true;
      } else if(p_a_0_ instanceof NBTTagList && p_a_2_) {
         NBTTagList nbttaglist = (NBTTagList)p_a_0_;
         NBTTagList nbttaglist1 = (NBTTagList)p_a_1_;
         if(nbttaglist.size() == 0) {
            return nbttaglist1.size() == 0;
         } else {
            for(int i = 0; i < nbttaglist.size(); ++i) {
               NBTBase nbtbase = nbttaglist.g(i);
               boolean flag = false;

               for(int j = 0; j < nbttaglist1.size(); ++j) {
                  if(a(nbtbase, nbttaglist1.g(j), p_a_2_)) {
                     flag = true;
                     break;
                  }
               }

               if(!flag) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return p_a_0_.equals(p_a_1_);
      }
   }
}
