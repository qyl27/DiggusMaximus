package net.kyrptonaught.diggusmaximus.fabric;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.server.level.ServerPlayer;

public class ModNetworkingImpl {
    public static void sendFailedPacket(ServerPlayer player, ExcavateFailedPacket packet) {
        if (!ServerPlayNetworking.canSend(player, ExcavateFailedPacket.TYPE)) {
            return;
        }

        ServerPlayNetworking.send(player, packet);
    }

    public static void registerPackets() {
        PayloadTypeRegistry.serverboundPlay().register(ExcavatePacket.TYPE, ExcavatePacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ExcavateFailedPacket.TYPE, ExcavateFailedPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ExcavatePacket.TYPE, (payload, context) -> {
            ExcavatePacket.handleServer(context.player(), payload);
        });
    }
}
