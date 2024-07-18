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

public class LavaExplosion extends BaseExplosion {
    public final List<BlockPos> sources = new ArrayList<>();

    public LavaExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
                         @org.jetbrains.annotations.Nullable DamageSource pDamageSource,
                         @org.jetbrains.annotations.Nullable ExplosionDamageCalculator pDamageCalculator,
                         double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                         BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire,
                pBlockInteraction, soundEvent);
    }

    @Override
    boolean canBlockExplode(BlockPos blockpos) {
        FluidState fluidstate = this.level.getFluidState(blockpos);
        return !(fluidstate.is(Fluids.LAVA) || fluidstate.is(Fluids.FLOWING_LAVA));
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
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        for (BlockPos pos : this.sources) {
            if (this.level instanceof ServerLevel) {
                level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
            }
        }
        for (BlockPos pos : this.toBlow) {
            if (this.level instanceof ServerLevel) {
                level.setBlockAndUpdate(pos, Fluids.FLOWING_LAVA.defaultFluidState().createLegacyBlock());
            }
        }
    }
}
