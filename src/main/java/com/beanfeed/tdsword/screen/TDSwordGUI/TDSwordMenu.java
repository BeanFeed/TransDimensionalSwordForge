package com.beanfeed.tdsword.screen.TDSwordGUI;

import com.beanfeed.tdsword.TransDimensionalSword;
import com.beanfeed.tdsword.items.TDItems;
import com.beanfeed.tdsword.items.TDSword;
import com.beanfeed.tdsword.screen.SlotHandler.TDSMGoldHandler;
import com.beanfeed.tdsword.screen.SlotHandler.TDSMLapisHandler;
import com.beanfeed.tdsword.screen.SlotHandler.TDSMRuneHandler;
import com.beanfeed.tdsword.screen.SlotHandler.TDSMTearHandler;
import com.beanfeed.tdsword.screen.TDMenuTypes;
import com.beanfeed.tdsword.screen.TDSwordGUI.GuiElement.TDSItemStackHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;
import org.apache.commons.lang3.SerializationUtils;

public class TDSwordMenu extends AbstractContainerMenu {
    private final Level level;
    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private final TDSItemStackHandler tdsitemHandler= new TDSItemStackHandler(3);
    private final ItemStack itemStack;
    public final boolean isActivated;
    //private final ContainerData data;

    public TDSwordMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.getMainHandItem(), new ItemStackHandler(3));
    }

    public TDSwordMenu(int id, Inventory inv, ItemStack itemStack, ItemStackHandler slots) {
        super(TDMenuTypes.TD_SWORD_MENU.get(), id);
        checkContainerSize(inv, 2);
        //this.itemStack = itemStack;
        this.level = inv.player.level;
        if(slots.getStackInSlot(0).is(Items.GHAST_TEAR)) {
            this.tdsitemHandler.setStackInSlot(0,slots.getStackInSlot(0));
        }
        this.itemHandler.deserializeNBT(itemStack.getOrCreateTag().getCompound("inventory"));
        this.itemStack = itemStack;
        //TransDimensionalSword.LOGGER.info("Does sword contain Active key: " + String.valueOf(itemStack.getOrCreateTag().contains("active")));
        //TransDimensionalSword.LOGGER.info("Key value: " + String.valueOf(itemStack.getOrCreateTag().getBoolean("active")));
        this.isActivated = itemStack.getOrCreateTag().contains("active") && itemStack.getOrCreateTag().getBoolean("active");

        //this.data = data;
        addPlayerHotbar(inv);
        addPlayerInventory(inv);
        addSwordSlots();
    }
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 36;

    // THIS YOU HAVE TO DEFINE!
    private int TE_INVENTORY_SLOT_COUNT = 1;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();
        if(this.isActivated) TE_INVENTORY_SLOT_COUNT = 3;
        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            //System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return pPlayer.getMainHandItem().getItem() instanceof TDSword;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 93 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 151));
        }
    }
    private void addSwordSlots() {
        if(this.isActivated)
        {
            this.addSlot(new TDSMGoldHandler(itemHandler, 0,62, 33));
            this.addSlot(new TDSMLapisHandler(itemHandler, 1,80, 33));
            this.addSlot(new TDSMRuneHandler(itemHandler, 2, 98, 33));
        }
        else {
            this.addSlot(new TDSMTearHandler(tdsitemHandler, 0,80, 33));

        }
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        CompoundTag nbt = itemStack.getOrCreateTag();
        if(isActivated) nbt.put("inventory", itemHandler.serializeNBT());
        else nbt.put("inventory", tdsitemHandler.serializeNBT());
    }
}
