package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.menu.SlotSemantics;
import appeng.menu.me.items.CraftingTermMenu;
import gripe._90.polyeng.integration.AE2WTLibIntegration;
import net.neoforged.fml.ModList;

public class CraftingTerminalWidget<M extends CraftingTermMenu> extends BaseTerminalWidget<M, CraftingTermScreen<M>> {
    public CraftingTerminalWidget(CraftingTermScreen<M> screen) {
        super(screen, screen.getMenu().getSlots(SlotSemantics.CRAFTING_RESULT).getFirst());
    }

    @Override
    public int getYPos() {
        return ModList.get().isLoaded("ae2wtlib") && AE2WTLibIntegration.isWirelessTerminal(menu)
                ? getOutputSlot().y - WIDGET_Y_OFFSET - 7
                : super.getYPos();
    }
}
