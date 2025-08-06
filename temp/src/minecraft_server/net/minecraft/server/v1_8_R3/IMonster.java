package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.IAnimal;

public interface IMonster extends IAnimal {
   Predicate<Entity> d = new Predicate<Entity>() {
      public boolean a(Entity p_a_1_) {
         return p_a_1_ instanceof IMonster;
      }
   };
   Predicate<Entity> e = new Predicate<Entity>() {
      public boolean a(Entity p_a_1_) {
         return p_a_1_ instanceof IMonster && !p_a_1_.isInvisible();
      }
   };
}
