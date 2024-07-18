package io.discusser.moretnt.explosions;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DomeExplosion extends BaseExplosion {
    public DomeExplosion(Level pLevel, @org.jetbrains.annotations.Nullable Entity pSource,
                        @org.jetbrains.annotations.Nullable DamageSource pDamageSource,
                        @org.jetbrains.annotations.Nullable ExplosionDamageCalculator pDamageCalculator,
                        double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire,
                        BlockInteraction pBlockInteraction, SoundEvent soundEvent) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire,
                pBlockInteraction, soundEvent);
    }

    public double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    // Adapted from WorldEdit's makeSphere function
    // https://github.com/EngineHub/WorldEdit/blob/master/worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java#L1862
    @Override
    public void explode() {
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));
        Set<BlockPos> set = Sets.newHashSet();

        double radiusX = this.radius + 0.5;
        double radiusY = this.radius + 0.5;
        double radiusZ = this.radius + 0.5;

        double invRadiusX = 1 / radiusX;
        double invRadiusY = 1 / radiusY;
        double invRadiusZ = 1 / radiusZ;

        int ceilRadiusX = (int) Math.ceil(radiusX);
        int ceilRadiusY = (int) Math.ceil(radiusY);
        int ceilRadiusZ = (int) Math.ceil(radiusZ);

        double nextXn = 0;
        forX: for (int x = 0; x <= ceilRadiusX; ++x) {
            double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY: for (int y = 0; y <= ceilRadiusY; ++y) {
                double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                for (int z = 0; z <= ceilRadiusZ; ++z) {
                    double zn = nextZn;
                    nextZn = (z + 1) * invRadiusZ;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break;
                    }

                    if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
                        continue;
                    }

                    set.add(BlockPos.containing(this.x + x, this.y + y, this.z + z));
                    set.add(BlockPos.containing(this.x - x, this.y + y, this.z + z));
                    set.add(BlockPos.containing(this.x + x, this.y - y, this.z + z));
                    set.add(BlockPos.containing(this.x + x, this.y + y, this.z - z));
                    set.add(BlockPos.containing(this.x - x, this.y - y, this.z + z));
                    set.add(BlockPos.containing(this.x + x, this.y - y, this.z - z));
                    set.add(BlockPos.containing(this.x - x, this.y + y, this.z - z));
                    set.add(BlockPos.containing(this.x - x, this.y - y, this.z - z));
                }
            }
        }

        this.toBlow.addAll(set);

        float f2 = this.radius * 2.0F;
        List<Entity> list = new ArrayList<>();
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, this, list, f2);
    }

    @Override
    public void onFinalizeExplosion(boolean pSpawnParticles) {
        for (BlockPos blockPos : this.toBlow) {
            if (this.level instanceof ServerLevel) {
                BlockState blockState = this.level.getBlockState(blockPos);
                if (blockState.isAir() || !blockState.getFluidState().isEmpty() || blockState.canBeReplaced()) {
                    this.level.setBlockAndUpdate(blockPos, Blocks.GLASS.defaultBlockState());
                }
            }
        }
    }
}
