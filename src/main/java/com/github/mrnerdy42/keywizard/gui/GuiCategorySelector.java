package com.github.mrnerdy42.keywizard.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
/**
 *An NEI-style dropdown list menu. Its functionality is 
 *hacked together using the vanilla GuiButton class
 */
public class GuiCategorySelector extends GuiButton{
	
	private ListItem[] items;
	
	private boolean extended = false;
	private ListItem selectedItem;

	private class ListItem extends GuiButton{
		
		private int index;
		private String name;
		private GuiCategorySelector parent;
		
		public ListItem(int index, int row, int column, String itemText, String name, GuiCategorySelector parent) throws IllegalArgumentException{
			super(index, parent.x + (parent.width + 2) * column, parent.y + 21 * (row + 1), parent.width, 20, itemText);
			
			this.index = index;
			this.name = name;
			this.parent = parent;
			
		}
		
		/**
		 * Performs an action and returns true if the button is clicked by the mouse
		 * @param mc
		 *     the current minecraft instance 
		 * @param mouseX
		 *     the x position of the mouse
		 * @param mouseY
		 *     the y position of the mouse
		 * @param button
		 *     the lwjgl id of the button pressed
		 * @return true if the mouse was clicked on the button
		 */
		
	    public boolean mouseClicked(Minecraft mc, int mouseX, int mouseY, int button) {
	    	
	    	boolean buttonHit = false;
	    	
	    	if ( this.parent.extended && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height && button == 0 ) {
	    		this.playPressSound(mc.getSoundHandler());
	    		this.parent.selectItem(this.index);
	    		buttonHit = true;
	    	}
	    	return buttonHit;
	    }		
	}
	
	public GuiCategorySelector(int x, int y, int width, String buttonText, ArrayList<String> itemNames) {
		super(0, x, y, width, 20, buttonText);
		
		this.items = new ListItem[itemNames.size()];

		int row = 0;
		int column = 0;
		for (int i = 0; i < items.length; i ++) {
			if  (i % 11 == 0 && i != 0) {
				column++;
				row = 0;
			}
			this.items[i] = new ListItem(i, row, column, I18n.format(itemNames.get(i)), itemNames.get(i), this);
			row++;
		}
		
		this.selectItem(0);
	}
	
	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getShadingMultiplier(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 0xE0E0E0;

            if (packedFGColour != 0){
                j = packedFGColour;
            }else if (!this.enabled){
                j = 0xA0A0A0;
            }
            else if (this.hovered || this.extended){
                j = 0xFFFFA0;
            }
            

            this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }
	
	public void drawList(Minecraft mc, int mouseX, int mouseY, float partialTicks){
		this.drawButton(mc, mouseX, mouseY, partialTicks);;
		
		for (ListItem item : this.items) {
			item.drawButton(mc, mouseX, mouseY, partialTicks);
		}
	}
	
	public boolean getExtended(){
    	return this.extended;
    }
	
	public String getSelctedCategory(){
    	return this.selectedItem.name;
    }
    
    private int getShadingMultiplier(boolean mouseOver){
    	if (this.extended) {
    		return 2;
    	} else {
    	    return this.getHoverState(mouseOver);
    	}
    }
    
    public void mouseClicked(Minecraft mc, int mouseX, int mouseY, int button) {
    	
		boolean buttonHit = false;
		
		for (ListItem item : this.items){
			buttonHit = item.mouseClicked(mc, mouseX, mouseY, button);
			if (buttonHit)
				break;
		}
    	
    	if (mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height && button == 0) {
            this.playPressSound(mc.getSoundHandler());
    		this.setList(!this.extended);
    	} else if (!buttonHit) {
    		this.setList(false);
    	}
    }
    
    public void selectItem(int index){
		this.selectedItem = items[index];
		this.updateState();
	}
    
    public void setList(boolean extended){
		this.extended = extended;
		this.updateState();
	}
	
	private void updateState(){
		
		this.hovered = this.extended;
		
		for (ListItem item : this.items){
			
			item.visible = this.extended;
			
			this.enabled = !(item == this.selectedItem);
		}
	}
}
