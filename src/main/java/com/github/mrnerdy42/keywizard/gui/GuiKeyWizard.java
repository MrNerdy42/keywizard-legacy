package com.github.mrnerdy42.keywizard.gui;

import static org.lwjgl.input.Keyboard.KEY_1;
import static org.lwjgl.input.Keyboard.KEY_A;
import static org.lwjgl.input.Keyboard.KEY_APOSTROPHE;
import static org.lwjgl.input.Keyboard.KEY_BACK;
import static org.lwjgl.input.Keyboard.KEY_BACKSLASH;
import static org.lwjgl.input.Keyboard.KEY_CAPITAL;
import static org.lwjgl.input.Keyboard.KEY_EQUALS;
import static org.lwjgl.input.Keyboard.KEY_F1;
import static org.lwjgl.input.Keyboard.KEY_F10;
import static org.lwjgl.input.Keyboard.KEY_F11;
import static org.lwjgl.input.Keyboard.KEY_F12;
import static org.lwjgl.input.Keyboard.KEY_GRAVE;
import static org.lwjgl.input.Keyboard.KEY_LMENU;
import static org.lwjgl.input.Keyboard.KEY_LMETA;
import static org.lwjgl.input.Keyboard.KEY_LSHIFT;
import static org.lwjgl.input.Keyboard.KEY_Q;
import static org.lwjgl.input.Keyboard.KEY_RBRACKET;
import static org.lwjgl.input.Keyboard.KEY_RCONTROL;
import static org.lwjgl.input.Keyboard.KEY_RETURN;
import static org.lwjgl.input.Keyboard.KEY_RMENU;
import static org.lwjgl.input.Keyboard.KEY_RSHIFT;
import static org.lwjgl.input.Keyboard.KEY_SLASH;
import static org.lwjgl.input.Keyboard.KEY_SPACE;
import static org.lwjgl.input.Keyboard.KEY_TAB;
import static org.lwjgl.input.Keyboard.KEY_Z;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Mouse;

