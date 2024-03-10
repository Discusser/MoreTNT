package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.explosions.BaseExplosion;
import io.discusser.moretnt.explosions.KnockbackExplosion;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class PrimedKnockbackTNT extends BasePrimedTNT {
    public PrimedKnockbackTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PrimedKnockbackTNT(Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        super(MoreTNTEntities.KNOCKBACK_TNT.get(), pLevel, pX, pY, pZ, size, fire, facing);
    }

    @Override
    public Class<? extends BaseExplosion> getExplosionClass() {
        return KnockbackExplosion.class;
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.PLAYER_ATTACK_KNOCKBACK;
    }
}
