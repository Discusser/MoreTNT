package io.github.discusser.moretnt;

import com.mojang.logging.LogUtils;
import io.github.discusser.moretnt.objects.MoreTNTObject;
import io.github.discusser.moretnt.objects.PrimedTNTObject;
import io.github.discusser.moretnt.objects.blocks.BaseTNTBlock;
import io.github.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.github.discusser.moretnt.objects.registration.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.function.BiFunction;

@Mod(MoreTNT.MODID)
public class MoreTNT {
    public static final String MODID = "moretnt";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final HashMap<BaseTNTBlock, PrimedTNTObject> blockToPrimedTNTMap = new HashMap<>();

    public MoreTNT(IEventBus modEventBus, ModContainer modContainer) {
        // Register all mod objects
        MoreTNTBlocks.BLOCKS.register(modEventBus);
        MoreTNTItems.ITEMS.register(modEventBus);
        MoreTNTEntities.ENTITY_TYPES.register(modEventBus);
        MoreTNTSounds.SOUND_EVENTS.register(modEventBus);
        MoreTNTTabs.TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
//        NeoForge.EVENT_BUS.register(this);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        for (MoreTNTObject object : MoreTNTObjects.objects) {
            blockToPrimedTNTMap.put(object.blockItem().block().get(), object.primedTNTObject());

            BaseTNTBlock block = object.blockItem().block().get();
            DispenserBlock.registerBehavior(object.blockItem().item().get(),
                    this.dispenserExecute((level, blockPos) -> block.createPrimed(level, blockPos, block.size, block.fire)));
        }
    }


    public DefaultDispenseItemBehavior dispenserExecute(BiFunction<Level, BlockPos, BasePrimedTNT> function) {
        return new DefaultDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource pSource, @NotNull ItemStack pStack) {
                Level level = pSource.level();
                BlockPos blockpos = pSource.pos().relative(pSource.state().getValue(DispenserBlock.FACING));
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
