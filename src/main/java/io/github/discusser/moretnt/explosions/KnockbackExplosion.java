package io.github.discusser.moretnt.explosions;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class KnockbackExplosion extends BaseExplosion {
    public KnockbackExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles, Holder<SoundEvent> pExplosionSound) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pX, pY, pZ, pRadius, pFire, pBlockInteraction, pSmallExplosionParticles, pLargeExplosionParticles, pExplosionSound);
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        for (Map.Entry<Entity, Vec3> entry : this.entitiesInRange.entrySet()) {
            Entity entity = entry.getKey();
            Vec3 knockback = entry.getValue();

            if (entity instanceof LivingEntity) {
                knockback = knockback.multiply(this.radius, this.radius, this.radius);
            }

            entity.hurtMarked = true;
            entity.setDeltaMovement(entity.getDeltaMovement().add(knockback.x, knockback.y, knockback.z));
        }
    }
}
