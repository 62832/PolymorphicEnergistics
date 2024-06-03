package gripe._90.polyeng.widget;

import appeng.client.gui.me.common.MEStorageScreen;
import appeng.menu.me.common.MEStorageMenu;
import com.illusivesoulworks.polymorph.api.client.base.ITickingRecipesWidget;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import gripe._90.polyeng.PolymorphicEnergistics;
import gripe._90.polyeng.mixin.AEBaseMenuAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public abstract class BaseTerminalWidget<M extends MEStorageMenu, S extends MEStorageScreen<M>>
        extends PlayerRecipesWidget implements ITickingRecipesWidget {
    protected final M menu;
    private final Class<? extends Slot> slotClass;

    private Slot outputSlot;
    private int menuHeight;

    public BaseTerminalWidget(S screen, Slot outputSlot, Class<? extends Slot> slotClass) {
        super(screen, outputSlot);
        this.outputSlot = outputSlot;
        this.slotClass = slotClass;
        menu = screen.getMenu();
    }

    @SuppressWarnings("resource")
    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        menu.getPlayer().level().getRecipeManager().byKey(id).ifPresent(recipe -> ((AEBaseMenuAccessor) menu)
                .invokeSendClientAction(PolymorphicEnergistics.ACTION));
    }

    @Override
    public Slot getOutputSlot() {
        return outputSlot;
    }

    @Override
    public void tick() {
        if (containerScreen.imageHeight != menuHeight) {
            for (var slot : menu.slots) {
                if (slotClass.isInstance(slot)) {
                    outputSlot = slot;
                    resetWidgetOffsets();
                    break;
                }
            }

            menuHeight = containerScreen.imageHeight;
        }
    }
}
