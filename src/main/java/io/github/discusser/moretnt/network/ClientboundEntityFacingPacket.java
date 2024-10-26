package io.github.discusser.moretnt.network;

import io.github.discusser.moretnt.MoreTNT;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ClientboundEntityFacingPacket(int id, Direction facing) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientboundEntityFacingPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MoreTNT.MODID, "clientbound_entity_facing_packet")
    );

    public static final StreamCodec<ByteBuf, ClientboundEntityFacingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ClientboundEntityFacingPacket::id,
            Direction.STREAM_CODEC,
            ClientboundEntityFacingPacket::facing,
            ClientboundEntityFacingPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
