package net.kyrptonaught.diggusmaximus.neoforge;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = ModConstants.MOD_ID)
public class ModNetworkingImpl {
    public static void sendExcavatePacket(BlockPos pos, ResourceLocation id, Shape shape, Direction hitFace) {
        PacketDistributor.sendToServer(new ExcavatePacket(pos, id, shape, hitFace));
    }

    public static void registerPackets() {
    }

    @SubscribeEvent
    public static void onRegisterPacket(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(ModConstants.NETWORK_PROTOCOL_VERSION);
        registrar.optional().playToServer(ExcavatePacket.PACKET_ID, ExcavatePacket.CODEC, (payload, context) -> {
            if (context.player() instanceof ServerPlayer player) {
                ExcavatePacket.handleServer(player, payload);
            }
        });
    }
}
