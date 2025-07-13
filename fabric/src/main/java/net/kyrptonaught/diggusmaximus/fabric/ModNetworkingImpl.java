package net.kyrptonaught.diggusmaximus.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.server.level.ServerPlayer;

public class ModNetworkingImpl {
    public static void sendExcavatePacket(ExcavatePacket packet) {
        ClientPlayNetworking.send(packet);
    }

    public static void sendFailedPacket(ServerPlayer player, ExcavateFailedPacket packet) {
        ServerPlayNetworking.send(player, packet);
    }

    public static void registerPackets() {
        PayloadTypeRegistry.playC2S().register(ExcavatePacket.TYPE, ExcavatePacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ExcavateFailedPacket.TYPE, ExcavateFailedPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ExcavatePacket.TYPE, (payload, context) -> {
            ExcavatePacket.handleServer(context.player(), payload);
        });

        ClientPlayNetworking.registerGlobalReceiver(ExcavateFailedPacket.TYPE, (payload, context) -> {
            ExcavateFailedPacket.handleClient(context.player(), payload);
        });
    }
}
