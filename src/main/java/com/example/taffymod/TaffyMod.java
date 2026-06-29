package com.example.taffymod;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.StringArgumentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.util.DistExecutor;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

@Mod("taffymod")
public class TaffyMod {

    public TaffyMod(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerPayloads);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("taffymod")
                .playToClient(TaffyPopupPayload.TYPE, TaffyPopupPayload.STREAM_CODEC, this::handlePopup);
    }

    private void handlePopup(TaffyPopupPayload payload, IPayloadContext context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.put("OptionPane.messageFont", new Font("Microsoft YaHei", Font.PLAIN, 18));
                    JOptionPane.showMessageDialog(null, payload.message());
                } catch (Exception e) {
                    UIManager.put("OptionPane.messageFont", new Font("SimSun", Font.PLAIN, 18));
                    JOptionPane.showMessageDialog(null, payload.message());
                }
            });
        });
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
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
