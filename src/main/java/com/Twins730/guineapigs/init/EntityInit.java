package com.Twins730.guineapigs.init;

import com.Twins730.guineapigs.GuineaPigMod;
import com.Twins730.guineapigs.objects.entity.GuineaPigEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GuineaPigMod.MOD_ID);

    public static final RegistryObject<EntityType<GuineaPigEntity>> GUINEA_PIG = ENTITIES.register("guinea_pig",()-> EntityType.Builder.of(GuineaPigEntity::new , MobCategory.CREATURE).sized(0.5f,0.5f).clientTrackingRange(10).build(new ResourceLocation("guinea_pig").toString()));


    public static void makeAttributes(EntityAttributeCreationEvent event) {
        event.put(GUINEA_PIG.get(), GuineaPigEntity.setCustomAttributes().build());
    }

}
