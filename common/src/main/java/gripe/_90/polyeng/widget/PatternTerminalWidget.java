package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.menu.me.items.PatternEncodingTermMenu;
import appeng.menu.slot.PatternTermSlot;
import appeng.parts.encoding.EncodingMode;
import com.illusivesoulworks.polymorph.api.client.base.ITickingRecipesWidget;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import gripe._90.polyeng.PolymorphicEnergistics;
import gripe._90.polyeng.mixin.AEBaseMenuAccessor;
import gripe._90.polyeng.mixin.AbstractContainerScreenAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class PatternTerminalWidget extends PlayerRecipesWidget implements ITickingRecipesWidget {
    private final PatternEncodingTermMenu menu;
    private Slot outputSlot;
    private int menuHeight;

    public PatternTerminalWidget(PatternEncodingTermScreen<?> screen, Slot outputSlot) {
        super(screen, outputSlot);
        this.outputSlot = outputSlot;
        menu = screen.getMenu();
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

    @Override
    public Slot getOutputSlot() {
        return outputSlot;
    }

    @Override
    public void tick() {
        var screen = (AbstractContainerScreenAccessor) containerScreen;

        if (screen.getImageHeight() != menuHeight) {
            for (var slot : containerScreen.getMenu().slots) {
                if (slot instanceof PatternTermSlot patternSlot) {
                    outputSlot = patternSlot;
                    resetWidgetOffsets();
                    break;
                }
            }

            menuHeight = screen.getImageHeight();
        }
    }
}
