package io.github.discusser.moretnt.objects.registration;

import io.github.discusser.moretnt.MoreTNT;
import io.github.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoreTNTEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, MoreTNT.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> NEGATIVE_TNT = registerTNTEntity("negative_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> NEGATIVE_TNT_4X = registerTNTEntity("negative_tnt_4x");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> SHUFFLE_TNT = registerTNTEntity("shuffle_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> SHUFFLE_TNT_4X = registerTNTEntity("shuffle_tnt_4x");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> KNOCKBACK_TNT = registerTNTEntity("knockback_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> WATER_TNT = registerTNTEntity("water_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> LAVA_TNT = registerTNTEntity("lava_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> COBBLESTONE_TNT = registerTNTEntity("cobblestone_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> STONE_TNT = registerTNTEntity("stone_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> OBSIDIAN_TNT = registerTNTEntity("obsidian_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> SNOW_TNT = registerTNTEntity("snow_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> SNOW_TNT_4X = registerTNTEntity("snow_tnt_4x");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> CAT_TNT = registerTNTEntity("cat_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> DOME_TNT = registerTNTEntity("dome_tnt");
    public static final DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> FIRE_TNT = registerTNTEntity("fire_tnt");

    private static DeferredHolder<EntityType<?>, EntityType<? extends BasePrimedTNT>> registerTNTEntity(String name) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<BasePrimedTNT>of(BasePrimedTNT::new, MobCategory.MISC)
                .fireImmune()
                .sized(0.98F, 0.98F)
                .clientTrackingRange(10)
                .updateInterval(10)
                .build(MoreTNT.MODID + ":" + name)
        );
    }
}
