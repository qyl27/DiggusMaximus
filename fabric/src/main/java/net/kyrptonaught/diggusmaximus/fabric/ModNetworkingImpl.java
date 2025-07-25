package net.kyrptonaught.diggusmaximus.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.kyrptonaught.diggusmaximus.fabric.client.ModNetworkingClient;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.server.level.ServerPlayer;

public class ModNetworkingImpl {
    public static void sendExcavatePacket(ExcavatePacket packet) {
        ModNetworkingClient.sendExcavatePacket(packet);
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

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ModNetworkingClient.registerPacket();
        }
    }
}
