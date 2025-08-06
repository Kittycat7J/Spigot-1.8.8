package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandAchievement;
import net.minecraft.server.v1_8_R3.CommandBan;
import net.minecraft.server.v1_8_R3.CommandBanIp;
import net.minecraft.server.v1_8_R3.CommandBanList;
import net.minecraft.server.v1_8_R3.CommandBlockData;
import net.minecraft.server.v1_8_R3.CommandBlockListenerAbstract;
import net.minecraft.server.v1_8_R3.CommandClear;
import net.minecraft.server.v1_8_R3.CommandClone;
import net.minecraft.server.v1_8_R3.CommandDebug;
import net.minecraft.server.v1_8_R3.CommandDeop;
import net.minecraft.server.v1_8_R3.CommandDifficulty;
import net.minecraft.server.v1_8_R3.CommandEffect;
import net.minecraft.server.v1_8_R3.CommandEnchant;
import net.minecraft.server.v1_8_R3.CommandEntityData;
import net.minecraft.server.v1_8_R3.CommandExecute;
import net.minecraft.server.v1_8_R3.CommandFill;
import net.minecraft.server.v1_8_R3.CommandGamemode;
import net.minecraft.server.v1_8_R3.CommandGamemodeDefault;
import net.minecraft.server.v1_8_R3.CommandGamerule;
import net.minecraft.server.v1_8_R3.CommandGive;
import net.minecraft.server.v1_8_R3.CommandHandler;
import net.minecraft.server.v1_8_R3.CommandHelp;
import net.minecraft.server.v1_8_R3.CommandIdleTimeout;
import net.minecraft.server.v1_8_R3.CommandKick;
import net.minecraft.server.v1_8_R3.CommandKill;
import net.minecraft.server.v1_8_R3.CommandList;
import net.minecraft.server.v1_8_R3.CommandMe;
import net.minecraft.server.v1_8_R3.CommandOp;
import net.minecraft.server.v1_8_R3.CommandPardon;
import net.minecraft.server.v1_8_R3.CommandPardonIP;
import net.minecraft.server.v1_8_R3.CommandParticle;
import net.minecraft.server.v1_8_R3.CommandPlaySound;
import net.minecraft.server.v1_8_R3.CommandPublish;
import net.minecraft.server.v1_8_R3.CommandReplaceItem;
import net.minecraft.server.v1_8_R3.CommandSaveAll;
import net.minecraft.server.v1_8_R3.CommandSaveOff;
import net.minecraft.server.v1_8_R3.CommandSaveOn;
import net.minecraft.server.v1_8_R3.CommandSay;
import net.minecraft.server.v1_8_R3.CommandScoreboard;
import net.minecraft.server.v1_8_R3.CommandSeed;
import net.minecraft.server.v1_8_R3.CommandSetBlock;
import net.minecraft.server.v1_8_R3.CommandSetWorldSpawn;
import net.minecraft.server.v1_8_R3.CommandSpawnpoint;
import net.minecraft.server.v1_8_R3.CommandSpreadPlayers;
import net.minecraft.server.v1_8_R3.CommandStats;
import net.minecraft.server.v1_8_R3.CommandStop;
import net.minecraft.server.v1_8_R3.CommandSummon;
import net.minecraft.server.v1_8_R3.CommandTell;
import net.minecraft.server.v1_8_R3.CommandTellRaw;
import net.minecraft.server.v1_8_R3.CommandTestFor;
import net.minecraft.server.v1_8_R3.CommandTestForBlock;
import net.minecraft.server.v1_8_R3.CommandTestForBlocks;
import net.minecraft.server.v1_8_R3.CommandTime;
import net.minecraft.server.v1_8_R3.CommandTitle;
import net.minecraft.server.v1_8_R3.CommandToggleDownfall;
import net.minecraft.server.v1_8_R3.CommandTp;
import net.minecraft.server.v1_8_R3.CommandTrigger;
import net.minecraft.server.v1_8_R3.CommandWeather;
import net.minecraft.server.v1_8_R3.CommandWhitelist;
import net.minecraft.server.v1_8_R3.CommandWorldBorder;
import net.minecraft.server.v1_8_R3.CommandXp;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.ICommand;
import net.minecraft.server.v1_8_R3.ICommandDispatcher;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.RemoteControlCommandListener;
import org.spigotmc.SpigotConfig;

