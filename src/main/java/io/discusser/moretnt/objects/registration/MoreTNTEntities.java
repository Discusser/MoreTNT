package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoreTNTEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MoreTNT.MODID);

    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> NEGATIVE_TNT = registerTNTEntity("negative_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> NEGATIVE_TNT_4X = registerTNTEntity("negative_tnt_4x");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SHUFFLE_TNT = registerTNTEntity("shuffle_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SHUFFLE_TNT_4X = registerTNTEntity("shuffle_tnt_4x");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> KNOCKBACK_TNT = registerTNTEntity("knockback_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> WATER_TNT = registerTNTEntity("water_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> LAVA_TNT = registerTNTEntity("lava_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> COBBLESTONE_TNT = registerTNTEntity("cobblestone_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> STONE_TNT = registerTNTEntity("stone_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> OBSIDIAN_TNT = registerTNTEntity("obsidian_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SNOW_TNT = registerTNTEntity("snow_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> SNOW_TNT_4X = registerTNTEntity("snow_tnt_4x");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> CAT_TNT = registerTNTEntity("cat_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> DOME_TNT = registerTNTEntity("dome_tnt");
    public static final RegistryObject<EntityType<? extends BasePrimedTNT>> FIRE_TNT = registerTNTEntity("fire_tnt");

    private static RegistryObject<EntityType<? extends BasePrimedTNT>> registerTNTEntity(String name) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<BasePrimedTNT>of(BasePrimedTNT::new, MobCategory.MISC)
                .fireImmune()
                .sized(0.98F, 0.98F)
                .clientTrackingRange(10)
                .updateInterval(10)
                .build(MoreTNT.MODID + ":" + name)
        );
    }
}
