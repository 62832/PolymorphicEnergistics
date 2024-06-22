package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.menu.me.items.CraftingTermMenu;
import net.minecraft.world.inventory.Slot;

public class CraftingTerminalWidget<M extends CraftingTermMenu> extends BaseTerminalWidget<M, CraftingTermScreen<M>> {
    public CraftingTerminalWidget(CraftingTermScreen<M> screen, Slot outputSlot) {
        super(screen, outputSlot);
    }
}
