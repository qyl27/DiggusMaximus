package net.kyrptonaught.diggusmaximus.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class ModNetworkingImpl {
    public static void sendExcavatePacket(BlockPos pos, ResourceLocation id, Shape shape, Direction hitFace) {
        ClientPlayNetworking.send(new ExcavatePacket(pos, id, shape, hitFace));
    }

    public static void registerPackets() {
        PayloadTypeRegistry.playC2S().register(ExcavatePacket.PACKET_ID, ExcavatePacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ExcavatePacket.PACKET_ID, (payload, context) -> {
            ExcavatePacket.handleServer(context.player(), payload);
        });
    }
}
