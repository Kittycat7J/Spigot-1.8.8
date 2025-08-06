package net.minecraft.server.v1_8_R3;

import com.google.common.base.Joiner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PlayerSelector;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.command.VanillaCommandWrapper;

public abstract class CommandBlockListenerAbstract implements ICommandListener {
   private static final SimpleDateFormat a = new SimpleDateFormat("HH:mm:ss");
   private int b;
   private boolean c = true;
   private IChatBaseComponent d = null;
   private String e = "";
   private String f = "@";
   private final CommandObjectiveExecutor g = new CommandObjectiveExecutor();
   protected CommandSender sender;

   public int j() {
      return this.b;
   }

   public IChatBaseComponent k() {
      return this.d;
   }

   public void a(NBTTagCompound p_a_1_) {
      p_a_1_.setString("Command", this.e);
      p_a_1_.setInt("SuccessCount", this.b);
      p_a_1_.setString("CustomName", this.f);
      p_a_1_.setBoolean("TrackOutput", this.c);
      if(this.d != null && this.c) {
         p_a_1_.setString("LastOutput", IChatBaseComponent.ChatSerializer.a(this.d));
      }

      this.g.b(p_a_1_);
   }

   public void b(NBTTagCompound p_b_1_) {
      this.e = p_b_1_.getString("Command");
      this.b = p_b_1_.getInt("SuccessCount");
      if(p_b_1_.hasKeyOfType("CustomName", 8)) {
         this.f = p_b_1_.getString("CustomName");
      }

      if(p_b_1_.hasKeyOfType("TrackOutput", 1)) {
         this.c = p_b_1_.getBoolean("TrackOutput");
      }

      if(p_b_1_.hasKeyOfType("LastOutput", 8) && this.c) {
         this.d = IChatBaseComponent.ChatSerializer.a(p_b_1_.getString("LastOutput"));
      }

      this.g.a(p_b_1_);
   }

   public boolean a(int p_a_1_, String p_a_2_) {
      return p_a_1_ <= 2;
   }

   public void setCommand(String p_setCommand_1_) {
      this.e = p_setCommand_1_;
      this.b = 0;
   }

   public String getCommand() {
      return this.e;
   }

