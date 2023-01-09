package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import com.Twins730.guineapigs.objects.item.GuineaPigPouchItem;
import com.Twins730.guineapigs.objects.item.GuineaPigSpawnEggItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GuineaPigMod.MOD_ID);

    public static final RegistryObject<Item> ALFALFA = ITEMS.register("alfalfa", ()-> new Item((new Item.Properties()).group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> ALFALFA_SEEDS = ITEMS.register("alfalfa_seeds", ()-> new BlockNamedItem(BlockInit.ALFALFA.get(), (new Item.Properties()).group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> GUINEA_PIG_SPAWN_EGG = ITEMS.register("guinea_pig_spawn_egg", ()-> new GuineaPigSpawnEggItem(EntityInit.GUINEA_PIG,13540481,11164735,(new Item.Properties()).group(ItemGroup.MISC)));

    public static final RegistryObject<Item> GUINEA_PIG_POUCH = ITEMS.register("guinea_pig_pouch", ()-> new GuineaPigPouchItem(EntityInit.GUINEA_PIG,(new Item.Properties()).group(ItemGroup.MATERIALS).maxStackSize(1)));
    public static final RegistryObject<Item> EMPTY_GUINEA_PIG_POUCH = ITEMS.register("empty_guinea_pig_pouch", ()-> new Item((new Item.Properties()).group(ItemGroup.MATERIALS).maxStackSize(16)));

}
