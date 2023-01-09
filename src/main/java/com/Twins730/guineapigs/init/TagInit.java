package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TagInit {

    public static final ITag.INamedTag<Item> GUINEA_PIG_EDIBLES = ItemTags.createOptional(new ResourceLocation(GuineaPigMod.MOD_ID,"guinea_pig_edibles"));

}
