package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.CraftingTermScreen;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import gripe._90.polyeng.PolymorphicEnergistics;
import gripe._90.polyeng.mixin.AEBaseMenuAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class CraftingTerminalWidget extends PlayerRecipesWidget {
    public CraftingTerminalWidget(CraftingTermScreen<?> screen, Slot outputSlot) {
        super(screen, outputSlot);
    }

    @SuppressWarnings("resource")
    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        var menu = ((CraftingTermScreen<?>) containerScreen).getMenu();
        menu.getPlayer().level().getRecipeManager().byKey(id).ifPresent(recipe -> ((AEBaseMenuAccessor) menu)
                .invokeSendClientAction(PolymorphicEnergistics.ACTION));
    }
}
