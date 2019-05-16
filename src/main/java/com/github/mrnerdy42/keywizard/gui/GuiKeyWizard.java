package com.github.mrnerdy42.keywizard.gui;

import static org.lwjgl.input.Keyboard.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.lwjgl.input.Mouse;

import com.github.mrnerdy42.keywizard.util.KeybindUtils;
import com.github.mrnerdy42.keywizard.util.KeyboardFactory;
import com.github.mrnerdy42.keywizard.util.KeyboardLayout;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyModifier;

public class GuiKeyWizard extends GuiScreen {
	
	private final GuiScreen parentScreen;
	
	private int page = 1;
	private KeyBinding selectedKeybind;
	private KeyModifier activeModifier = KeyModifier.NONE;
	private String selectedCategory = "categories.all";
	private String searchText = "";


	private GuiCategorySelector categoryList;
	private GuiTextField searchBar;
	private GuiBindingList bindingList;
	private GuiButton buttonPage;
	private GuiButton buttonReset;
	private GuiButton buttonClear;
	private GuiButton buttonDone;
	private GuiButton buttonActiveModifier;
	
	protected GuiKeyboard keyboard;

	public GuiKeyWizard(Minecraft mcIn, GuiScreen parentScreen) {
		this.mc = mcIn;
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		
		int maxLength = 0;
	
		for (KeyBinding binding : KeybindUtils.ALL_BINDINGS) {
			if (binding.getDisplayName().length() > maxLength)
				maxLength = binding.getDisplayName().length();
		}
	
		int listWidth = (maxLength * 14);
	
		this.bindingList = new GuiBindingList(this, 10, this.height - 30, listWidth, this.height - 40,
				fontRenderer.FONT_HEIGHT * 2 + 10);
		
		this.searchBar = new GuiTextField(0, this.fontRenderer, 10, this.height - 20, listWidth, 14);
		this.searchBar.setFocused(true);
		this.searchBar.setCanLoseFocus(false);
	
		int guiX = listWidth + 15;
		int adjustedWidth = this.width - guiX;
		int keyboardY = this.height / 2 - 90;
		
		ArrayList<String> categories = KeybindUtils.getCategories();
		categories.add(0, "categories.conflicts");
		categories.add(0, "categories.unbound");
		categories.add(0, "categories.all");
		
		this.keyboard = KeyboardFactory.makeKeyboard(KeyboardLayout.QWERTY, this, guiX, keyboardY, adjustedWidth - 5, this.height/15);
		
		this.categoryList = new GuiCategorySelector(guiX, 5, 125, I18n.format("gui.bindingCategories"), categories);
		this.selectedCategory = this.categoryList.getSelctedCategory();
		this.buttonPage = new GuiButton(0, guiX + 105, 5, 100, 20, I18n.format("gui.page") + ": " + String.format("%d", this.page) );
	
		this.buttonReset = new GuiButton(0, guiX, this.height - 40, adjustedWidth / 3, 20, I18n.format("gui.resetBinding"));
		this.buttonClear = new GuiButton(0, guiX + adjustedWidth / 3 + 3, this.height - 40, adjustedWidth / 3, 20, I18n.format("gui.clearBinding"));
		this.buttonDone = new GuiButton(0, guiX + (adjustedWidth / 3)*2 + 6, this.height - 40, adjustedWidth / 3 - 10, 20, I18n.format("gui.done"));
		this.buttonActiveModifier = new GuiButton(1, guiX, this.height - 65, 125, 20,
				I18n.format("gui.activeModifier" )+ ": " + activeModifier.toString());
		
		this.setSelectedKeybind(this.bindingList.getSelectedKeybind());
		
	    //this.buttonList.add(this.buttonPage);
		this.buttonList.add(this.buttonActiveModifier);
		this.buttonList.add(this.buttonReset);
		this.buttonList.add(this.buttonClear);
		this.buttonList.add(this.buttonDone);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.bindingList.drawScreen(mouseX, mouseY, partialTicks);
		this.searchBar.drawTextBox();
		
		this.keyboard.draw(this.mc, mouseX, mouseY, partialTicks);
		this.categoryList.drawList(this.mc, mouseX, mouseY, partialTicks);
	
		// Color key and draw hovering text
		/*
		 *currentPage.forEach((keyId, keyButton) -> {
	     *
		 *	ArrayList<String> bindingNames = KeybindUtils.getBindingNames(keyId, this.activeModifier);
	     *
		 *	switch (bindingNames.size()) {
		 *	case 0:
		 *		keyButton.displayString = KeyHelper.translateKey(keyId);
		 *		break;
		 *	case 1:
		 *		keyButton.displayString = TextFormatting.GREEN + KeyHelper.translateKey(keyId);
		 *		break;
		 *	default:
		 *		keyButton.displayString = TextFormatting.RED + KeyHelper.translateKey(keyId);
		 *		break;
		 *	}
	     *
		 *	if (keyButton.isMouseOver() && !this.categoryList.getExtended()) {
		 *		drawHoveringText(KeybindUtils.getBindingNames(keyId, this.activeModifier), mouseX, mouseY,
		 *				fontRenderer);
		 *	}
		 *});
		 */
	}