   public void a(World p_a_1_) {
      if(p_a_1_.isClientSide) {
         this.b = 0;
      }

      MinecraftServer minecraftserver = MinecraftServer.getServer();
      if(minecraftserver != null && minecraftserver.O() && minecraftserver.getEnableCommandBlock()) {
         minecraftserver.getCommandHandler();

         try {
            this.d = null;
            this.b = executeCommand(this, this.sender, this.e);
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Executing command block");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Command to be executed");
            crashreportsystemdetails.a("Command", new Callable() {
               public String a() throws Exception {
                  return CommandBlockListenerAbstract.this.getCommand();
               }

               public Object call() throws Exception {
                  return this.a();
               }
            });
            crashreportsystemdetails.a("Name", new Callable() {
               public String a() throws Exception {
                  return CommandBlockListenerAbstract.this.getName();
               }

               public Object call() throws Exception {
                  return this.a();
               }
            });
            throw new ReportedException(crashreport);
         }
      } else {
         this.b = 0;
      }

   }

   public static int executeCommand(ICommandListener p_executeCommand_0_, CommandSender p_executeCommand_1_, String p_executeCommand_2_) {
      SimpleCommandMap simplecommandmap = p_executeCommand_0_.getWorld().getServer().getCommandMap();
      Joiner joiner = Joiner.on(" ");
      if(p_executeCommand_2_.startsWith("/")) {
         p_executeCommand_2_ = p_executeCommand_2_.substring(1);
      }

      String[] astring = p_executeCommand_2_.split(" ");
      ArrayList<String[]> arraylist = new ArrayList();
      String s = astring[0];
      if(s.startsWith("minecraft:")) {
         s = s.substring("minecraft:".length());
      }

      if(s.startsWith("bukkit:")) {
         s = s.substring("bukkit:".length());
      }

      if(!s.equalsIgnoreCase("stop") && !s.equalsIgnoreCase("kick") && !s.equalsIgnoreCase("op") && !s.equalsIgnoreCase("deop") && !s.equalsIgnoreCase("ban") && !s.equalsIgnoreCase("ban-ip") && !s.equalsIgnoreCase("pardon") && !s.equalsIgnoreCase("pardon-ip") && !s.equalsIgnoreCase("reload")) {
         if(p_executeCommand_0_.getWorld().players.isEmpty()) {
            return 0;
         } else {
            Command command = simplecommandmap.getCommand(astring[0]);
            if(p_executeCommand_0_.getWorld().getServer().getCommandBlockOverride(astring[0])) {
               command = simplecommandmap.getCommand("minecraft:" + astring[0]);
            }

            if(command instanceof VanillaCommandWrapper) {
               p_executeCommand_2_ = p_executeCommand_2_.trim();
               if(p_executeCommand_2_.startsWith("/")) {
                  p_executeCommand_2_ = p_executeCommand_2_.substring(1);
               }

               String[] astring1 = p_executeCommand_2_.split(" ");
               astring1 = VanillaCommandWrapper.dropFirstArgument(astring1);
               return !((VanillaCommandWrapper)command).testPermission(p_executeCommand_1_)?0:((VanillaCommandWrapper)command).dispatchVanillaCommand(p_executeCommand_1_, p_executeCommand_0_, astring1);
            } else if(simplecommandmap.getCommand(astring[0]) == null) {
               return 0;
            } else {
               arraylist.add(astring);
               WorldServer[] aworldserver = MinecraftServer.getServer().worldServer;
               MinecraftServer minecraftserver = MinecraftServer.getServer();
               minecraftserver.worldServer = new WorldServer[minecraftserver.worlds.size()];
               minecraftserver.worldServer[0] = (WorldServer)p_executeCommand_0_.getWorld();
               int i = 0;

               for(int j = 1; j < minecraftserver.worldServer.length; ++j) {
                  WorldServer worldserver = (WorldServer)minecraftserver.worlds.get(i++);
                  if(minecraftserver.worldServer[0] == worldserver) {
                     --j;
                  } else {
                     minecraftserver.worldServer[j] = worldserver;
                  }
               }

               try {
                  ArrayList<String[]> arraylist1 = new ArrayList();

                  for(int i1 = 0; i1 < astring.length; ++i1) {
                     if(PlayerSelector.isPattern(astring[i1])) {
                        for(int k = 0; k < arraylist.size(); ++k) {
                           arraylist1.addAll(buildCommands(p_executeCommand_0_, (String[])arraylist.get(k), i1));
                        }

                        ArrayList<String[]> arraylist2 = arraylist;
                        arraylist = arraylist1;
                        arraylist1 = arraylist2;
                        arraylist2.clear();
                     }
                  }
               } finally {
                  MinecraftServer.getServer().worldServer = aworldserver;
               }

               int l = 0;

               for(int j1 = 0; j1 < arraylist.size(); ++j1) {
                  try {
                     if(simplecommandmap.dispatch(p_executeCommand_1_, joiner.join(Arrays.asList((String[])arraylist.get(j1))))) {
                        ++l;
                     }
                  } catch (Throwable throwable) {
                     if(p_executeCommand_0_.f() instanceof EntityMinecartCommandBlock) {
                        MinecraftServer.getServer().server.getLogger().log(Level.WARNING, String.format("MinecartCommandBlock at (%d,%d,%d) failed to handle command", new Object[]{Integer.valueOf(p_executeCommand_0_.getChunkCoordinates().getX()), Integer.valueOf(p_executeCommand_0_.getChunkCoordinates().getY()), Integer.valueOf(p_executeCommand_0_.getChunkCoordinates().getZ())}), throwable);
                     } else if(p_executeCommand_0_ instanceof CommandBlockListenerAbstract) {
                        CommandBlockListenerAbstract commandblocklistenerabstract = (CommandBlockListenerAbstract)p_executeCommand_0_;
                        MinecraftServer.getServer().server.getLogger().log(Level.WARNING, String.format("CommandBlock at (%d,%d,%d) failed to handle command", new Object[]{Integer.valueOf(commandblocklistenerabstract.getChunkCoordinates().getX()), Integer.valueOf(commandblocklistenerabstract.getChunkCoordinates().getY()), Integer.valueOf(commandblocklistenerabstract.getChunkCoordinates().getZ())}), throwable);
                     } else {
                        MinecraftServer.getServer().server.getLogger().log(Level.WARNING, String.format("Unknown CommandBlock failed to handle command", new Object[0]), throwable);
                     }
                  }
               }

               return l;
            }
         }
      } else {
         return 0;
      }
   }

   private static ArrayList<String[]> buildCommands(ICommandListener p_buildCommands_0_, String[] p_buildCommands_1_, int p_buildCommands_2_) {
      ArrayList<String[]> arraylist = new ArrayList();
      List<EntityPlayer> list = PlayerSelector.<EntityPlayer>getPlayers(p_buildCommands_0_, p_buildCommands_1_[p_buildCommands_2_], EntityPlayer.class);
      if(list != null) {
         for(EntityPlayer entityplayer : list) {
            if(entityplayer.world == p_buildCommands_0_.getWorld()) {
               String[] astring = (String[])p_buildCommands_1_.clone();
               astring[p_buildCommands_2_] = entityplayer.getName();
               arraylist.add(astring);
            }
         }
      }

      return arraylist;
   }

   public String getName() {
      return this.f;
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      return new ChatComponentText(this.getName());
   }

   public void setName(String p_setName_1_) {
      this.f = p_setName_1_;
   }

   public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
      if(this.c && this.getWorld() != null && !this.getWorld().isClientSide) {
         this.d = (new ChatComponentText("[" + a.format(new Date()) + "] ")).addSibling(p_sendMessage_1_);
         this.h();
      }

   }

   public boolean getSendCommandFeedback() {
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      return minecraftserver == null || !minecraftserver.O() || minecraftserver.worldServer[0].getGameRules().getBoolean("commandBlockOutput");
   }

   public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_) {
      this.g.a(this, p_a_1_, p_a_2_);
   }

   public abstract void h();

   public void b(IChatBaseComponent p_b_1_) {
      this.d = p_b_1_;
   }

   public void a(boolean p_a_1_) {
      this.c = p_a_1_;
   }

   public boolean m() {
      return this.c;
   }

   public boolean a(EntityHuman p_a_1_) {
      if(!p_a_1_.abilities.canInstantlyBuild) {
         return false;
      } else {
         if(p_a_1_.getWorld().isClientSide) {
            p_a_1_.a(this);
         }

         return true;
      }
   }

   public CommandObjectiveExecutor n() {
      return this.g;
   }
}
