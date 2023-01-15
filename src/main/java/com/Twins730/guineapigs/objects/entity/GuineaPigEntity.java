package com.Twins730.guineapigs.objects.entity;

import com.Twins730.guineapigs.init.EntityInit;
import com.Twins730.guineapigs.init.ItemInit;
import com.Twins730.guineapigs.init.SoundInit;
import com.Twins730.guineapigs.init.TagInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuineaPigEntity extends TamableAnimal {

    private static final EntityDataAccessor<Integer> GUINEA_PIG_TYPE = SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.INT);
    private static Ingredient TEMPTATION_ITEMS;
    private TemptGoal temptGoal;

    public GuineaPigEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
        this.setTame(false);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    public static  AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }


    @Override
    protected void registerGoals() {
        TEMPTATION_ITEMS = Ingredient.of(getTempingItems());
        this.temptGoal = new GuineaPigTemptGoal(this, 0.6D, TEMPTATION_ITEMS);
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, this.temptGoal);
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(10, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 0.8D, 1.0000001E-5F));
        this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 10.0F));
    }
    protected ItemStack getGuineaPigPouch() {
        return new ItemStack(ItemInit.GUINEA_PIG_POUCH.get());
    }

    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance instance, MobSpawnType spawnReason, @Nullable SpawnGroupData data, @Nullable CompoundTag nbt) {
        data = super.finalizeSpawn(accessor, instance, spawnReason, data, nbt);
        if(nbt != null && nbt.contains("GuineaVariantTag", 3)){
            this.setVariant(nbt.getInt("GuineaVariantTag"));
            this.setTame(nbt.getBoolean("GuineaTamedTag"));
            this.setBaby(nbt.getBoolean("GuineaChildTag"));
            if (this.isTame()) {
                this.setOwnerUUID(UUID.fromString(nbt.getString("GuineaOwnerUUIDTag")));
            }
            this.playSound(SoundEvents.ARMOR_EQUIP_LEATHER,1,1);
        }
        return data;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(item == ItemInit.EMPTY_GUINEA_PIG_POUCH.get() && isAlive()){
            itemstack.shrink(1);
            ItemStack pouch = this.getGuineaPigPouch();
            this.setPouchData(pouch);
            player.setItemInHand(hand, pouch);
            this.discard();
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        if(this.isTame()){
            if(TEMPTATION_ITEMS.test(itemstack) && this.getHealth() < this.getMaxHealth()){
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.heal(3f);
                return InteractionResult.SUCCESS;
            }

            InteractionResult actionresulttype = super.mobInteract(player, hand);
            if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                return InteractionResult.SUCCESS;
            }

            return actionresulttype;
        } else {
            if(item == ItemInit.ALFALFA.get()){
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    this.playSound(SoundEvents.PARROT_EAT, 1.0F, 1.0F);
                }
                if (this.random.nextInt(5) == 0) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    protected void setPouchData(ItemStack pouch) {
        if (this.hasCustomName()) {
            pouch.setHoverName(this.getCustomName());
        }
        CompoundTag compoundnbt = pouch.getOrCreateTag();
        compoundnbt.putInt("GuineaVariantTag", this.getVariant());
        compoundnbt.putBoolean("GuineaTamedTag", this.isTame());
        if (isTame()) {
            compoundnbt.putString("GuineaOwnerUUIDTag", this.getOwnerUUID().toString());
        }
        compoundnbt.putBoolean("GuineaChildTag", this.isBaby());
    }

    @Override
    public boolean canMate(Animal entity) {
        if(entity instanceof GuineaPigEntity){
            return !entity.isBaby();
        }
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GUINEA_PIG_TYPE, 0);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.GUINEA_PIG_SQUEAK.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.GUINEA_PIG_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return (this.isTempting()) ? SoundInit.GUINEA_PIG_CALL.get() : SoundInit.GUINEA_PIG_WHEEK.get();
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(GUINEA_PIG_TYPE), 0, 5);
    }

    public void setVariant(int variantIn) {
        this.entityData.set(GUINEA_PIG_TYPE, variantIn);
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }


    @Override
    public boolean isFood(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    private boolean isTempting(){
        return this.temptGoal != null && this.temptGoal.canUse();
    }

    private static Item[] getTempingItems(){
        List<Item> foodItems = new ArrayList<>();
       for(Item item : ForgeRegistries.ITEMS.getValues()){
           if(new ItemStack(item).is(TagInit.GUINEA_PIG_EDIBLES)){
               foodItems.add(item);
           }
       }
       Item[] items = new Item[foodItems.size()];
       for(int i = 0;i < foodItems.size();i++){
           items[i] = foodItems.get(i);
       }

       return items;
    }

    @Nullable
    @Override
    public GuineaPigEntity getBreedOffspring(ServerLevel level, AgeableMob mob) {
        GuineaPigEntity guineaPig = EntityInit.GUINEA_PIG.get().create(level);
        guineaPig.setVariant(random.nextInt(5));
        UUID uuid = this.getOwnerUUID();
        if(uuid != null) {
            guineaPig.setTame(true);
            guineaPig.setOwnerUUID(uuid);
        }
        return guineaPig;
    }

    static class GuineaPigTemptGoal extends TemptGoal {
        @Nullable
        private Player selectedPlayer;
        private final GuineaPigEntity guinea;

        public GuineaPigTemptGoal(GuineaPigEntity guineaPig, double p_28220_, Ingredient temptingItems) {
            super(guineaPig, p_28220_, temptingItems, false);
            this.guinea = guineaPig;
        }

        public void tick() {
            super.tick();
            if (this.selectedPlayer == null && this.mob.getRandom().nextInt(this.adjustedTickDelay(600)) == 0) {
                this.selectedPlayer = this.player;
            } else if (this.mob.getRandom().nextInt(this.adjustedTickDelay(500)) == 0) {
                this.selectedPlayer = null;
            }

        }

        public boolean canUse() {
            return super.canUse() && !this.guinea.isTame();
        }
    }
}
