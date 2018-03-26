package fr.atesab.customcursormod;

import net.minecraft.client.gui.GuiScreen;
public interface CursorTester {
	/**
	 * Test if a {@link CursorType} need to be set to the current cursor 
	 * @param newCursorType the {@link CursorType} the new cursor type
	 * @param currentScreen the current {@link GuiScreen}
	 * @param mouseX the current X location of the mouse
	 * @param mouseY the current Y location of the mouse
	 * @param renderPartialTicks 
	 * @return if the cursor need to be change
	 */
	public boolean testCursor(CursorType newCursorType, GuiScreen currentScreen, int mouseX, int mouseY, float renderPartialTicks);
}
