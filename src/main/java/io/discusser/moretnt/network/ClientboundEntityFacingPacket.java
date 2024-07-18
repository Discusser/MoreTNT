package io.discusser.moretnt.network;

import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundEntityFacingPacket {
    public final int id;
    public final Direction facing;

    public ClientboundEntityFacingPacket(int id, Direction facing) {
        this.id = id;
        this.facing = facing;
    }

    public ClientboundEntityFacingPacket(FriendlyByteBuf pBuffer) {
        this.id = pBuffer.readInt();
        this.facing = pBuffer.readEnum(Direction.class);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeInt(this.id);
        pBuffer.writeEnum(this.facing);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientboundEntityFacingPacketHandler.handle(this, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
