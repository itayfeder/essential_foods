package com.itayfeder.essential_foods.client.screens;

import com.itayfeder.essential_foods.EssentialFoodsMod;
import com.itayfeder.essential_foods.common.containers.MillContainer;
import com.itayfeder.essential_foods.common.containers.SeedProducerContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SeedProducerScreen extends ContainerScreen<SeedProducerContainer> {
    private static final ResourceLocation MILL_GUI_TEXTURES = new ResourceLocation(EssentialFoodsMod.MOD_ID, "textures/gui/container/seed_producer.png");
    private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public SeedProducerScreen(SeedProducerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    protected void init() {
        super.init();
        this.titleX = (this.xSize - this.font.getStringPropertyWidth(this.title)) / 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(MILL_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        int i1 = this.container.tileEntity.productionTime;
        if (i1 > 0) {

            int j1 = BUBBLELENGTHS[i1 / 2 % 7];
            if (j1 > 0) {
                this.blit(matrixStack, i + 100, j + 28 + 29 - j1, 185, 29 - j1, 12, j1);
            }
        }
    }
}