	@Override
	public void updateScreen() {
	    super.updateScreen();
	    this.searchBar.updateCursorCounter();
	    if ( this.buttonReset != null )
	    	this.buttonReset.enabled = !this.selectedKeybind.isSetToDefaultValue();
		if ( this.buttonClear != null ) {
			this.buttonClear.enabled = !(this.selectedKeybind.getKeyCode() == 0);
		}
	
	    if ( this.categoryList != null )
	    	this.selectedCategory = this.categoryList.getSelctedCategory();
	    
	    if ( !this.searchBar.getText().equals(this.searchText) ) {
	    	this.searchText = this.searchBar.getText();
	    }
	    
	    if (this.activeModifier != null) {
	    	switch (this.activeModifier.toString()) {
	    	case "CONTROL":
	    		this.keyboard.disableKey(KEY_LCONTROL);
	    		this.keyboard.disableKey(KEY_RCONTROL);
	    		
	    		this.keyboard.enableKey(KEY_LMENU);
	    		this.keyboard.enableKey(KEY_RMENU);
	    		this.keyboard.enableKey(KEY_LSHIFT);
	    		this.keyboard.enableKey(KEY_RSHIFT);
	    		break;
	    	case "ALT":
	    		this.keyboard.disableKey(KEY_LMENU);
	    		this.keyboard.disableKey(KEY_RMENU);
	    		
	    		this.keyboard.enableKey(KEY_LCONTROL);
	    		this.keyboard.enableKey(KEY_RCONTROL);
	    		this.keyboard.enableKey(KEY_LSHIFT);
	    		this.keyboard.enableKey(KEY_RSHIFT);
	    		break;
	    	case "SHIFT":
	    		this.keyboard.disableKey(KEY_LSHIFT);
	    		this.keyboard.disableKey(KEY_RSHIFT);
	    		
	    		this.keyboard.enableKey(KEY_LCONTROL);
	    		this.keyboard.enableKey(KEY_RCONTROL);
	    		this.keyboard.enableKey(KEY_LMENU);
	    		this.keyboard.enableKey(KEY_RMENU);
	    		break;
	    	case "NONE" :
	    		this.keyboard.enableKey(KEY_LCONTROL);
	    		this.keyboard.enableKey(KEY_RCONTROL);
	    		this.keyboard.enableKey(KEY_LMENU);
	    		this.keyboard.enableKey(KEY_RMENU);
	    		this.keyboard.enableKey(KEY_LSHIFT);
	    		this.keyboard.enableKey(KEY_RSHIFT);
	    	}
	    }
	    
	    this.buttonPage.displayString = I18n.format("gui.page") + ": " + String.format("%d", this.page);
	    this.bindingList.updateList();
	}

	@Override
	protected void actionPerformed(GuiButton button) {

		if (button == this.buttonReset) {
			this.selectedKeybind.setToDefault();
			KeyBinding.resetKeyBindingArrayAndHash();
			this.buttonReset.enabled = !selectedKeybind.isSetToDefaultValue();
			return;
		}
		
		if (button == this.buttonClear) {
			this.selectedKeybind.setKeyModifierAndCode(KeyModifier.NONE, 0);
			KeyBinding.resetKeyBindingArrayAndHash();
			this.buttonClear.enabled = this.selectedKeybind.getKeyCode() != 0;
		}
		
		if (button == this.buttonDone) {
			if (this.parentScreen != null)
				this.mc.displayGuiScreen(this.parentScreen);
			else 
				this.mc.displayGuiScreen((GuiScreen)null);
		}

		if (button == this.buttonActiveModifier) {
			this.changeActiveModifier();
			return;
		}
		
		if (button == this.buttonPage) {
			this.page++;
			if (this.page > 2) {
				this.page = 1;
			}
		this.buttonReset.enabled = !selectedKeybind.isSetToDefaultValue();
			/*
			 *switch (this.page) {
			 *    case 1:
			 *    	this.currentPage = this.keyboard;
			 *    	break;
			 *    case 2:
			 *    	this.currentPage = this.numpad;
			 *    	break;
			 *    default:
			 *    	this.currentPage = this.keyboard;
			 *    	break;
			 *}
			 */
				
		}
	}

	@Override
	public void handleMouseInput() throws IOException {
		int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
	
		super.handleMouseInput();
		this.bindingList.handleMouseInput(mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
	    super.mouseClicked(x, y, button);
	    this.searchBar.mouseClicked(x, y, button);
	    if (button == 1 && x >= this.searchBar.x && x < this.searchBar.x + this.searchBar.width && y >= this.searchBar.y && y < this.searchBar.y + this.searchBar.height) {
	        this.searchBar.setText("");
	    }
	    this.categoryList.mouseClicked(this.mc, x, y, button);
	    this.keyboard.mouseClicked(mc, x, y, button);
	}

	@Override
	protected void keyTyped(char c, int keyCode) throws IOException {
	    super.keyTyped(c, keyCode);
	    this.searchBar.textboxKeyTyped(c, keyCode);
	}

	/** Change the active modifier */
	private void changeActiveModifier() {

		if (this.activeModifier == KeyModifier.NONE) {
			this.activeModifier = KeyModifier.ALT;
		} else if (this.activeModifier == KeyModifier.ALT) {
			this.activeModifier = KeyModifier.CONTROL;
		} else if (this.activeModifier == KeyModifier.CONTROL) {
			this.activeModifier = KeyModifier.SHIFT;
		} else {
			this.activeModifier = KeyModifier.NONE;
		}

		this.buttonActiveModifier.displayString = I18n.format("gui.activeModifier" )+ ": " + activeModifier.toString();
	}
	
    public Minecraft getClient() {
		return this.mc;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
	
	public String getSearchText() {
		return Pattern.quote(this.searchText);
	}
	
	public String getSelectedCategory() {
		return this.selectedCategory;
	}
	
	public KeyModifier getActiveModifier() {
		return this.activeModifier;
	}
	
	public KeyBinding getSelectedKeybind() {
		return this.selectedKeybind;
	}

	protected void setSelectedKeybind(KeyBinding binding){
    	this.selectedKeybind = binding;
    }
	
	public boolean getCategoryListExtended() {
		return this.categoryList.getExtended();
	}
}
