package io.discusser.moretnt.objects;

import io.discusser.moretnt.explosions.BaseExplosion;
import io.discusser.moretnt.explosions.ExplosionFactory;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PrimedTNTObject {
    public final RegistryObject<EntityType<? extends BasePrimedTNT>> entityType;
    public final ExplosionFactory<? extends BaseExplosion> explosionFactory;
    public Supplier<SoundEvent> sound;


    public PrimedTNTObject(RegistryObject<EntityType<? extends BasePrimedTNT>> entityType,
                           ExplosionFactory<? extends BaseExplosion> explosionFactory, SoundEvent sound) {
        this.entityType = entityType;
        this.explosionFactory = explosionFactory;
        this.sound = () -> sound;
    }

    public PrimedTNTObject(RegistryObject<EntityType<? extends BasePrimedTNT>> entityType,
                           ExplosionFactory<? extends BaseExplosion> explosionFactory, Supplier<SoundEvent> sound) {
        this.entityType = entityType;
        this.explosionFactory = explosionFactory;
        this.sound = sound;
    }
}
