package io.discusser.moretnt;

import com.mojang.logging.LogUtils;
import io.discusser.moretnt.client.renderers.BaseTNTRenderer;
import io.discusser.moretnt.network.MoreTNTPacketHandler;
import io.discusser.moretnt.objects.MoreTNTObject;
import io.discusser.moretnt.objects.blocks.BaseTNTBlock;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import io.discusser.moretnt.objects.registration.MoreTNTItems;
import io.discusser.moretnt.objects.registration.MoreTNTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Mod(MoreTNT.MODID)
public class MoreTNT {
    public static final String MODID = "moretnt";
    public static final Logger LOGGER = LogUtils.getLogger();
    public final List<MoreTNTObject> objects = new ArrayList<>();
    public final Map<EntityType<? extends BasePrimedTNT>, Block> toRender = new HashMap<>();

    // Creative tab for various items
    public static final CreativeModeTab TAB = new CreativeModeTab(MODID + "_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(MoreTNTItems.NEGATIVE_TNT.get());
        }
    };

    public MoreTNT() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register all mod objects
        MoreTNTBlocks.BLOCKS.register(modEventBus);
        MoreTNTItems.ITEMS.register(modEventBus);
        MoreTNTEntities.ENTITY_TYPES.register(modEventBus);
        MoreTNTSounds.SOUND_EVENTS.register(modEventBus);

        modEventBus.addListener(this::registerRenderers);
        modEventBus.addListener(this::commonSetup);

        addObjects();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addObjects() {
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.NEGATIVE_TNT, MoreTNTEntities.NEGATIVE_TNT, MoreTNTItems.NEGATIVE_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.SHUFFLE_TNT, MoreTNTEntities.SHUFFLE_TNT, MoreTNTItems.SHUFFLE_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.SHUFFLE_TNT_4X, MoreTNTEntities.SHUFFLE_TNT, MoreTNTItems.SHUFFLE_TNT_4X));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.KNOCKBACK_TNT, MoreTNTEntities.KNOCKBACK_TNT, MoreTNTItems.KNOCKBACK_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.WATER_TNT, MoreTNTEntities.WATER_TNT, MoreTNTItems.WATER_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.LAVA_TNT, MoreTNTEntities.LAVA_TNT, MoreTNTItems.LAVA_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.SNOW_TNT, MoreTNTEntities.SNOW_TNT, MoreTNTItems.SNOW_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.SNOW_TNT_4X, MoreTNTEntities.SNOW_TNT, MoreTNTItems.SNOW_TNT_4X));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.CAT_TNT, MoreTNTEntities.CAT_TNT, MoreTNTItems.CAT_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.DOME_TNT, MoreTNTEntities.DOME_TNT, MoreTNTItems.DOME_TNT));
        this.objects.add(new MoreTNTObject(MoreTNTBlocks.FIRE_TNT, MoreTNTEntities.FIRE_TNT, MoreTNTItems.FIRE_TNT));
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        for (MoreTNTObject object : this.objects) {
            BaseTNTBlock block = object.block().get();
            DispenserBlock.registerBehavior(object.item().get(),
                    this.dispenserExecute((level, blockPos) -> block.createPrimed(level, blockPos, block.size, block.fire)));
        }

        MoreTNTPacketHandler.register();
    }

    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (MoreTNTObject object : this.objects) {
            event.registerEntityRenderer(object.entity().get(), pContext -> new BaseTNTRenderer(pContext, object.block().get()));
        }
    }

    public DefaultDispenseItemBehavior dispenserExecute(BiFunction<Level, BlockPos, BasePrimedTNT> function) {
        return new DefaultDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource pSource, @NotNull ItemStack pStack) {
                Level level = pSource.getLevel();
                BlockPos blockpos = pSource.getPos().relative(pSource.getBlockState().getValue(DispenserBlock.FACING));
                BasePrimedTNT instance = function.apply(level, blockpos);
                level.addFreshEntity(instance);
                level.playSound(null, instance.getX(), instance.getY(), instance.getZ(), SoundEvents.TNT_PRIMED,
                        SoundSource.BLOCKS,
                        1.0F, 1.0F);
                level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
                pStack.shrink(1);
                return pStack;
            }
        };
    }
}
