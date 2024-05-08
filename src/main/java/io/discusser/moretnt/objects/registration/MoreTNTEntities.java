package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.objects.entities.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoreTNTEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MoreTNT.MODID);

    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> NEGATIVE_TNT = registerTNTEntity("negative_tnt",
            EntityType.Builder.<PrimedNegativeTNT>of(PrimedNegativeTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SHUFFLE_TNT = registerTNTEntity("shuffle_tnt",
            EntityType.Builder.<PrimedShuffleTNT>of(PrimedShuffleTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SHUFFLE_TNT_4X = registerTNTEntity("shuffle_tnt_4x",
            EntityType.Builder.<PrimedShuffleTNT>of(PrimedShuffleTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> KNOCKBACK_TNT = registerTNTEntity("knockback_tnt",
            EntityType.Builder.<PrimedKnockbackTNT>of(PrimedKnockbackTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> WATER_TNT = registerTNTEntity("water_tnt",
            EntityType.Builder.<PrimedWaterTNT>of(PrimedWaterTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> LAVA_TNT = registerTNTEntity("lava_tnt",
            EntityType.Builder.<PrimedLavaTNT>of(PrimedLavaTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SNOW_TNT = registerTNTEntity("snow_tnt",
            EntityType.Builder.<PrimedSnowTNT>of(PrimedSnowTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SNOW_TNT_4X = registerTNTEntity("snow_tnt_4x",
            EntityType.Builder.<PrimedSnowTNT>of(PrimedSnowTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> CAT_TNT = registerTNTEntity("cat_tnt",
            EntityType.Builder.<PrimedCatTNT>of(PrimedCatTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> DOME_TNT = registerTNTEntity("dome_tnt",
            EntityType.Builder.<PrimedDomeTNT>of(PrimedDomeTNT::new, MobCategory.MISC));
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> FIRE_TNT = registerTNTEntity("fire_tnt",
            EntityType.Builder.<PrimedFireTNT>of(PrimedFireTNT::new, MobCategory.MISC));

    private static RegistryObject<EntityType<? extends BasePrimedTNT>> registerTNTEntity(String name, EntityType.Builder<?
            extends BasePrimedTNT> builder) {
        return ENTITY_TYPES.register(name, () -> builder
                .fireImmune()
                .sized(0.98F, 0.98F)
                .clientTrackingRange(10)
                .updateInterval(10)
                .build(MoreTNT.MODID + ":" + name)
        );
    }
}
