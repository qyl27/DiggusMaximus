package net.kyrptonaught.diggusmaximus.neoforge;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@EventBusSubscriber(modid = ModConstants.MOD_ID, value = Dist.CLIENT)
public class ModClientNetworkingImpl {
    public static void sendExcavatePacket(ExcavatePacket packet) {
        var connection = Minecraft.getInstance().getConnection();
        if (connection == null || !connection.hasChannel(ExcavatePacket.TYPE)) {
            return;
        }

        ClientPacketDistributor.sendToServer(packet);
    }

    public static void registerPackets() {
        // No-op.
    }

    @SubscribeEvent
    public static void onRegisterPacket(RegisterClientPayloadHandlersEvent event) {
        event.register(ExcavateFailedPacket.TYPE, (payload, context) -> {
            ExcavateFailedPacket.handleClient(context.player(), payload);
        });
    }
}
