package net.kyrptonaught.diggusmaximus.fabric.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;

public class ModNetworkingClient {
    public static void registerPacket() {
        ClientPlayNetworking.registerGlobalReceiver(ExcavateFailedPacket.TYPE, (payload, context) -> {
            ExcavateFailedPacket.handleClient(context.player(), payload);
        });
    }

    public static void sendExcavatePacket(ExcavatePacket packet) {
        ClientPlayNetworking.send(packet);
    }
}
