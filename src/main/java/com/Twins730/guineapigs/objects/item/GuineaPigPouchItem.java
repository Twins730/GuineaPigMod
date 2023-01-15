package com.Twins730.guineapigs.objects.item;

import com.Twins730.guineapigs.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import java.util.function.Supplier;

public class GuineaPigPouchItem extends Item {

    private final Supplier<? extends EntityType<?>> guineaPigTypeSupplier;

    public GuineaPigPouchItem(Supplier<? extends EntityType<?>> guineaPigTypeSupplier, Item.Properties builder) {
        super(builder);
        this.guineaPigTypeSupplier = guineaPigTypeSupplier;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(context.getLevel() instanceof ServerLevel){
            this.placeGuineaPig((ServerLevel) context.getLevel(),stack,context.getClickedPos());
        }
        context.getPlayer().playSound(SoundEvents.ARMOR_EQUIP_LEATHER,1,1);
        context.getPlayer().getUseItem().shrink(1);
        context.getPlayer().setItemInHand(context.getHand(), new ItemStack(ItemInit.EMPTY_GUINEA_PIG_POUCH.get()));
        return super.onItemUseFirst(stack, context);
    }

    private void placeGuineaPig(ServerLevel worldIn, ItemStack stack, BlockPos pos){
        this.guineaPigTypeSupplier.get().spawn(worldIn, stack, (Player) null, pos, MobSpawnType.BUCKET, true, false);
    }
}
