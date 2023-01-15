package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;

import net.minecraft.world.item.Item;

public class TagInit {

    public static final TagKey<Item> GUINEA_PIG_EDIBLES = ItemTags.create(new ResourceLocation(GuineaPigMod.MOD_ID,"guinea_pig_edibles"));

}
