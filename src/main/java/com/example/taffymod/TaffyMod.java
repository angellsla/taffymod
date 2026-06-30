package com.example.taffymod;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;

@Mod("taffymod")
public class TaffyMod {

    public TaffyMod(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("taffy")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("message", StringArgumentType.greedyString())
                                        .executes(this::executePopup)
                                )
                        )
        );
    }

    private int executePopup(CommandContext<CommandSourceStack> context) {
        try {
            Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "target");
            String msg = StringArgumentType.getString(context, "message");

            for (ServerPlayer player : targets) {
                PacketDistributor.sendToPlayer(player, new TaffyPopupPayload(msg));
            }

            context.getSource().sendSuccess(() ->
                    Component.literal("§a[成功] 已向 " + targets.size() + " 名玩家发送弹幕：" + msg), false);
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("§c执行失败：" + e.getMessage()));
        }
        return Command.SINGLE_SUCCESS;
    }
}
