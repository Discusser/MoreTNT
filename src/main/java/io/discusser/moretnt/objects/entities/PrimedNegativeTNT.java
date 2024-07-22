package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.explosions.BaseExplosion;
import io.discusser.moretnt.explosions.NegativeExplosion;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import io.discusser.moretnt.objects.registration.MoreTNTSounds;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class PrimedNegativeTNT extends BasePrimedTNT {
    public PrimedNegativeTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PrimedNegativeTNT(Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        super(MoreTNTEntities.NEGATIVE_TNT.get(), pLevel, pX, pY, pZ, size, fire, facing);
    }

    public PrimedNegativeTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        super(pEntityType, pLevel, pX, pY, pZ, size, fire, facing);
    }

    @Override
    public Class<? extends BaseExplosion> getExplosionClass() {
        return NegativeExplosion.class;
    }

    @Override
    public SoundEvent getSound() {
        return MoreTNTSounds.GENERIC_EXPLODE_REVERSED.get();
    }
}
