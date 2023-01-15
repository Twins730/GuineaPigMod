package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import com.Twins730.guineapigs.objects.item.GuineaPigPouchItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GuineaPigMod.MOD_ID);

    public static final RegistryObject<Item> ALFALFA = ITEMS.register("alfalfa", ()-> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> ALFALFA_SEEDS = ITEMS.register("alfalfa_seeds", ()-> new ItemNameBlockItem(BlockInit.ALFALFA.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> GUINEA_PIG_SPAWN_EGG = ITEMS.register("guinea_pig_spawn_egg", ()-> new ForgeSpawnEggItem(EntityInit.GUINEA_PIG,13540481,11164735,(new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> GUINEA_PIG_POUCH = ITEMS.register("guinea_pig_pouch", ()-> new GuineaPigPouchItem(EntityInit.GUINEA_PIG,(new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS).stacksTo(1)));
    public static final RegistryObject<Item> EMPTY_GUINEA_PIG_POUCH = ITEMS.register("empty_guinea_pig_pouch", ()-> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS).stacksTo(1)));

}
