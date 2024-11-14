package gripe._90.polyeng.integration;

import appeng.menu.me.items.CraftingTermMenu;
import de.mari_023.ae2wtlib.wct.WCTMenu;

public class AE2WTLibIntegration {
    public static boolean isWirelessTerminal(CraftingTermMenu menu) {
        return menu instanceof WCTMenu;
    }
}
