package com.Twins730.guineapigs;

import com.Twins730.guineapigs.client.model.GuineaPigModel;
import com.Twins730.guineapigs.client.renderer.GuineaPigRenderer;
import com.Twins730.guineapigs.init.BlockInit;
import com.Twins730.guineapigs.init.EntityInit;
import com.Twins730.guineapigs.init.ItemInit;
import com.Twins730.guineapigs.init.SoundInit;
import com.Twins730.guineapigs.objects.entity.trades.ModItemForEmeralds;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.world.entity.npc.VillagerTrades.WANDERING_TRADER_TRADES;

@Mod(GuineaPigMod.MOD_ID)
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
        modEventBus.addListener(this::layerDefinition);
        modEventBus.addListener(this::entityRenderer);
        MinecraftForge.EVENT_BUS.addListener(this::tradeEvent);
        MinecraftForge.EVENT_BUS.register(this);
        GuineaPigMod.LOGGER.info("Finished up mod:" + MOD_ID);
    }

    private void commonSetup(FMLCommonSetupEvent event){
        ComposterBlock.COMPOSTABLES.put(ItemInit.ALFALFA_SEEDS.get(), 0.3F);
    }

    private void clientSetup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ALFALFA.get(), RenderType.cutout());
    }

    private void entityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.GUINEA_PIG.get(), GuineaPigRenderer::new);
    }
    private void layerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(GuineaPigModel.LAYER_LOCATION, GuineaPigModel::createBodyLayer);
    }

    private void tradeEvent(WandererTradesEvent event){
        event.getGenericTrades().add(new ModItemForEmeralds(ItemInit.ALFALFA_SEEDS.get(),3,1,1));
        event.getRareTrades().add(new ModItemForEmeralds(ItemInit.GUINEA_PIG_POUCH.get(),3,1,6));
    }

}
