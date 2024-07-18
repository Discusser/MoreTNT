package io.discusser.moretnt.network;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundEntityFacingPacketHandler {
    @SuppressWarnings("unused")
    public static void handle(ClientboundEntityFacingPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            Entity entity = player.level().getEntity(msg.id);
            if (entity instanceof BasePrimedTNT) {
                ((BasePrimedTNT) entity).facing = msg.facing;
            }
        }
    }
}
