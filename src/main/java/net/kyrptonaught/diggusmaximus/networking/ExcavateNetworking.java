package net.kyrptonaught.diggusmaximus.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.kyrptonaught.diggusmaximus.excavate.Excavate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class ExcavateNetworking {
    public static void registerReceivePacket() {
        PayloadTypeRegistry.playC2S().register(ExcavatePacket.PACKET_ID, ExcavatePacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ExcavatePacket.PACKET_ID, (payload, context) -> {
            var player = context.player();
            var server = player.server;
            server.execute(() -> {
                if (ConfigHelper.getConfig().config.enabled) {
                    if (payload.pos().closerToCenterThan(player.position(), 10)) {
                        new Excavate(payload.pos(), payload.id(), player, payload.facing()).startExcavate(payload.shape());
                    }
                }
            });
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendExcavatePacket(BlockPos pos, ResourceLocation id, Direction facing, int shape) {
        ClientPlayNetworking.send(new ExcavatePacket(pos, id, facing, shape));
    }
}