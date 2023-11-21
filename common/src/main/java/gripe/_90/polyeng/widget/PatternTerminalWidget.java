package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.menu.me.items.PatternEncodingTermMenu;
import appeng.parts.encoding.EncodingMode;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import gripe._90.polyeng.PolymorphicEnergistics;
import gripe._90.polyeng.mixin.AEBaseMenuAccessor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class PatternTerminalWidget extends PlayerRecipesWidget {
    private final PatternEncodingTermMenu menu;

    public PatternTerminalWidget(AbstractContainerScreen<?> containerScreen, Slot outputSlot) {
        super(containerScreen, outputSlot);
        this.menu = ((PatternEncodingTermScreen<?>) containerScreen).getMenu();
    }

    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        menu.getPlayer().getLevel().getRecipeManager().byKey(id).ifPresent(recipe -> ((AEBaseMenuAccessor) menu)
                .invokeSendClientAction(PolymorphicEnergistics.ACTION));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float renderPartialTicks) {
        if (menu.mode == EncodingMode.CRAFTING) {
            super.render(poseStack, mouseX, mouseY, renderPartialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return menu.mode == EncodingMode.CRAFTING && super.mouseClicked(mouseX, mouseY, button);
    }
}