import com.github.mrnerdy42.keywizard.util.KeyHelper;
import com.github.mrnerdy42.keywizard.util.KeybindUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GuiKeyWizard extends GuiScreen {
	
	

	// An alternative to the mc field of GuiScreen because it was throwing a
	// null pointer exception
	protected Minecraft client = FMLClientHandler.instance().getClient();
	protected KeyBinding[] allBindings = KeybindUtils.ALL_BINDINGS;

	// This hash map maps LWJGL key ids to buttons in the gui. Use this to
	// access keys instead of buttonList
	private HashMap<Integer, GuiButton> keyHash = new HashMap();
	private HashMap<Integer, GuiButton> numpadHash = new HashMap();
	private KeyBinding selectedKeybind;
	private KeyModifier activeModifier = KeyModifier.NONE;
	private String selectedCategory = "categories.all";
	private String searchText = "";
	private String keyboardMode = "keyboard";


	private GuiCategorySelector categoryList;
	private GuiTextField searchBar;
	private GuiBindingList bindingList;
	private GuiButton reset;
	private GuiButton activeModifierButton;
	private GuiButton numpadButton;

	/**
	 * This variable is incremented every time a key is added to the keyboard.
	 * This is to allows the ids in the buttonList to be sequential.
	 */
	private int currentID = 2;

	@Override
	protected void actionPerformed(GuiButton button) {

		if (button == this.reset) {
			this.selectedKeybind.setToDefault();
			KeyBinding.resetKeyBindingArrayAndHash();
			this.reset.enabled = !selectedKeybind.isSetToDefaultValue();
			return;
		}

		if (button == this.activeModifierButton) {
			this.changeActiveModifier();
			return;
		}

		if ( this.keyHash.containsValue(button) && !this.categoryList.getExtended() ){
			int newKeyId = 0;

			for (int keyId : keyHash.keySet()) {
				if (keyHash.containsKey(keyId) && keyHash.get(keyId) == button)
					newKeyId = keyId;
			}

			if (newKeyId != 0) {
				this.selectedKeybind.setKeyModifierAndCode(this.activeModifier, newKeyId);
				KeyBinding.resetKeyBindingArrayAndHash();
			}
			this.reset.enabled = !selectedKeybind.isSetToDefaultValue();
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

		this.categoryList.drawList(this.client, mouseX, mouseY);

		// Color key and draw hovering text
		keyHash.forEach((keyId, keyButton) -> {

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
						fontRendererObj);
			}
		});
	}

	public Minecraft getClient() {
		return this.client;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
	}
	
	public String getSearchText() {
		return this.searchText;
	}
	
	public String getSelectedCategory() {
		return this.selectedCategory;
	}

	@Override
	public void handleMouseInput() throws IOException {
		int mouseX = Mouse.getEventX() * this.width / this.client.displayWidth;
		int mouseY = this.height - Mouse.getEventY() * this.height / this.client.displayHeight - 1;

		super.handleMouseInput();
		this.bindingList.handleMouseInput(mouseX, mouseY);
	}

	@Override
	public void initGui() {

		int maxLength = 0;

		for (KeyBinding binding : KeybindUtils.ALL_BINDINGS) {
			if (binding.getDisplayName().length() > maxLength)
				maxLength = binding.getDisplayName().length();
		}

		int listWidth = (maxLength * 20) + 10;

		this.bindingList = new GuiBindingList(this, 10, this.height - 30, listWidth, this.height - 40,
				fontRendererObj.FONT_HEIGHT * 2 + 10);
		
		this.searchBar = new GuiTextField(0, this.fontRendererObj, 10, this.height - 20, listWidth, 14);
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

		this.reset = new GuiButton(0, startX - 30, this.height - 40, 125, 20, "Reset binding");
		this.activeModifierButton = new GuiButton(1, startX - 30, this.height - 65, 150, 20,
				"Active Modifier: " + activeModifier.toString());
		this.numpadButton = new GuiButton(2, startX + 100, this.height - 40, 125, 20, "NUMPAD");
		
		this.setSelectedKeybind(this.bindingList.getSelectedKeybind());

		this.buttonList.add(this.activeModifierButton);
		this.buttonList.add(this.reset);
		this.buttonList.add(this.numpadButton);

		int rowPos = 0;

		GuiButton button;
		
		for (int i = KEY_F1; i < KEY_F10 + 1; i++) {
			this.placeKey(i, (startX + rowPos * 36) - 30, startY, 34);
			rowPos++;
		}
		
		for (int i = KEY_F11; i < KEY_F12 + 1; i++) {
			this.placeKey(i, (startX + rowPos * 36) - 30, startY, 34);
			rowPos++;
		}
		
		rowPos = 0;

		for (int i = KEY_1; i < KEY_EQUALS + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30), startY + 25, 25);
			rowPos++;
		}
		rowPos = 0;

		for (int i = KEY_Q; i < KEY_RBRACKET + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30) + 15, startY + 50, 25);
			rowPos++;
		}
		rowPos = 0;

		for (int i = KEY_A; i < KEY_APOSTROPHE + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30) + 20, startY + 75, 25);
			rowPos++;
		}
		rowPos = 0;

		for (int i = KEY_Z; i < KEY_SLASH + 1; i++) {
			this.placeKey(i, (startX + rowPos * 30) + 25, startY + 100, 25);
			rowPos++;
		}

		this.placeAuxKey(KEY_GRAVE, KEY_1, -30, 0, 25);
		this.placeAuxKey(KEY_BACK, KEY_EQUALS, 30, 0, 40);

		this.placeAuxKey(KEY_TAB, KEY_Q, -45, 0, 40);
		this.placeAuxKey(KEY_BACKSLASH, KEY_RBRACKET, 30, 0, 25);

		this.placeAuxKey(KEY_CAPITAL, KEY_A, -50, 0, 45);
		this.placeAuxKey(KEY_RETURN, KEY_APOSTROPHE, 30, 0, 50);

		this.placeAuxKey(KEY_LSHIFT, KEY_Z, -55, 0, 50);
		this.placeAuxKey(KEY_RSHIFT, KEY_SLASH, 30, 0, 75);

		this.placeAuxKey(KEY_RCONTROL, KEY_LSHIFT, 0, 25, 35);
		this.placeAuxKey(KEY_LMETA, KEY_RCONTROL, 40, 0, 35);
		this.placeAuxKey(KEY_LMENU, KEY_LMETA, 40, 0, 35);

		this.placeAuxKey(KEY_SPACE, KEY_LMENU, 40, 0, 185);

		this.placeAuxKey(KEY_RMENU, KEY_SPACE, 195, 0, 35);
		this.placeAuxKey(KEY_LMETA, KEY_RMENU, 40, 0, 35);
		this.placeAuxKey(KEY_RCONTROL, KEY_LMETA, 40, 0, 35);

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
        if (button == 1 && x >= this.searchBar.xPosition && x < this.searchBar.xPosition + this.searchBar.width && y >= this.searchBar.yPosition && y < this.searchBar.yPosition + this.searchBar.height) {
            this.searchBar.setText("");
        }
        this.categoryList.mouseClicked(this.client, x, y, button);
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
	 */
	private void placeAuxKey(int keyCode, int anchorCode, int xOffset, int yOffset, int width) {
		GuiButton anchor = keyHash.get(anchorCode);
		GuiButton button = new GuiButton(this.currentID, anchor.xPosition + xOffset, anchor.yPosition + yOffset, width,
				20, KeyHelper.translateKey(keyCode));
		this.buttonList.add(button);
		this.keyHash.put(keyCode, button);
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
	 */
	private void placeKey(int keyCode, int x, int y, int width) {
		GuiButton button = new GuiButton(this.currentID, x, y, width, 20, KeyHelper.translateKey(keyCode));
		this.buttonList.add(button);
		this.keyHash.put(keyCode, button);
		this.currentID++;
	}

	@Override
    public void updateScreen() {
        super.updateScreen();
        this.searchBar.updateCursorCounter();
        if ( this.reset != null )
        	this.reset.enabled = !this.selectedKeybind.isSetToDefaultValue();
        if ( this.categoryList != null )
        	this.selectedCategory = this.categoryList.getSelctedCategory();
        
        if ( !this.searchBar.getText().equals(this.searchText) ) {
        	this.searchText = this.searchBar.getText();
        }
        this.bindingList.updateList();
        
        if (this.keyboardMode.equals("keyboard")) {
        	keyHash.values().forEach(button -> {
        		button.visible = true;
        	});
        	numpadHash.values().forEach(button -> {
        		button.visible = false;
        	});
        } else if (this.keyboardMode.equals("numpad")) {
        	keyHash.values().forEach(button -> {
        		button.visible = false;
        	});
        	numpadHash.values().forEach(button -> {
        		button.visible = true;
        	});
        }
    }

}
