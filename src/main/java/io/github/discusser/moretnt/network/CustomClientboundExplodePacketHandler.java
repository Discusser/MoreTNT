package io.github.discusser.moretnt.network;

import io.github.discusser.moretnt.explosions.BaseExplosion;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class CustomClientboundExplodePacketHandler implements IPayloadHandler<CustomClientboundExplodePacket> {
    @Override
    public void handle(CustomClientboundExplodePacket payload, IPayloadContext context) {
        Player player = context.player();

        SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(payload.resourceLocation);
        BaseExplosion explosion = new BaseExplosion(player.level(), null, null, null,
                payload.x, payload.y, payload.z, payload.power, false, Explosion.BlockInteraction.DESTROY, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, Holder.direct(sound));
        explosion.finalizeExplosion(true);
        player.setDeltaMovement(player.getDeltaMovement().add(payload.knockback.x, payload.knockback.y, payload.knockback.z));
    }
}
