package io.github.discusser.moretnt.network;

import io.github.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import org.jetbrains.annotations.NotNull;

public class ClientboundEntityFacingPacketHandler implements IPayloadHandler<ClientboundEntityFacingPacket> {
    @Override
    public void handle(@NotNull ClientboundEntityFacingPacket payload, @NotNull IPayloadContext context) {
        Player player = context.player();

        Entity entity = player.level().getEntity(payload.id());
        if (entity instanceof BasePrimedTNT) {
            ((BasePrimedTNT) entity).facing = payload.facing();
        }
    }
}
