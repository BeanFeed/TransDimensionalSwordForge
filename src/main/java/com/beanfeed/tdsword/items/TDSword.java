package com.beanfeed.tdsword.items;

import com.beanfeed.tdsword.TransDimensionalSword;
import com.beanfeed.tdsword.Utils;
import com.beanfeed.tdsword.screen.TDSwordGUI.TDSwordMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class TDSword extends Item {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private Vec3 lastWaypoint = null;
    private boolean isActivated = false;
    private int lapisSlot = 0;
    private int lapisAmount = 0;
    private int goldSlot = 1;
    private int goldAmount = 0;
    //protected final ContainerData data;
    private List<Vec3> Waypoints;
    private float lastWaypointYRotation = 0.0f;
    private ResourceKey<Level> lastDim = null;
    public TDSword(Properties pProperties) {
        super(pProperties);
        /*
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    default -> {
                        if(isActivated) yield 1;
                        yield 0;
                    }
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> TDSword.this.setActivated(pValue);
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };

         */
    }

    //Returns saved waypoint
    public Vec3 getLastWaypoint(ItemStack stack) {
        updateItemHandler(stack);
        ItemStack rune = itemHandler.getStackInSlot(2);
        CompoundTag nbt = rune.getOrCreateTag();
        //TransDimensionalSword.LOGGER.info(String.valueOf(rune));
        if(!nbt.contains("waypoint")) return null;
        BlockPos pos = NbtUtils.readBlockPos(nbt.getCompound("waypoint"));
        return Utils.BlockPosToVec3(pos);
    }
    public float getLastWaypointRotation(ItemStack stack) {
        updateItemHandler(stack);
        ItemStack rune = itemHandler.getStackInSlot(2);
        CompoundTag nbt = rune.getOrCreateTag();
        if(!nbt.contains("rotation")) return 0.0f;
        return nbt.getFloat("rotation");
    }
    public ResourceKey<Level> getLastDimension(ItemStack stack) {
        updateItemHandler(stack);
        ItemStack rune = itemHandler.getStackInSlot(2);
        CompoundTag nbt = rune.getOrCreateTag();
        if(!nbt.contains("dimension")) return null;
        ResourceLocation keyLocation = ResourceLocation.tryParse(nbt.getString("dimension"));
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, keyLocation);
    }
    public int getGoldAmount(ItemStack stack) {
        updateItemHandler(stack);
        return itemHandler.getStackInSlot(0).getCount();
    }
    public void setGoldAmount(ItemStack stack, int amount) {
        updateItemHandler(stack);
        itemHandler.getStackInSlot(0).setCount(amount);
        stack.getOrCreateTag().put("inventory", itemHandler.serializeNBT());
    }
    public boolean isActivated(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if(!nbt.contains("active")) {
            nbt.putBoolean("active", false);
            return false;
        }
        return nbt.getBoolean("active");
    }
    private void updateActive(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if(!nbt.contains("active")) {
            nbt.putBoolean("active", false);
            isActivated = false;
        }
        isActivated = nbt.getBoolean("active");
    }
    //public SimpleContainer inv = new SimpleContainer(2);
    private void setActivated(int value) {
        isActivated = value == 1;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide()) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        //TransDimensionalSword.LOGGER.info(pUsedHand.toString());
        updateActive(pPlayer.getItemInHand(pUsedHand));
        if(!pPlayer.isCrouching())
        {
            /*
            if(pPlayer.getOffhandItem().is(Items.LAPIS_LAZULI)) {
                //If item is lapis
                pPlayer.getOffhandItem().shrink(1);
                if(inv.getItem(lapisSlot) == ItemStack.EMPTY) {
                    inv.setItem(lapisSlot, new ItemStack(Items.LAPIS_LAZULI, 1));
                }
            } else {
                //If item is gold ingot
                pPlayer.getOffhandItem().shrink(1);
                if(inv.getItem(goldSlot) == ItemStack.EMPTY) {
                    inv.setItem(goldSlot, new ItemStack(Items.GOLD_INGOT, 1));
                }
            }
            */
            ItemStack stack = pPlayer.getItemInHand(pUsedHand);
            CompoundTag nbt = stack.getOrCreateTag();
            CompoundTag storedItemNBT = new CompoundTag();
            if(!nbt.contains("inventory")) {
                nbt.put("inventory", storedItemNBT);
            }
            itemHandler.deserializeNBT(nbt.getCompound("inventory"));
            if(!pPlayer.level.isClientSide()) {
                pPlayer.openMenu(new SimpleMenuProvider(
                        (containerid, playerInventory, player) -> new TDSwordMenu(containerid, playerInventory, playerInventory.player.getItemInHand(pUsedHand), itemHandler),
                        Component.translatable("menu.title.tdsword.tdswordmenu")
                ));
                //TransDimensionalSword.LOGGER.info("Open");
            }
        } else if(isActivated){
            updateItemHandler(pPlayer.getItemInHand(pUsedHand));
            ItemStack lapisStack = itemHandler.getStackInSlot(1);
            if(lapisStack.getCount() == 0) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            ItemStack itemStack = itemHandler.getStackInSlot(2);
            TransDimensionalSword.LOGGER.info(String.valueOf(Rune.getWaypointNBT(itemStack, pPlayer)));
            lapisStack.shrink(1);
            itemStack.save(Rune.getWaypointNBT(itemStack, pPlayer));
            itemStack.setHoverName(Component.translatable("item.tdsword.filled_rune"));
            CompoundTag nbt = pPlayer.getItemInHand(pUsedHand).getOrCreateTag();
            nbt.put("inventory", itemHandler.serializeNBT());
            //var rotation = new Vec3(Math.round(tempLastWaypointRotation), 0, Math.round(tempLastWaypointRotation);

        }else {
            updateItemHandler(pPlayer.getItemInHand(pUsedHand));
            ItemStack stack = itemHandler.getStackInSlot(0);
            TransDimensionalSword.LOGGER.info("Check Activate");
            TransDimensionalSword.LOGGER.info(String.valueOf(stack.getCount() == 0));
            if(stack.getCount() == 0) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            else {
                isActivated = true;
                CompoundTag nbt = stack.getOrCreateTag();
                nbt.putBoolean("active", isActivated);
                stack.save(nbt);
                TransDimensionalSword.LOGGER.info(String.valueOf(isActivated(stack)));
            }

        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }
    private void updateItemHandler(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

}
