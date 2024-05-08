package io.discusser.moretnt.objects.entities;

import io.discusser.moretnt.explosions.BaseExplosion;
import io.discusser.moretnt.explosions.DomeExplosion;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class PrimedDomeTNT extends BasePrimedTNT {
    public PrimedDomeTNT(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PrimedDomeTNT(EntityType<? extends PrimedTnt> entityType, Level pLevel, double pX, double pY, double pZ, float size, boolean fire, Direction facing) {
        super(entityType, pLevel, pX, pY, pZ, size, fire, facing);
    }

    @Override
    public BaseExplosion createExplosion(double x, double y, double z) {
        return new DomeExplosion(this.level, null, null, null, x, y, z, this.size,
                this.fire, Explosion.BlockInteraction.BREAK, this.getSound());
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.GLASS_BREAK;
    }

    @Override
    public Class<? extends BaseExplosion> getExplosionClass() {
        return DomeExplosion.class;
    }
}
