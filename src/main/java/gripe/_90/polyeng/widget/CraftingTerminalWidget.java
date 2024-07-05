package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.menu.SlotSemantics;
import appeng.menu.me.items.CraftingTermMenu;

public class CraftingTerminalWidget<M extends CraftingTermMenu> extends BaseTerminalWidget<M, CraftingTermScreen<M>> {
    public CraftingTerminalWidget(CraftingTermScreen<M> screen) {
        super(screen, screen.getMenu().getSlots(SlotSemantics.CRAFTING_RESULT).getFirst());
    }
}
