package net.kyrptonaught.diggusmaximus.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kyrptonaught.diggusmaximus.networking.ExcavateFailedPacket;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;

public class ModClientNetworkingImpl {
    public static void sendExcavatePacket(ExcavatePacket packet) {
        if (!ClientPlayNetworking.canSend(ExcavatePacket.TYPE)) {
            return;
        }

        ClientPlayNetworking.send(packet);
    }

    public static void registerPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ExcavateFailedPacket.TYPE, (payload, context) -> {
            ExcavateFailedPacket.handleClient(context.player(), payload);
        });
    }
}
