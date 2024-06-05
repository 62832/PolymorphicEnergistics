package gripe._90.polyeng.widget;

import appeng.client.gui.me.common.MEStorageScreen;
import appeng.menu.me.common.MEStorageMenu;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.mojang.datafixers.util.Pair;
import gripe._90.polyeng.PolymorphicEnergistics;
import gripe._90.polyeng.mixin.AEBaseMenuAccessor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public abstract class BaseTerminalWidget<M extends MEStorageMenu, S extends MEStorageScreen<M>>
        extends PlayerRecipesWidget {
    private static final WidgetSprites OUTPUT = sprites("output_button");
    private static final WidgetSprites CURRENT_OUTPUT = sprites("current_output");
    private static final WidgetSprites SELECTOR = sprites("selector_button");

    protected final M menu;

    public BaseTerminalWidget(S screen, Slot outputSlot) {
        super(screen, outputSlot);
        menu = screen.getMenu();
    }

    private static WidgetSprites sprites(String base) {
        return new WidgetSprites(
                new ResourceLocation(PolymorphicEnergistics.MODID, base),
                new ResourceLocation(PolymorphicEnergistics.MODID, base + "_highlighted"));
    }

    @SuppressWarnings("resource")
    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        menu.getPlayer().level().getRecipeManager().byKey(id).ifPresent(recipe -> ((AEBaseMenuAccessor) menu)
                .callSendClientAction(PolymorphicEnergistics.ACTION));
    }

    @Override
    public Pair<WidgetSprites, WidgetSprites> getOutputSprites() {
        return Pair.of(OUTPUT, CURRENT_OUTPUT);
    }

    @Override
    public WidgetSprites getSelectorSprites() {
        return SELECTOR;
    }
}
