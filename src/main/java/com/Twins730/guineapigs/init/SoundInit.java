package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GuineaPigMod.MOD_ID);

    public static final RegistryObject<SoundEvent> GUINEA_PIG_SQUEAK = SOUNDS.register("guinea_pig_squeak", ()-> new SoundEvent(new ResourceLocation(GuineaPigMod.MOD_ID,"guineapigs.entity.guinea_pig.squeak")));
    public static final RegistryObject<SoundEvent> GUINEA_PIG_WHEEK = SOUNDS.register("guinea_pig_wheek", ()-> new SoundEvent(new ResourceLocation(GuineaPigMod.MOD_ID,"guineapigs.entity.guinea_pig.wheek")));
    public static final RegistryObject<SoundEvent> GUINEA_PIG_CALL = SOUNDS.register("guinea_pig_call", ()-> new SoundEvent(new ResourceLocation(GuineaPigMod.MOD_ID,"guineapigs.entity.guinea_pig.call")));
    public static final RegistryObject<SoundEvent> GUINEA_PIG_DEATH = SOUNDS.register("guinea_pig_death", ()-> new SoundEvent(new ResourceLocation(GuineaPigMod.MOD_ID,"guineapigs.entity.guinea_pig.death")));
}
