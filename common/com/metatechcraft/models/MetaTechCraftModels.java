package com.metatechcraft.models;

import org.lwjgl.opengl.GL11;

import com.forgetutorials.lib.renderers.FTAOpenGL;
import com.metatechcraft.lib.ModInfo;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public enum MetaTechCraftModels {

	frameBox("blocks/boxFrame.obj"), squareFrame("blocks/squareFrame.obj");

	private IModelCustom modelFrameBox;

	public final ResourceLocation MODEL_LOCATION;
	public static final ResourceLocation boxFrameTexture = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "models/blocks/" + "strangeTexture.png");

	MetaTechCraftModels(String location) {
		this.MODEL_LOCATION = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "models/" + location);
	}

	public void render(ResourceLocation texture, EnumFacing facing) {
		if (this.modelFrameBox == null) {
			this.modelFrameBox = AdvancedModelLoader.loadModel(this.MODEL_LOCATION);
		}
		GL11.glPushMatrix();
		FTAOpenGL.glRotate(facing);
		GL11.glColor4f(1, 1, 1, 1);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		this.modelFrameBox.renderAll();
		GL11.glPopMatrix();
	}

	public void render() {
		render(MetaTechCraftModels.boxFrameTexture, null);
	}

	public void render(ResourceLocation texture, int facing) {
		this.render(texture, EnumFacing.getFront(facing));
	}
}