package gripe._90.polyeng.integration;

import appeng.menu.me.items.CraftingTermMenu;
import de.mari_023.ae2wtlib.wct.WCTMenu;

public class AE2WTLibIntegration {
    public static int getWidgetYPos(CraftingTermMenu menu, int widgetY, int offset, int fallback) {
        return menu instanceof WCTMenu ? widgetY - offset : fallback;
    }
}
