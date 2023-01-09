package com.Twins730.guineapigs;

import com.Twins730.guineapigs.client.renderer.GuineaPigRenderer;
import com.Twins730.guineapigs.init.BlockInit;
import com.Twins730.guineapigs.init.EntityInit;
import com.Twins730.guineapigs.init.ItemInit;
import com.Twins730.guineapigs.init.SoundInit;
import com.Twins730.guineapigs.objects.item.GuineaPigSpawnEggItem;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GuineaPigMod.MOD_ID)
@Mod.EventBusSubscriber(modid = GuineaPigMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuineaPigMod {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "guineapigs";

    public GuineaPigMod() {
        GuineaPigMod.LOGGER.info("Setting up mod:" + MOD_ID + " starting!");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(modEventBus);
        SoundInit.SOUNDS.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);

        modEventBus.addListener(EntityInit::makeAttributes);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.addListener(this::tradeEvent);
        MinecraftForge.EVENT_BUS.register(this);
        GuineaPigMod.LOGGER.info("Finished up mod:" + MOD_ID);
    }

    private void commonSetup(FMLCommonSetupEvent event){
        ComposterBlock.CHANCES.put(ItemInit.ALFALFA_SEEDS.get(), 0.3F);
    }

    private void clientSetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GUINEA_PIG.get(), GuineaPigRenderer::new);
        RenderTypeLookup.setRenderLayer(BlockInit.ALFALFA.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public void registerEntities(final RegistryEvent.Register<EntityType<?>> event){
        GuineaPigSpawnEggItem.initSpawnEggs();
    }

    private void tradeEvent(WandererTradesEvent event){
        event.getGenericTrades().add(new BasicTrade(new ItemStack(Items.EMERALD),new ItemStack(ItemInit.ALFALFA_SEEDS.get(),3),3,2,1));
        event.getRareTrades().add(new BasicTrade(new ItemStack(Items.EMERALD),new ItemStack(ItemInit.GUINEA_PIG_POUCH.get(),2),1,6,1));
    }

}
