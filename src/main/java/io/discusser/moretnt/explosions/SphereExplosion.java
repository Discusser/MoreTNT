package io.discusser.moretnt.explosions;

import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SphereExplosion extends BaseExplosion {
    public final Block toPlace;
    public final boolean filled;

    public SphereExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, SoundEvent soundEvent, Block toPlace, boolean filled) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent);
        this.toPlace = toPlace;
        this.filled = filled;
    }

    public SphereExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, SoundEvent soundEvent, Block toPlace) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, soundEvent);
        this.toPlace = toPlace;
        this.filled = true;
    }

    public double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    public void addBlock(BlockPos blockPos) {
        if (MoreTNTBlocks.isTNT(this.level.getBlockState(blockPos))) {
            neighborTnt.add(blockPos);
        } else {
            this.toBlow.add(blockPos);
        }
    }

    // Adapted from https://github.com/EngineHub/WorldEdit/blob/6f210ad3984006118ba337d66f88bf291f01d4a0/worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java#L1875
    @Override
    public void explode() {
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));

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
        forX:
        for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY:
            for (int y = 0; y <= ceilRadiusY; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                for (int z = 0; z <= ceilRadiusZ; ++z) {
                    final double zn = nextZn;
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

                    if (!this.filled) {
                        if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
                            continue;
                        }
                    }

                    this.addBlock(BlockPos.containing(this.x + x, this.y + y, this.z + z));
                    this.addBlock(BlockPos.containing(this.x - x, this.y + y, this.z + z));
                    this.addBlock(BlockPos.containing(this.x + x, this.y - y, this.z + z));
                    this.addBlock(BlockPos.containing(this.x + x, this.y + y, this.z - z));
                    this.addBlock(BlockPos.containing(this.x - x, this.y - y, this.z + z));
                    this.addBlock(BlockPos.containing(this.x + x, this.y - y, this.z - z));
                    this.addBlock(BlockPos.containing(this.x - x, this.y + y, this.z - z));
                    this.addBlock(BlockPos.containing(this.x - x, this.y - y, this.z - z));
                }
            }
        }

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
                    this.level.setBlockAndUpdate(blockPos, this.toPlace.defaultBlockState());
                }
            }
        }
    }
}
