package net.minecraft.server.v1_8_R3;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionEntityNotFound;
import net.minecraft.server.v1_8_R3.ExceptionInvalidNumber;
import net.minecraft.server.v1_8_R3.ExceptionPlayerNotFound;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommand;
import net.minecraft.server.v1_8_R3.ICommandDispatcher;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerSelector;

public abstract class CommandAbstract implements ICommand {
   private static ICommandDispatcher a;

   public int a() {
      return 4;
   }

   public List<String> b() {
      return Collections.emptyList();
   }

   public boolean canUse(ICommandListener p_canUse_1_) {
      return p_canUse_1_.a(this.a(), this.getCommand());
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return null;
   }

   public static int a(String p_a_0_) throws ExceptionInvalidNumber {
      try {
         return Integer.parseInt(p_a_0_);
      } catch (NumberFormatException var2) {
         throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[]{p_a_0_});
      }
   }

   public static int a(String p_a_0_, int p_a_1_) throws ExceptionInvalidNumber {
      return a(p_a_0_, p_a_1_, Integer.MAX_VALUE);
   }

   public static int a(String p_a_0_, int p_a_1_, int p_a_2_) throws ExceptionInvalidNumber {
      int i = a(p_a_0_);
      if(i < p_a_1_) {
         throw new ExceptionInvalidNumber("commands.generic.num.tooSmall", new Object[]{Integer.valueOf(i), Integer.valueOf(p_a_1_)});
      } else if(i > p_a_2_) {
         throw new ExceptionInvalidNumber("commands.generic.num.tooBig", new Object[]{Integer.valueOf(i), Integer.valueOf(p_a_2_)});
      } else {
         return i;
      }
   }

   public static long b(String p_b_0_) throws ExceptionInvalidNumber {
      try {
         return Long.parseLong(p_b_0_);
      } catch (NumberFormatException var2) {
         throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[]{p_b_0_});
      }
   }

   public static long a(String p_a_0_, long p_a_1_, long p_a_3_) throws ExceptionInvalidNumber {
      long i = b(p_a_0_);
      if(i < p_a_1_) {
         throw new ExceptionInvalidNumber("commands.generic.num.tooSmall", new Object[]{Long.valueOf(i), Long.valueOf(p_a_1_)});
      } else if(i > p_a_3_) {
         throw new ExceptionInvalidNumber("commands.generic.num.tooBig", new Object[]{Long.valueOf(i), Long.valueOf(p_a_3_)});
      } else {
         return i;
      }
   }

   public static BlockPosition a(ICommandListener p_a_0_, String[] p_a_1_, int p_a_2_, boolean p_a_3_) throws ExceptionInvalidNumber {
      BlockPosition blockposition = p_a_0_.getChunkCoordinates();
      return new BlockPosition(b((double)blockposition.getX(), p_a_1_[p_a_2_], -30000000, 30000000, p_a_3_), b((double)blockposition.getY(), p_a_1_[p_a_2_ + 1], 0, 256, false), b((double)blockposition.getZ(), p_a_1_[p_a_2_ + 2], -30000000, 30000000, p_a_3_));
   }

   public static double c(String p_c_0_) throws ExceptionInvalidNumber {
      try {
         double d0 = Double.parseDouble(p_c_0_);
         if(!Doubles.isFinite(d0)) {
            throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[]{p_c_0_});
         } else {
            return d0;
         }
      } catch (NumberFormatException var4) {
         throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[]{p_c_0_});
      }
   }

   public static double a(String p_a_0_, double p_a_1_) throws ExceptionInvalidNumber {
      return a(p_a_0_, p_a_1_, Double.MAX_VALUE);
   }

   public static double a(String p_a_0_, double p_a_1_, double p_a_3_) throws ExceptionInvalidNumber {
      double d0 = c(p_a_0_);
      if(d0 < p_a_1_) {
         throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[]{Double.valueOf(d0), Double.valueOf(p_a_1_)});
      } else if(d0 > p_a_3_) {
         throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[]{Double.valueOf(d0), Double.valueOf(p_a_3_)});
      } else {
         return d0;
      }
   }

   public static boolean d(String p_d_0_) throws CommandException {
      if(!p_d_0_.equals("true") && !p_d_0_.equals("1")) {
         if(!p_d_0_.equals("false") && !p_d_0_.equals("0")) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[]{p_d_0_});
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public static EntityPlayer b(ICommandListener p_b_0_) throws ExceptionPlayerNotFound {
      if(p_b_0_ instanceof EntityPlayer) {
         return (EntityPlayer)p_b_0_;
      } else {
         throw new ExceptionPlayerNotFound("You must specify which player you wish to perform this action on.", new Object[0]);
      }
   }

   public static EntityPlayer a(ICommandListener p_a_0_, String p_a_1_) throws ExceptionPlayerNotFound {
      EntityPlayer entityplayer = PlayerSelector.getPlayer(p_a_0_, p_a_1_);
      if(entityplayer == null) {
         try {
            entityplayer = MinecraftServer.getServer().getPlayerList().a(UUID.fromString(p_a_1_));
         } catch (IllegalArgumentException var4) {
            ;
         }
      }

      if(entityplayer == null) {
         entityplayer = MinecraftServer.getServer().getPlayerList().getPlayer(p_a_1_);
      }

      if(entityplayer == null) {
         throw new ExceptionPlayerNotFound();
      } else {
         return entityplayer;
      }
   }

   public static Entity b(ICommandListener p_b_0_, String p_b_1_) throws ExceptionEntityNotFound {
      return a(p_b_0_, p_b_1_, Entity.class);
   }

   public static <T extends Entity> T a(ICommandListener p_a_0_, String p_a_1_, Class<? extends T> p_a_2_) throws ExceptionEntityNotFound {
      Object object = PlayerSelector.getEntity(p_a_0_, p_a_1_, p_a_2_);
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      if(object == null) {
         object = minecraftserver.getPlayerList().getPlayer(p_a_1_);
      }

      if(object == null) {
         try {
            UUID uuid = UUID.fromString(p_a_1_);
            object = minecraftserver.a(uuid);
            if(object == null) {
               object = minecraftserver.getPlayerList().a(uuid);
            }
         } catch (IllegalArgumentException var6) {
            throw new ExceptionEntityNotFound("commands.generic.entity.invalidUuid", new Object[0]);
         }
      }

      if(object != null && p_a_2_.isAssignableFrom(object.getClass())) {
         return (T)object;
      } else {
         throw new ExceptionEntityNotFound();
      }
   }

   public static List<Entity> c(ICommandListener p_c_0_, String p_c_1_) throws ExceptionEntityNotFound {
      return (List<Entity>)(PlayerSelector.isPattern(p_c_1_)?PlayerSelector.getPlayers(p_c_0_, p_c_1_, Entity.class):Lists.newArrayList(new Entity[]{b(p_c_0_, p_c_1_)}));
   }

   public static String d(ICommandListener p_d_0_, String p_d_1_) throws ExceptionPlayerNotFound {
      try {
         return a(p_d_0_, p_d_1_).getName();
      } catch (ExceptionPlayerNotFound exceptionplayernotfound) {
         if(PlayerSelector.isPattern(p_d_1_)) {
            throw exceptionplayernotfound;
         } else {
            return p_d_1_;
         }
      }
   }

   public static String e(ICommandListener p_e_0_, String p_e_1_) throws ExceptionEntityNotFound {
      try {
         return a(p_e_0_, p_e_1_).getName();
      } catch (ExceptionPlayerNotFound var5) {
         try {
            return b(p_e_0_, p_e_1_).getUniqueID().toString();
         } catch (ExceptionEntityNotFound exceptionentitynotfound) {
            if(PlayerSelector.isPattern(p_e_1_)) {
               throw exceptionentitynotfound;
            } else {
               return p_e_1_;
            }
         }
      }
   }

   public static IChatBaseComponent a(ICommandListener p_a_0_, String[] p_a_1_, int p_a_2_) throws ExceptionPlayerNotFound {
      return b(p_a_0_, p_a_1_, p_a_2_, false);
   }

   public static IChatBaseComponent b(ICommandListener p_b_0_, String[] p_b_1_, int p_b_2_, boolean p_b_3_) throws ExceptionPlayerNotFound {
      ChatComponentText chatcomponenttext = new ChatComponentText("");

      for(int i = p_b_2_; i < p_b_1_.length; ++i) {
         if(i > p_b_2_) {
            chatcomponenttext.a(" ");
         }

         Object object = new ChatComponentText(p_b_1_[i]);
         if(p_b_3_) {
            IChatBaseComponent ichatbasecomponent = PlayerSelector.getPlayerNames(p_b_0_, p_b_1_[i]);
            if(ichatbasecomponent == null) {
               if(PlayerSelector.isPattern(p_b_1_[i])) {
                  throw new ExceptionPlayerNotFound();
               }
            } else {
               object = ichatbasecomponent;
            }
         }

         chatcomponenttext.addSibling((IChatBaseComponent)object);
      }

      return chatcomponenttext;
   }

   public static String a(String[] p_a_0_, int p_a_1_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(int i = p_a_1_; i < p_a_0_.length; ++i) {
         if(i > p_a_1_) {
            stringbuilder.append(" ");
         }

         String s = p_a_0_[i];
         stringbuilder.append(s);
      }

      return stringbuilder.toString();
   }

   public static CommandAbstract.CommandNumber a(double p_a_0_, String p_a_2_, boolean p_a_3_) throws ExceptionInvalidNumber {
      return a(p_a_0_, p_a_2_, -30000000, 30000000, p_a_3_);
   }

   public static CommandAbstract.CommandNumber a(double p_a_0_, String p_a_2_, int p_a_3_, int p_a_4_, boolean p_a_5_) throws ExceptionInvalidNumber {
      boolean flag = p_a_2_.startsWith("~");
      if(flag && Double.isNaN(p_a_0_)) {
         throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[]{Double.valueOf(p_a_0_)});
      } else {
         double d0 = 0.0D;
         if(!flag || p_a_2_.length() > 1) {
            boolean flag1 = p_a_2_.contains(".");
            if(flag) {
               p_a_2_ = p_a_2_.substring(1);
            }

            d0 += c(p_a_2_);
            if(!flag1 && !flag && p_a_5_) {
               d0 += 0.5D;
            }
         }

         if(p_a_3_ != 0 || p_a_4_ != 0) {
            if(d0 < (double)p_a_3_) {
               throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[]{Double.valueOf(d0), Integer.valueOf(p_a_3_)});
            }

            if(d0 > (double)p_a_4_) {
               throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[]{Double.valueOf(d0), Integer.valueOf(p_a_4_)});
            }
         }

         return new CommandAbstract.CommandNumber(d0 + (flag?p_a_0_:0.0D), d0, flag);
      }
   }

   public static double b(double p_b_0_, String p_b_2_, boolean p_b_3_) throws ExceptionInvalidNumber {
      return b(p_b_0_, p_b_2_, -30000000, 30000000, p_b_3_);
   }

   public static double b(double p_b_0_, String p_b_2_, int p_b_3_, int p_b_4_, boolean p_b_5_) throws ExceptionInvalidNumber {
      boolean flag = p_b_2_.startsWith("~");
      if(flag && Double.isNaN(p_b_0_)) {
         throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[]{Double.valueOf(p_b_0_)});
      } else {
         double d0 = flag?p_b_0_:0.0D;
         if(!flag || p_b_2_.length() > 1) {
            boolean flag1 = p_b_2_.contains(".");
            if(flag) {
               p_b_2_ = p_b_2_.substring(1);
            }

            d0 += c(p_b_2_);
            if(!flag1 && !flag && p_b_5_) {
               d0 += 0.5D;
            }
         }

         if(p_b_3_ != 0 || p_b_4_ != 0) {
            if(d0 < (double)p_b_3_) {
               throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[]{Double.valueOf(d0), Integer.valueOf(p_b_3_)});
            }

            if(d0 > (double)p_b_4_) {
               throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[]{Double.valueOf(d0), Integer.valueOf(p_b_4_)});
            }
         }

         return d0;
      }
   }

   public static Item f(ICommandListener p_f_0_, String p_f_1_) throws ExceptionInvalidNumber {
      MinecraftKey minecraftkey = new MinecraftKey(p_f_1_);
      Item item = (Item)Item.REGISTRY.get(minecraftkey);
      if(item == null) {
         throw new ExceptionInvalidNumber("commands.give.item.notFound", new Object[]{minecraftkey});
      } else {
         return item;
      }
   }

   public static Block g(ICommandListener p_g_0_, String p_g_1_) throws ExceptionInvalidNumber {
      MinecraftKey minecraftkey = new MinecraftKey(p_g_1_);
      if(!Block.REGISTRY.d(minecraftkey)) {
         throw new ExceptionInvalidNumber("commands.give.block.notFound", new Object[]{minecraftkey});
      } else {
         Block block = (Block)Block.REGISTRY.get(minecraftkey);
         if(block == null) {
            throw new ExceptionInvalidNumber("commands.give.block.notFound", new Object[]{minecraftkey});
         } else {
            return block;
         }
      }
   }

   public static String a(Object[] p_a_0_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(int i = 0; i < p_a_0_.length; ++i) {
         String s = p_a_0_[i].toString();
         if(i > 0) {
            if(i == p_a_0_.length - 1) {
               stringbuilder.append(" and ");
            } else {
               stringbuilder.append(", ");
            }
         }

         stringbuilder.append(s);
      }

      return stringbuilder.toString();
   }

   public static IChatBaseComponent a(List<IChatBaseComponent> p_a_0_) {
      ChatComponentText chatcomponenttext = new ChatComponentText("");

      for(int i = 0; i < p_a_0_.size(); ++i) {
         if(i > 0) {
            if(i == p_a_0_.size() - 1) {
               chatcomponenttext.a(" and ");
            } else if(i > 0) {
               chatcomponenttext.a(", ");
            }
         }

         chatcomponenttext.addSibling((IChatBaseComponent)p_a_0_.get(i));
      }

      return chatcomponenttext;
   }

   public static String a(Collection<String> p_a_0_) {
      return a(p_a_0_.toArray(new String[p_a_0_.size()]));
   }

   public static List<String> a(String[] p_a_0_, int p_a_1_, BlockPosition p_a_2_) {
      if(p_a_2_ == null) {
         return null;
      } else {
         int i = p_a_0_.length - 1;
         String s;
         if(i == p_a_1_) {
            s = Integer.toString(p_a_2_.getX());
         } else if(i == p_a_1_ + 1) {
            s = Integer.toString(p_a_2_.getY());
         } else {
            if(i != p_a_1_ + 2) {
               return null;
            }

            s = Integer.toString(p_a_2_.getZ());
         }

         return Lists.newArrayList(new String[]{s});
      }
   }

   public static List<String> b(String[] p_b_0_, int p_b_1_, BlockPosition p_b_2_) {
      if(p_b_2_ == null) {
         return null;
      } else {
         int i = p_b_0_.length - 1;
         String s;
         if(i == p_b_1_) {
            s = Integer.toString(p_b_2_.getX());
         } else {
            if(i != p_b_1_ + 1) {
               return null;
            }

            s = Integer.toString(p_b_2_.getZ());
         }

         return Lists.newArrayList(new String[]{s});
      }
   }

   public static boolean a(String p_a_0_, String p_a_1_) {
      return p_a_1_.regionMatches(true, 0, p_a_0_, 0, p_a_0_.length());
   }

   public static List<String> a(String[] p_a_0_, String... p_a_1_) {
      return a((String[])p_a_0_, (Collection)Arrays.asList(p_a_1_));
   }

   public static List<String> a(String[] p_a_0_, Collection<?> p_a_1_) {
      String s = p_a_0_[p_a_0_.length - 1];
      ArrayList arraylist = Lists.newArrayList();
      if(!p_a_1_.isEmpty()) {
         for(String s1 : Iterables.transform(p_a_1_, Functions.toStringFunction())) {
            if(a(s, s1)) {
               arraylist.add(s1);
            }
         }

         if(arraylist.isEmpty()) {
            for(Object object : p_a_1_) {
               if(object instanceof MinecraftKey && a(s, ((MinecraftKey)object).a())) {
                  arraylist.add(String.valueOf(object));
               }
            }
         }
      }

      return arraylist;
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return false;
   }

   public static void a(ICommandListener p_a_0_, ICommand p_a_1_, String p_a_2_, Object... p_a_3_) {
      a(p_a_0_, p_a_1_, 0, p_a_2_, p_a_3_);
   }

   public static void a(ICommandListener p_a_0_, ICommand p_a_1_, int p_a_2_, String p_a_3_, Object... p_a_4_) {
      if(a != null) {
         a.a(p_a_0_, p_a_1_, p_a_2_, p_a_3_, p_a_4_);
      }

   }

   public static void a(ICommandDispatcher p_a_0_) {
      a = p_a_0_;
   }

   public int a(ICommand p_a_1_) {
      return this.getCommand().compareTo(p_a_1_.getCommand());
   }

   public static class CommandNumber {
      private final double a;
      private final double b;
      private final boolean c;

      protected CommandNumber(double p_i1033_1_, double p_i1033_3_, boolean p_i1033_5_) {
         this.a = p_i1033_1_;
         this.b = p_i1033_3_;
         this.c = p_i1033_5_;
      }

      public double a() {
         return this.a;
      }

      public double b() {
         return this.b;
      }

      public boolean c() {
         return this.c;
      }
   }
}
