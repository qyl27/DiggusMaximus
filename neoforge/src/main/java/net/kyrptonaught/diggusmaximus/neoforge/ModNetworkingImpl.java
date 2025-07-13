package net.kyrptonaught.diggusmaximus.neoforge;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = ModConstants.MOD_ID)
public class ModNetworkingImpl {
    public static void sendExcavatePacket(ExcavatePacket packet) {
        PacketDistributor.sendToServer(packet);
    }

    public static void sendFailedPacket(ServerPlayer player, ExcavateFailedPacket packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void registerPackets() {
    }

    @SubscribeEvent
    public static void onRegisterPacket(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(ModConstants.NETWORK_PROTOCOL_VERSION);
        registrar.optional().playToServer(ExcavatePacket.TYPE, ExcavatePacket.CODEC, (payload, context) -> {
            if (context.player() instanceof ServerPlayer player) {
                ExcavatePacket.handleServer(player, payload);
            }
        });
    }
}
