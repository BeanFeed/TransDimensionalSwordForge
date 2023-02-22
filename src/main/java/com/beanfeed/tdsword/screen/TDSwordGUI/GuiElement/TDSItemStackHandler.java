package com.beanfeed.tdsword.screen.TDSwordGUI.GuiElement;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class TDSItemStackHandler extends ItemStackHandler {
    public TDSItemStackHandler(int size)
    {
        super(size);
    }
    @Override
    public int getSlotLimit(int slot)
    {
        if(this.getSlots() == 1) return 1;
        return 64;
    }
}
