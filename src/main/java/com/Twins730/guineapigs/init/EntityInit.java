package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import com.Twins730.guineapigs.objects.entity.GuineaPigEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, GuineaPigMod.MOD_ID);

    public static final RegistryObject<EntityType<GuineaPigEntity>> GUINEA_PIG = ENTITIES.register("guinea_pig",()-> EntityType.Builder.create(GuineaPigEntity::new ,EntityClassification.MISC).size(0.5f,0.5f).build(new ResourceLocation("guinea_pig").toString()));


    public static void makeAttributes(EntityAttributeCreationEvent event) {
        event.put(GUINEA_PIG.get(), GuineaPigEntity.setCustomAttributes().create());
    }

}
