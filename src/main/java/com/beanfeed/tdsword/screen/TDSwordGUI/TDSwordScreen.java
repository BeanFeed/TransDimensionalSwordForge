package com.beanfeed.tdsword.screen.TDSwordGUI;

import com.beanfeed.tdsword.TransDimensionalSword;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TDSwordScreen extends AbstractContainerScreen<TDSwordMenu> {
    private static final ResourceLocation MAIN_TEXTURE =
    new ResourceLocation(TransDimensionalSword.MODID, "textures/gui/container/tdsmenu.png");
    private static final ResourceLocation ACTIVATE_TEXTURE =
    new ResourceLocation(TransDimensionalSword.MODID, "textures/gui/container/tdsactivate.png");
    private final boolean isActivated;
    public TDSwordScreen(TDSwordMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 175;
        this.imageWidth = 176;
        this.inventoryLabelY = 83;
        this.titleLabelY = 5;
        this.isActivated = pMenu.isActivated;
        //int titleWidth = this.font.width(pTitle.getString());
        //this.titleLabelX = (width - titleWidth)/2;
    }
    /*
    @Override
    protected void init() {
        super.init();
    }
    */
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if(isActivated) RenderSystem.setShaderTexture(0, MAIN_TEXTURE);
        else RenderSystem.setShaderTexture(0, ACTIVATE_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);


    }
    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int titleWidth = this.font.width(title.getString());
        Component nTitle = Component.translatable("menu.title.tdsword.alttdsmenu");
        if(!isActivated) {
            titleWidth = this.font.width(nTitle.getString());
        }
        //TransDimensionalSword.LOGGER.info(String.valueOf(width));
        this.titleLabelX = (imageWidth - titleWidth) / 2;
        if(!isActivated) this.font.draw(pPoseStack, nTitle, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        else this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(pPoseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
