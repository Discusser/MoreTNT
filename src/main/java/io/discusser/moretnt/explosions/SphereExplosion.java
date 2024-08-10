package io.discusser.moretnt.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class SphereExplosion extends BaseExplosion {
    public final Block toPlace;

    public SphereExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, SoundEvent soundEvent, Block toPlace) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent);
        this.toPlace = toPlace;
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        for (BlockPos blockPos : this.toBlow) {
            if (this.level instanceof ServerLevel) {
                this.level.setBlockAndUpdate(blockPos, this.toPlace.defaultBlockState());
            }
        }
    }
}
