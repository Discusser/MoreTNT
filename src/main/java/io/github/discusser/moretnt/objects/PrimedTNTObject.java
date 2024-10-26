package io.github.discusser.moretnt.objects;

import io.github.discusser.moretnt.explosions.BaseExplosion;
import io.github.discusser.moretnt.explosions.ExplosionFactory;
import io.github.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class PrimedTNTObject {
    public final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> entityType;
    public final ExplosionFactory<? extends BaseExplosion> explosionFactory;
    public Holder<SoundEvent> sound;


    public PrimedTNTObject(DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> entityType,
                           ExplosionFactory<? extends BaseExplosion> explosionFactory, Holder<SoundEvent> sound) {
        this.entityType = entityType;
        this.explosionFactory = explosionFactory;
        this.sound = sound;
    }

    public PrimedTNTObject(DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> entityType,
                           ExplosionFactory<? extends BaseExplosion> explosionFactory, SoundEvent sound) {
        this.entityType = entityType;
        this.explosionFactory = explosionFactory;
        this.sound = Holder.direct(sound);
    }
}
