package com.example.taffymod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.swing.*;
import java.awt.*;

@EventBusSubscriber(modid = "taffymod", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TaffyClientHandler {

    @SubscribeEvent
    public static void registerPayloadHandler(RegisterPayloadHandlersEvent event) {
        event.registrar("taffymod")
                .playToClient(TaffyPopupPayload.TYPE, TaffyPopupPayload.STREAM_CODEC, TaffyClientHandler::handlePopup);
    }

    private static void handlePopup(TaffyPopupPayload payload, IPayloadContext context) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.put("OptionPane.messageFont", new Font("Microsoft YaHei", Font.PLAIN, 18));
                JOptionPane.showMessageDialog(null, payload.message());
            } catch (Exception e) {
                UIManager.put("OptionPane.messageFont", new Font("SimSun", Font.PLAIN, 18));
                JOptionPane.showMessageDialog(null, payload.message());
            }
        });
    }
}
