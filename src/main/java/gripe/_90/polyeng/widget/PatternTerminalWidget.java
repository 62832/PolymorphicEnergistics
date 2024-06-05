package gripe._90.polyeng.widget;

import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.menu.me.items.PatternEncodingTermMenu;
import appeng.parts.encoding.EncodingMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.Slot;

public class PatternTerminalWidget<M extends PatternEncodingTermMenu>
        extends BaseTerminalWidget<M, PatternEncodingTermScreen<M>> {
    public PatternTerminalWidget(PatternEncodingTermScreen<M> screen, Slot outputSlot) {
        super(screen, outputSlot);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float renderPartialTicks) {
        if (menu.mode == EncodingMode.CRAFTING) {
            super.render(guiGraphics, mouseX, mouseY, renderPartialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return menu.mode == EncodingMode.CRAFTING && super.mouseClicked(mouseX, mouseY, button);
    }
}
