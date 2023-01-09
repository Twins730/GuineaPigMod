package com.Twins730.guineapigs.objects.entity;

import com.Twins730.guineapigs.init.EntityInit;
import com.Twins730.guineapigs.init.ItemInit;
import com.Twins730.guineapigs.init.SoundInit;
import com.Twins730.guineapigs.init.TagInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuineaPigEntity extends TameableEntity {

    private static final DataParameter<Integer> GUINEA_PIG_TYPE = EntityDataManager.createKey(GuineaPigEntity.class, DataSerializers.VARINT);
    private static Ingredient TEMPTATION_ITEMS;
    private TemptGoal temptGoal;

    public GuineaPigEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void registerGoals() {
        TEMPTATION_ITEMS = Ingredient.fromItems(getTempingItems());
        this.temptGoal = new GuineaPigEntity.TemptGoal(this, 0.6D, TEMPTATION_ITEMS);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(10, new PanicGoal(this, 1));
        this.goalSelector.addGoal(8,temptGoal);
    }
    protected ItemStack getGuineaPigPouch() {
        return new ItemStack(ItemInit.GUINEA_PIG_POUCH.get());
    }

    protected float getSoundVolume() {
        return 0.5F;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld serverWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData data, @Nullable CompoundNBT nbt) {
        data = super.onInitialSpawn(serverWorld, difficultyInstance, spawnReason, data, nbt);
        if(nbt != null && nbt.contains("GuineaVariantTag", 3)){
            this.setVariant(nbt.getInt("GuineaVariantTag"));
            this.setTamed(nbt.getBoolean("GuineaTamedTag"));
            this.setChild(nbt.getBoolean("GuineaChildTag"));
        }
        return data;
    }

    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if(item == ItemInit.EMPTY_GUINEA_PIG_POUCH.get() && isAlive()){
            itemstack.shrink(1);
            ItemStack pouch = this.getGuineaPigPouch();
            this.setPouchData(pouch);

            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, pouch);
            } else if (!player.inventory.addItemStackToInventory(pouch)) {
                player.dropItem(pouch, false);
            }

            this.remove();
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }

        if(this.isTamed()){
            if(TEMPTATION_ITEMS.test(itemstack)){
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                this.heal(3f);
                return ActionResultType.SUCCESS;
            }

            ActionResultType actionresulttype = super.func_230254_b_(player, hand);
            if (!actionresulttype.isSuccessOrConsume() && this.isOwner(player)) {
                this.func_233687_w_(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                return ActionResultType.SUCCESS;
            }

            return actionresulttype;
        } else {
            if(item == ItemInit.ALFALFA.get()){
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                if (this.rand.nextInt(9) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.world.setEntityState(this, (byte)6);
                }

                return ActionResultType.SUCCESS;
            }
        }

        return super.func_230254_b_(player, hand);
    }

    protected void setPouchData(ItemStack pouch) {
        if (this.hasCustomName()) {
            pouch.setDisplayName(this.getCustomName());
        }
        CompoundNBT compoundnbt = pouch.getOrCreateTag();
        compoundnbt.putInt("GuineaVariantTag", this.getVariant());
        compoundnbt.putBoolean("GuineaTamedTag", this.isTamed());
        compoundnbt.putBoolean("GuineaChildTag", this.isChild());
    }


    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity entity) {
        GuineaPigEntity guineaPig = EntityInit.GUINEA_PIG.get().create(world);
        UUID uuid = this.getOwnerId();
        guineaPig.setVariant(rand.nextInt(5));
        guineaPig.setOwnerId(uuid);
        guineaPig.setTamed(true);
        return guineaPig;
    }

    @Override
    public boolean canMateWith(AnimalEntity entity) {
        if(entity instanceof GuineaPigEntity){
            return ((GuineaPigEntity) entity).isTamed() == this.isTamed();
        }
        return false;
    }


    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(GUINEA_PIG_TYPE, 0);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
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
        return this.isTempting() ? SoundInit.GUINEA_PIG_CALL.get() : SoundInit.GUINEA_PIG_WHEEK.get();
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataManager.get(GUINEA_PIG_TYPE), 0, 5);
    }

    public void setVariant(int variantIn) {
        this.dataManager.set(GUINEA_PIG_TYPE, variantIn);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.getVariant());
    }


    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    private boolean isTempting(){
        return this.temptGoal != null && this.temptGoal.isRunning();
    }

    private static Item[] getTempingItems(){
        List<Item> foodItems = new ArrayList<>();
       for(Item item : ForgeRegistries.ITEMS.getValues()){
           if(item.isIn(TagInit.GUINEA_PIG_EDIBLES)){
               foodItems.add(item);
           }
       }
       Item[] items = new Item[foodItems.size()];
       for(int i = 0;i < foodItems.size();i++){
           items[i] = foodItems.get(i);
       }

       return items;
    }

    static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {

        @Nullable
        private PlayerEntity temptingPlayer;
        private final GuineaPigEntity guineaPigEntity;

        public TemptGoal(GuineaPigEntity guineaPig, double speedIn, Ingredient temptItemsIn) {
            super(guineaPig, speedIn, temptItemsIn, false);
            this.guineaPigEntity = guineaPig;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            super.tick();
            if (this.temptingPlayer == null && this.creature.getRNG().nextInt(600) == 0) {
                this.temptingPlayer = this.closestPlayer;
            } else if (this.creature.getRNG().nextInt(500) == 0) {
                this.temptingPlayer = null;
            }

        }



        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return super.shouldExecute() && !guineaPigEntity.isTamed();
        }
    }
}
