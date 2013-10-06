package com.metatechcraft.models;

import org.lwjgl.opengl.GL11;

import com.metatechcraft.lib.ModInfo;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public enum ModelFrameBox {
	
	frameBox("blocks/boxFrame.obj"),
	squareFrame("blocks/squareFrame.obj");

	private IModelCustom modelFrameBox;

	public final String MODEL_LOCATION;
	public static final ResourceLocation boxFrameTexture = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "models/blocks/" + "boxFrame.png");

	ModelFrameBox(String location) {
		this.MODEL_LOCATION = "/assets/metatechcraft/models/" + location;
	}

	public void render() {
		render(ModelFrameBox.boxFrameTexture, null);
	}
	public void render(ResourceLocation texture, EnumFacing facing) {
		if (modelFrameBox==null){
			this.modelFrameBox = AdvancedModelLoader.loadModel(this.MODEL_LOCATION);
		}
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.0, 0.5);
		if (facing!=null){
			GL11.glTranslated(0.0, 0.5, 0.0);
			switch(facing){
			case DOWN:
				break;
			case UP:
				GL11.glRotated(180, 1, 0, 0);// up-side down
				break;
			case NORTH:
				GL11.glRotated(90, 1, 0, 0);// north
				break;
			case SOUTH:
				GL11.glRotated(90, -1, 0, 0);// south
				break;
			case EAST:
				GL11.glRotated(90, 0, 0, -1);// east
				break;
			case WEST:
				GL11.glRotated(90, 0, 0, 1);// west
				break;
			default:
				break;
			
			}
			GL11.glTranslated(0.0, -0.5, 0.0);
		}
		GL11.glColor4f(1, 1, 1, 1);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		this.modelFrameBox.renderAll();
		GL11.glPopMatrix();
	}
}