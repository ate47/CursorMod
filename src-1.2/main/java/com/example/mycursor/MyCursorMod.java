package com.example.mycursor;

import fr.atesab.customcursormod.CursorConfig;
import fr.atesab.customcursormod.CursorMod;
import fr.atesab.customcursormod.CursorTester;
import fr.atesab.customcursormod.CursorType;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/**
 * A example mod to show how to create new cursors
 * @author ATE47
 */
@Mod(name="My Cursor",modid="mycursor",dependencies="required-after:customcursormod@[1.2]")
public class MyCursorMod {
	@EventHandler
	public void preInit(FMLPreInitializationEvent ev) {
		/* create a default cursor config, with the resource link, hotspot (x,y) location (from top left) */
		CursorConfig defaultCursorConfig = new CursorConfig("textures/gui/customcursor_hand.png", 9, 1);
		
		/* create a tester when CursorMod search for a cursor */ 
		CursorTester cursorTester = new CursorTester() {
			@Override
			public boolean testCursor(CursorType newCursorType, GuiScreen currentScreen, int mouseX, int mouseY,
					float renderPartialTicks) {
				return currentScreen instanceof GuiMultiplayer; /* the condition to set this cursor */
			}
		};
		
		/* create the cursor with his config's name, localized name, the default cursor config and the tester */
		CursorType myCursor = new CursorType("mycursor", "My Cursor", defaultCursorConfig,	cursorTester);
		
		/* register the cursor */
		CursorMod.registerCursor(myCursor);
	}
}
