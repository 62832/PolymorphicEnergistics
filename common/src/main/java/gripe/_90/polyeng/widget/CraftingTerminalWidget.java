package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.menu.slot.CraftingTermSlot;
import com.illusivesoulworks.polymorph.api.client.base.ITickingRecipesWidget;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import gripe._90.polyeng.PolymorphicEnergistics;
import gripe._90.polyeng.mixin.AEBaseMenuAccessor;
import gripe._90.polyeng.mixin.AbstractContainerScreenAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class CraftingTerminalWidget extends PlayerRecipesWidget implements ITickingRecipesWidget {
    private Slot outputSlot;
    private int menuHeight;

    public CraftingTerminalWidget(CraftingTermScreen<?> screen, Slot outputSlot) {
        super(screen, outputSlot);
        this.outputSlot = outputSlot;
    }

    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        var menu = ((CraftingTermScreen<?>) containerScreen).getMenu();
        menu.getPlayer().getLevel().getRecipeManager().byKey(id).ifPresent(recipe -> ((AEBaseMenuAccessor) menu)
                .invokeSendClientAction(PolymorphicEnergistics.ACTION));
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
                if (slot instanceof CraftingTermSlot craftingSlot) {
                    outputSlot = craftingSlot;
                    resetWidgetOffsets();
                    break;
                }
            }

            menuHeight = screen.getImageHeight();
        }
    }
}
