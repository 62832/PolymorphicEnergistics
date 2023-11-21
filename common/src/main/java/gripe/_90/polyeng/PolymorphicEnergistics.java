package gripe._90.polyeng;

import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.menu.slot.CraftingTermSlot;
import appeng.menu.slot.PatternTermSlot;
import com.illusivesoulworks.polymorph.api.PolymorphApi;
import gripe._90.polyeng.widget.CraftingTerminalWidget;
import gripe._90.polyeng.widget.PatternTerminalWidget;

public class PolymorphicEnergistics {
    public static final String MODID = "polyeng";

    public static final String ACTION = MODID + "$selectRecipe";

    public static void register() {
        PolymorphApi.client().registerWidget(screen -> {
            if (screen instanceof CraftingTermScreen<?> craftingTerminal) {
                for (var slot : craftingTerminal.getMenu().slots) {
                    if (slot instanceof CraftingTermSlot) {
                        return new CraftingTerminalWidget(craftingTerminal, slot);
                    }
                }
            }

            if (screen instanceof PatternEncodingTermScreen<?> patternTerminal) {
                for (var slot : patternTerminal.getMenu().slots) {
                    if (slot instanceof PatternTermSlot) {
                        return new PatternTerminalWidget(patternTerminal, slot);
                    }
                }
            }

            return null;
        });
    }
}
