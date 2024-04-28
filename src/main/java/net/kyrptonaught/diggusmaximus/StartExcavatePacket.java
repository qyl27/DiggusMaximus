package net.kyrptonaught.diggusmaximus;


import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class StartExcavatePacket {
    static void registerReceivePacket() {
        PayloadTypeRegistry.playC2S().register(ExcavatePayload.PACKET_ID, ExcavatePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ExcavatePayload.PACKET_ID, (payload, context) -> {
            var player = context.player();
            var server = player.server;
            server.execute(() -> {
                if (DiggusMaximusMod.getOptions().enabled) {
                    if (payload.pos().isWithinDistance(player.getPos(), 10)) {
                        new Excavate(payload.pos(), payload.id(), player, payload.facing()).startExcavate(payload.shape());
                    }
                }
            });
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendExcavatePacket(BlockPos pos, Identifier id, Direction facing, int shape) {
        ClientPlayNetworking.send(new ExcavatePayload(pos, id, facing, shape));
    }

    public record ExcavatePayload(BlockPos pos, Identifier id, Direction facing, int shape) implements CustomPayload {
        public static final Identifier IDENTIFIER = new Identifier(DiggusMaximusMod.MOD_ID, "start_excavate_packet");

        public static final Id<ExcavatePayload> PACKET_ID = new Id<>(IDENTIFIER);

        public static final PacketCodec<PacketByteBuf, ExcavatePayload> CODEC = PacketCodec.ofStatic(ExcavatePayload::write, ExcavatePayload::from);

        @Override
        public Id<? extends CustomPayload> getId() {
            return PACKET_ID;
        }

        public static ExcavatePayload from(PacketByteBuf buf) {
            var pos = buf.readBlockPos();
            var id = buf.readIdentifier();
            var facingId = buf.readInt();
            var facing = facingId == -1 ? null : Direction.byId(facingId);
            var shape = buf.readInt();
            return new ExcavatePayload(pos, id, facing, shape);
        }

        public static void write(PacketByteBuf buf, ExcavatePayload payload) {
            buf.writeBlockPos(payload.pos);
            buf.writeIdentifier(payload.id);
            buf.writeInt(payload.facing == null ? -1 : payload.facing.getId());
            buf.writeInt(payload.shape);
        }
    }
}