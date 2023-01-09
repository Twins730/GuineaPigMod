package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GuineaPigMod.MOD_ID);

    public static final RegistryObject<Block> ALFALFA = BLOCKS.register("alfalfa", ()-> new CropsBlock(AbstractBlock.Properties.from(Blocks.WHEAT)));

}
