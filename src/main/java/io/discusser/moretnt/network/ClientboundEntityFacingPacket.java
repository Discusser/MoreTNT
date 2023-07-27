package io.discusser.moretnt.network;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
        MoreTNTPacketHandler.enqueueToClient(ctx, () -> {
            Player player = Minecraft.getInstance().player;

            if (player != null) {
                Entity entity = player.level.getEntity(this.id);
                if (entity instanceof BasePrimedTNT) {
                    ((BasePrimedTNT) entity).facing = this.facing;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
