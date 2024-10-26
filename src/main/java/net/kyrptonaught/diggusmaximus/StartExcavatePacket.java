package net.kyrptonaught.diggusmaximus;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StartExcavatePacket {
    static void registerReceivePacket() {
        PayloadTypeRegistry.playC2S().register(ExcavatePayload.PACKET_ID, ExcavatePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ExcavatePayload.PACKET_ID, (payload, context) -> {
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
        ClientPlayNetworking.send(new ExcavatePayload(pos, id, facing, shape));
    }

    public record ExcavatePayload(BlockPos pos, ResourceLocation id, Direction facing,
                                  int shape) implements CustomPacketPayload {
        public static final ResourceLocation IDENTIFIER = ResourceLocation.fromNamespaceAndPath(DiggusMaximusMod.MOD_ID, "start_excavate_packet");

        public static final Type<ExcavatePayload> PACKET_ID = new Type<>(IDENTIFIER);

        public static final StreamCodec<FriendlyByteBuf, ExcavatePayload> CODEC = StreamCodec.of(ExcavatePayload::write, ExcavatePayload::from);

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }

        public static ExcavatePayload from(FriendlyByteBuf buf) {
            var pos = buf.readBlockPos();
            var id = buf.readResourceLocation();
            var facingId = buf.readInt();
            var facing = facingId == -1 ? null : Direction.from3DDataValue(facingId);
            var shape = buf.readInt();
            return new ExcavatePayload(pos, id, facing, shape);
        }

        public static void write(FriendlyByteBuf buf, ExcavatePayload payload) {
            buf.writeBlockPos(payload.pos);
            buf.writeResourceLocation(payload.id);
            buf.writeInt(payload.facing == null ? -1 : payload.facing.get3DDataValue());
            buf.writeInt(payload.shape);
        }
    }
}