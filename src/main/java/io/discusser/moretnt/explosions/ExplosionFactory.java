package io.discusser.moretnt.explosions;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface ExplosionFactory<T extends BaseExplosion> {
    T create(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource,
             @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ,
             float pRadius, boolean pFire, Explosion.BlockInteraction pBlockInteraction, SoundEvent soundEvent);
}
