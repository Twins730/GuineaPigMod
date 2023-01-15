package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GuineaPigMod.MOD_ID);

    public static final RegistryObject<Block> ALFALFA = BLOCKS.register("alfalfa", ()-> new CropBlock(Block.Properties.copy(Blocks.WHEAT)));

}
