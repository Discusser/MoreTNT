package io.discusser.moretnt.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Set;

public class FireExplosion extends BaseExplosion {
    public FireExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource,
                         @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ,
                         float pRadius, boolean pFire, Explosion.BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire,
                pBlockInteraction, soundEvent);
    }

    @Override
    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        BlockState blockstate = this.level.getBlockState(blockpos);
        BlockState below = this.level.getBlockState(blockpos.below());
        if (blockstate.isAir() && blockstate.canBeReplaced()
                && below.getBlock().properties.hasCollision && !below.is(Blocks.FIRE)) {
            set.add(blockpos);
        }
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        for (BlockPos blockPos : this.toBlow) {
            if (this.level instanceof ServerLevel) {
                level.setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
            }
        }
    }
}
