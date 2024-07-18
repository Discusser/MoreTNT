package io.discusser.moretnt.network;

import io.discusser.moretnt.explosions.BaseExplosion;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CustomClientboundExplodePacketHandler {
    @SuppressWarnings("unused")
    public static void handle(CustomClientboundExplodePacket msg, Supplier<NetworkEvent.Context> ctx) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(msg.resourceLocation);
            BaseExplosion explosion = new BaseExplosion(player.level(), null, null, null,
                    msg.x, msg.y, msg.z, msg.power, false, Explosion.BlockInteraction.DESTROY, sound);
            explosion.finalizeExplosion(true);
            player.setDeltaMovement(player.getDeltaMovement().add(msg.knockbackX, msg.knockbackY, msg.knockbackZ));
        }
    }
}
