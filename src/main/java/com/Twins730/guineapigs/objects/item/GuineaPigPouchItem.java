package com.Twins730.guineapigs.objects.item;

import com.Twins730.guineapigs.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class GuineaPigPouchItem extends Item {

    private final java.util.function.Supplier<? extends EntityType<?>> guineaPigTypeSupplier;

    public GuineaPigPouchItem(java.util.function.Supplier<? extends EntityType<?>> guineaPigTypeSupplier, Item.Properties builder) {
        super(builder);
        this.guineaPigTypeSupplier = guineaPigTypeSupplier;
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        if(context.getWorld() instanceof ServerWorld){
            this.placeGuineaPig((ServerWorld) context.getWorld(),stack,context.getPos());
        }
        context.getPlayer().addItemStackToInventory(new ItemStack(ItemInit.EMPTY_GUINEA_PIG_POUCH.get()));
        context.getPlayer().getHeldItemMainhand().shrink(1);
        return super.onItemUseFirst(stack, context);
    }

    private void placeGuineaPig(ServerWorld worldIn, ItemStack stack, BlockPos pos){
        this.guineaPigTypeSupplier.get().spawn(worldIn, stack, (PlayerEntity)null, pos, SpawnReason.BUCKET, true, false);
       // this.guineaPig.spawn(worldIn, stack, (PlayerEntity)null, pos, SpawnReason.BUCKET, true, false);
    }

    protected EntityType<?> getGuineaPig() {
        return guineaPigTypeSupplier.get();
    }
}
