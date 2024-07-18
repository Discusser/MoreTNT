package io.discusser.moretnt.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WaterExplosion extends BaseExplosion {
    public final List<BlockPos> sources = new ArrayList<>();

    public WaterExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
                          @org.jetbrains.annotations.Nullable DamageSource pDamageSource,
                          @org.jetbrains.annotations.Nullable ExplosionDamageCalculator pDamageCalculator,
                          double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                          BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire,
                pBlockInteraction, soundEvent);
    }

    @Override
    void onBlockExploded(Set<BlockPos> set, BlockPos blockpos, int j, int k, int l) {
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (k == 15) {
            this.sources.add(blockpos);
        } else if (blockstate.isAir()) {
            set.add(blockpos);
        }
    }

    @Override
    boolean canBlockExplode(BlockPos blockpos) {
        FluidState fluidstate = this.level.getFluidState(blockpos);
        return !(fluidstate.is(Fluids.WATER) || fluidstate.is(Fluids.FLOWING_WATER));
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        for (BlockPos pos : this.sources) {
            if (this.level instanceof ServerLevel) {
                this.level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
        }
        for (BlockPos pos : this.toBlow) {
            if (this.level instanceof ServerLevel) {
                this.level.setBlockAndUpdate(pos, Fluids.FLOWING_WATER.defaultFluidState().createLegacyBlock());
            }
        }
    }
}
