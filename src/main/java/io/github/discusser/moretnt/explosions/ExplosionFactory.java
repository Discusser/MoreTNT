package io.github.discusser.moretnt.explosions;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface ExplosionFactory<T extends BaseExplosion> {
    T create(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource,
             @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ,
             float pRadius, boolean pFire, Explosion.BlockInteraction pBlockInteraction,
             ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles,
             Holder<SoundEvent> pExplosionSound);
}
