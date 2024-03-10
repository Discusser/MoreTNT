package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.explosions.BaseExplosion;
import io.discusser.moretnt.explosions.ShuffleExplosion;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class PrimedShuffleTNT extends BasePrimedTNT {
    public PrimedShuffleTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @SuppressWarnings("unused")
    public PrimedShuffleTNT(Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        this(MoreTNTEntities.SHUFFLE_TNT.get(), pLevel, pX, pY, pZ, size, fire, facing);
    }

    public PrimedShuffleTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        super(pEntityType, pLevel, pX, pY, pZ, size, fire, facing);
    }

    @Override
    public Class<? extends BaseExplosion> getExplosionClass() {
        return ShuffleExplosion.class;
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.CHORUS_FRUIT_TELEPORT;
    }
}
