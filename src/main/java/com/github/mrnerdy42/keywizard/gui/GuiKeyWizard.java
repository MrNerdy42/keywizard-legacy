package com.github.mrnerdy42.keywizard.gui;

import static org.lwjgl.input.Keyboard.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.github.mrnerdy42.keywizard.util.KeyHelper;
import com.github.mrnerdy42.keywizard.util.KeybindUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GuiKeyWizard extends GuiScreen {
	
	

	// An alternative to the mc field of GuiScreen because it was throwing a
	// null pointer exception
	//protected Minecraft client = FMLClientHandler.instance().getClient();
	//protected KeyBinding[] allBindings = KeybindUtils.ALL_BINDINGS;
	
	private final GameSettings options;

	// These hash maps map LWJGL key ids to buttons in the gui. Use these to
	// access keys instead of buttonList
	private HashMap<Integer, GuiButton> keyboard = new HashMap();
	private HashMap<Integer, GuiButton> numpad = new HashMap();
	private HashMap<Integer, GuiButton> currentPage = keyboard;
	
	private int page = 1;
	private KeyBinding selectedKeybind;
	private KeyModifier activeModifier = KeyModifier.NONE;
	private String selectedCategory = "categories.all";
	private String searchText = "";
	//private String keyboardMode = "keyboard";


	private GuiCategorySelector categoryList;
	private GuiTextField searchBar;
	private GuiBindingList bindingList;
	private GuiButton pageButton;
	private GuiButton resetButton;
	private GuiButton clearButton;
	private GuiButton activeModifierButton;
	
	public GuiKeyWizard(Minecraft mc, GameSettings settings) {
		this.options = settings;
		this.mc = mc;
	}

	/**
	 * This variable is incremented every time a key is added to the keyboard.
	 * This is to allows the ids in the buttonList to be sequential.
	 */
	private int currentID = 2;

	@Override
	protected void actionPerformed(GuiButton button) {

		if (button == this.resetButton) {
			this.selectedKeybind.setToDefault();
			KeyBinding.resetKeyBindingArrayAndHash();
			this.resetButton.enabled = !selectedKeybind.isSetToDefaultValue();
			return;
		}
		
		if (button == this.clearButton) {
			this.selectedKeybind.setKeyModifierAndCode(KeyModifier.NONE, 0);
			KeyBinding.resetKeyBindingArrayAndHash();
			this.clearButton.enabled = this.selectedKeybind.getKeyCode() != 0;
		}

		if (button == this.activeModifierButton) {
			this.changeActiveModifier();
			return;
		}
		
		if (button == this.pageButton) {
			this.page++;
			if (this.page > 2) {
				this.page = 1;
			}
			
			switch (this.page) {
			    case 1:
			    	this.currentPage = this.keyboard;
			    	break;
			    case 2:
			    	this.currentPage = this.numpad;
			    	break;
			    default:
			    	this.currentPage = this.keyboard;
			    	break;
			}
				
		}
		
		//if (!this.categoryList.getExtended() ){
		if ( this.currentPage.containsValue(button) && !this.categoryList.getExtended() ){
		
			int newKeyId = 0;

			for (int keyId : currentPage.keySet()) {
				if (currentPage.containsKey(keyId) && currentPage.get(keyId) == button)
					newKeyId = keyId;
			}

			if (newKeyId != 0) {
				this.selectedKeybind.setKeyModifierAndCode(this.activeModifier, newKeyId);
				this.options.setOptionKeyBinding(this.selectedKeybind, newKeyId);
				KeyBinding.resetKeyBindingArrayAndHash();
			}
			this.resetButton.enabled = !selectedKeybind.isSetToDefaultValue();
			System.out.println(this.currentPage.containsValue(button));
			return;
		}
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

		this.activeModifierButton.displayString = "Active Modifier: " + activeModifier.toString();
	}
	
    @Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.bindingList.drawScreen(mouseX, mouseY, partialTicks);
		this.searchBar.drawTextBox();

		this.categoryList.drawList(this.mc, mouseX, mouseY, partialTicks);

		// Color key and draw hovering text
		currentPage.forEach((keyId, keyButton) -> {

			ArrayList<String> bindingNames = KeybindUtils.getBindingNames(keyId, this.activeModifier);

			switch (bindingNames.size()) {
			case 0:
				keyButton.displayString = KeyHelper.translateKey(keyId);
				break;
			case 1:
				keyButton.displayString = TextFormatting.GREEN + KeyHelper.translateKey(keyId);
				break;
			default:
				keyButton.displayString = TextFormatting.RED + KeyHelper.translateKey(keyId);
				break;
			}

			if (keyButton.isMouseOver() && !this.categoryList.getExtended()) {
				drawHoveringText(KeybindUtils.getBindingNames(keyId, this.activeModifier), mouseX, mouseY,
						fontRenderer);
			}
		});
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

	@Override
	public void handleMouseInput() throws IOException {
		int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		super.handleMouseInput();
		this.bindingList.handleMouseInput(mouseX, mouseY);
	}

	@Override
	public void initGui() {
		
 		this.options.guiScale = 3;
		
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

		int startX = listWidth + 50;
		int startY = this.height / 2 - 80;
    
		
		ArrayList<String> categories = KeybindUtils.getCategories();
		categories.add(0, "categories.conflicts");
		categories.add(0, "categories.unbound");
		categories.add(0, "categories.all");


		this.categoryList = new GuiCategorySelector(startX - 30, 5, 125, "Binding Categories", categories);
		this.selectedCategory = this.categoryList.getSelctedCategory();
		this.pageButton = new GuiButton(0, startX + 105, 5, 100, 20, "Page: " + String.format("%d", page) );

		this.resetButton = new GuiButton(0, startX - 30, this.height - 40, 100, 20, "Reset binding");
		this.clearButton = new GuiButton(0, startX + 75, this.height - 40, 100, 20, "Clear binding");
		this.activeModifierButton = new GuiButton(1, startX - 30, this.height - 65, 150, 20,
				"Active Modifier: " + activeModifier.toString());
		
		this.setSelectedKeybind(this.bindingList.getSelectedKeybind());
		
        this.buttonList.add(this.pageButton);
		this.buttonList.add(this.activeModifierButton);
		this.buttonList.add(this.resetButton);
		this.buttonList.add(this.clearButton);

		int rowPos = 0;
		GuiButton button;
		
		for (int i = KEY_F1; i < KEY_F10 + 1; i++) {
			this.placeKey(i, (startX + rowPos * 36) - 30, startY, 34, keyboard);
			rowPos++;
		}
		
		for (int i = KEY_F11; i < KEY_F12 + 1; i++) {
			this.placeKey(i, (startX + rowPos * 36) - 30, startY, 34, keyboard);
			rowPos++;
		}
		
		rowPos = 0;
		for (int i = KEY_1; i < KEY_EQUALS + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30), startY + 25, 25, keyboard);
			rowPos++;
		}
		
		rowPos = 0;
		for (int i = KEY_Q; i < KEY_RBRACKET + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30) + 15, startY + 50, 25, keyboard);
			rowPos++;
		}
		
		rowPos = 0;
		for (int i = KEY_A; i < KEY_APOSTROPHE + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30) + 20, startY + 75, 25, keyboard);
			rowPos++;
		}
		
		rowPos = 0;
		for (int i = KEY_Z; i < KEY_SLASH + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30) + 25, startY + 100, 25, keyboard);
			rowPos++;
		}

		this.placeAuxKey(KEY_GRAVE, KEY_1, -30, 0, 25, keyboard);
		this.placeAuxKey(KEY_BACK, KEY_EQUALS, 30, 0, 40, keyboard);

		this.placeAuxKey(KEY_TAB, KEY_Q, -45, 0, 40, keyboard);
		this.placeAuxKey(KEY_BACKSLASH, KEY_RBRACKET, 30, 0, 25, keyboard);

		this.placeAuxKey(KEY_CAPITAL, KEY_A, -50, 0, 45, keyboard);
		this.placeAuxKey(KEY_RETURN, KEY_APOSTROPHE, 30, 0, 50, keyboard);

		this.placeAuxKey(KEY_LSHIFT, KEY_Z, -55, 0, 50, keyboard);
		this.placeAuxKey(KEY_RSHIFT, KEY_SLASH, 30, 0, 75, keyboard);

		this.placeAuxKey(KEY_LCONTROL, KEY_LSHIFT, 0, 25, 35, keyboard);
		this.placeAuxKey(KEY_LMETA, KEY_LCONTROL, 40, 0, 35, keyboard);
		this.placeAuxKey(KEY_LMENU, KEY_LMETA, 40, 0, 35, keyboard);

		this.placeAuxKey(KEY_SPACE, KEY_LMENU, 40, 0, 185, keyboard);

		this.placeAuxKey(KEY_RMENU, KEY_SPACE, 195, 0, 35, keyboard);
		this.placeAuxKey(KEY_RMETA, KEY_RMENU, 40, 0, 35, keyboard);
		this.placeAuxKey(KEY_RCONTROL, KEY_RMETA, 40, 0, 35, keyboard);
		
		// Draw the numpad page
		this.placeKey(KEY_NUMPAD7, startX, startY, 50, numpad);
		this.placeAuxKey(KEY_NUMPAD8, KEY_NUMPAD7, 55, 0, 50, numpad);
		this.placeAuxKey(KEY_NUMPAD9, KEY_NUMPAD8, 55, 0, 50, numpad);
		
		this.placeAuxKey(KEY_NUMPAD4, KEY_NUMPAD7, 0, 25, 50, numpad);
		this.placeAuxKey(KEY_NUMPAD5, KEY_NUMPAD4, 55, 0, 50, numpad);
		this.placeAuxKey(KEY_NUMPAD6, KEY_NUMPAD5, 55, 0, 50, numpad);
		
		this.placeAuxKey(KEY_NUMPAD1, KEY_NUMPAD4, 0, 25, 50, numpad);
		this.placeAuxKey(KEY_NUMPAD2, KEY_NUMPAD1, 55, 0, 50, numpad);
		this.placeAuxKey(KEY_NUMPAD3, KEY_NUMPAD2, 55, 0, 50, numpad);
		
		this.placeAuxKey(KEY_NUMPAD0, KEY_NUMPAD1, 0, 25, 100, numpad);
		this.placeAuxKey(KEY_DECIMAL, KEY_NUMPAD0, 105, 0, 55, numpad);
		
		this.placeAuxKey(KEY_DIVIDE, KEY_NUMPAD7, 0, -25, 50, numpad);
		this.placeAuxKey(KEY_MULTIPLY, KEY_NUMPAD8, 0, -25, 50, numpad);
		this.placeAuxKey(KEY_MINUS, KEY_NUMPAD9, 0, -25, 50, numpad);
		
		this.placeAuxKey(KEY_ADD, KEY_NUMPAD9, 55, 0, 70, numpad);
		this.placeAuxKey(KEY_NUMPADENTER, KEY_NUMPAD6, 55, 0, 70, numpad);
	}

	@Override
    protected void keyTyped(char c, int keyCode) throws IOException {
        super.keyTyped(c, keyCode);
        this.searchBar.textboxKeyTyped(c, keyCode);
    }
	
    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        this.searchBar.mouseClicked(x, y, button);
        if (button == 1 && x >= this.searchBar.x && x < this.searchBar.x + this.searchBar.width && y >= this.searchBar.y && y < this.searchBar.y + this.searchBar.height) {
            this.searchBar.setText("");
        }
        this.categoryList.mouseClicked(this.mc, x, y, button);
    }

	protected void setSelectedKeybind(KeyBinding binding){
    	this.selectedKeybind = binding;
    }
	
    /**
	 * Add a key to the keyboard using an existing key as an anchor
	 * 
	 * @param keyCode
	 *     the LWJGL code of the new key
	 * @param anchorCode
	 *     the LWJGL code of the key to anchor to
	 * @param xOffset
	 *     amount to offset the key on the x axis
	 * @param yOffset
	 *     amount to offset the key on the y axis
	 * @param width
	 *     the width of the new key
	 * @param page
	 *     the page to place the key on
	 */
	private void placeAuxKey(int keyCode, int anchorCode, int xOffset, int yOffset, int width, HashMap<Integer, GuiButton> page) {
		GuiButton anchor = page.get(anchorCode);
		GuiButton button = new GuiButton(this.currentID, anchor.x + xOffset, anchor.y + yOffset, width,
				20, KeyHelper.translateKey(keyCode));
		this.buttonList.add(button);
		page.put(keyCode, button);
		this.currentID++;
	}
    
    /**
	 * Add a key to the keyboard
	 * 
	 * @param keyCode
	 *     the LWJGL code of the new key
	 * @param x
	 *     x position of the button
	 * @param y
	 *     y position of the button
	 * @param width
	 *     the width of the new key
	 * @param page
	 *     the page to place the key on
	 */
	private void placeKey(int keyCode, int x, int y, int width, HashMap<Integer, GuiButton> page) {
		GuiButton button = new GuiButton(this.currentID, x, y, width, 20, KeyHelper.translateKey(keyCode));
		this.buttonList.add(button);
		page.put(keyCode, button);
		this.currentID++;
	}

	@Override
    public void updateScreen() {
        super.updateScreen();
        this.searchBar.updateCursorCounter();
        if ( this.resetButton != null )
        	this.resetButton.enabled = !this.selectedKeybind.isSetToDefaultValue();
		if ( this.clearButton != null ) {
			this.clearButton.enabled = (this.selectedKeybind.getKeyCode() == 0) ? false:true;
		}

        if ( this.categoryList != null )
        	this.selectedCategory = this.categoryList.getSelctedCategory();
        
        if ( !this.searchBar.getText().equals(this.searchText) ) {
        	this.searchText = this.searchBar.getText();
        }
        
        this.pageButton.displayString = "Page: " + String.format("%d", page);
        if ( this.page == 1 ) {
        	numpad.values().forEach(button -> {
        		button.visible = false;
        	});
        	keyboard.values().forEach(button -> {
        		button.visible = true;
        	});
        } else if ( this.page == 2 ) {
        	keyboard.values().forEach(button -> {
        		button.visible = false;
        	});
        	numpad.values().forEach(button -> {
        		button.visible = true;
        	});
        }
        this.bindingList.updateList();
    }

}