public class CommandDispatcher extends CommandHandler implements ICommandDispatcher {
   public CommandDispatcher() {
      this.a(new CommandTime());
      this.a(new CommandGamemode());
      this.a(new CommandDifficulty());
      this.a(new CommandGamemodeDefault());
      this.a(new CommandKill());
      this.a(new CommandToggleDownfall());
      this.a(new CommandWeather());
      this.a(new CommandXp());
      this.a(new CommandTp());
      this.a(new CommandGive());
      this.a(new CommandReplaceItem());
      this.a(new CommandStats());
      this.a(new CommandEffect());
      this.a(new CommandEnchant());
      this.a(new CommandParticle());
      this.a(new CommandMe());
      this.a(new CommandSeed());
      this.a(new CommandHelp());
      this.a(new CommandDebug());
      this.a(new CommandTell());
      this.a(new CommandSay());
      this.a(new CommandSpawnpoint());
      this.a(new CommandSetWorldSpawn());
      this.a(new CommandGamerule());
      this.a(new CommandClear());
      this.a(new CommandTestFor());
      this.a(new CommandSpreadPlayers());
      this.a(new CommandPlaySound());
      this.a(new CommandScoreboard());
      this.a(new CommandExecute());
      this.a(new CommandTrigger());
      this.a(new CommandAchievement());
      this.a(new CommandSummon());
      this.a(new CommandSetBlock());
      this.a(new CommandFill());
      this.a(new CommandClone());
      this.a(new CommandTestForBlocks());
      this.a(new CommandBlockData());
      this.a(new CommandTestForBlock());
      this.a(new CommandTellRaw());
      this.a(new CommandWorldBorder());
      this.a(new CommandTitle());
      this.a(new CommandEntityData());
      if(MinecraftServer.getServer().ae()) {
         this.a(new CommandOp());
         this.a(new CommandDeop());
         this.a(new CommandStop());
         this.a(new CommandSaveAll());
         this.a(new CommandSaveOff());
         this.a(new CommandSaveOn());
         this.a(new CommandBanIp());
         this.a(new CommandPardonIP());
         this.a(new CommandBan());
         this.a(new CommandBanList());
         this.a(new CommandPardon());
         this.a(new CommandKick());
         this.a(new CommandList());
         this.a(new CommandWhitelist());
         this.a(new CommandIdleTimeout());
      } else {
         this.a(new CommandPublish());
      }

      CommandAbstract.a((ICommandDispatcher)this);
   }

   public void a(ICommandListener p_a_1_, ICommand p_a_2_, int p_a_3_, String p_a_4_, Object... p_a_5_) {
      boolean flag = true;
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      if(!p_a_1_.getSendCommandFeedback()) {
         flag = false;
      }

      ChatMessage chatmessage = new ChatMessage("chat.type.admin", new Object[]{p_a_1_.getName(), new ChatMessage(p_a_4_, p_a_5_)});
      chatmessage.getChatModifier().setColor(EnumChatFormat.GRAY);
      chatmessage.getChatModifier().setItalic(Boolean.valueOf(true));
      if(flag) {
         for(EntityHuman entityhuman : minecraftserver.getPlayerList().v()) {
            if(entityhuman != p_a_1_ && minecraftserver.getPlayerList().isOp(entityhuman.getProfile()) && p_a_2_.canUse(p_a_1_)) {
               boolean flag1 = p_a_1_ instanceof MinecraftServer && MinecraftServer.getServer().r();
               boolean flag2 = p_a_1_ instanceof RemoteControlCommandListener && MinecraftServer.getServer().q();
               if(flag1 || flag2 || !(p_a_1_ instanceof RemoteControlCommandListener) && !(p_a_1_ instanceof MinecraftServer)) {
                  entityhuman.sendMessage(chatmessage);
               }
            }
         }
      }

      if(p_a_1_ != minecraftserver && minecraftserver.worldServer[0].getGameRules().getBoolean("logAdminCommands") && !SpigotConfig.silentCommandBlocks) {
         minecraftserver.sendMessage(chatmessage);
      }

      boolean flag3 = minecraftserver.worldServer[0].getGameRules().getBoolean("sendCommandFeedback");
      if(p_a_1_ instanceof CommandBlockListenerAbstract) {
         flag3 = ((CommandBlockListenerAbstract)p_a_1_).m();
      }

      if((p_a_3_ & 1) != 1 && flag3 || p_a_1_ instanceof MinecraftServer) {
         p_a_1_.sendMessage(new ChatMessage(p_a_4_, p_a_5_));
      }

   }
}
