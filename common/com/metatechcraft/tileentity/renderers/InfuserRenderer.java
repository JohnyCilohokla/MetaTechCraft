package com.metatechcraft.tileentity.renderers;

import org.lwjgl.opengl.GL11;

import com.metatechcraft.models.ModelFrameBox;
import com.metatechcraft.tileentity.InfuserTopTileEntity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class InfuserRenderer extends TileEntitySpecialRenderer implements IItemRenderer {

	private ModelFrameBox frameBox = new ModelFrameBox();
	private final RenderItem customRenderItem;

	public InfuserRenderer() {
		this.customRenderItem = new RenderItem() {

			@Override
			public boolean shouldBob() {

				return false;
			};
		};
		this.customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	public static Icon getFluidTexture(FluidStack fluidStack, boolean flowing) {
		if (fluidStack == null) {
			return null;
		}
		return InfuserRenderer.getFluidTexture(fluidStack.getFluid(), flowing);
	}

	public static Icon getFluidTexture(Fluid fluid, boolean flowing) {
		if (fluid == null) {
			return null;
		}
		Icon icon = flowing ? fluid.getFlowingIcon() : fluid.getStillIcon();
		if (icon == null) {
			icon = ((TextureMap) Minecraft.getMinecraft().func_110434_K().func_110581_b(TextureMap.field_110575_b)).func_110572_b("missingno");
		}
		return icon;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslatef(0, 1, 0);
		// GL11.glScalef(scale, scale, scale);
		// GL11.glRotatef(180f, 0f, 1f, 0f);

		// Block.beacon
		MinecraftForgeClient.getItemRenderer(new ItemStack(Block.blockDiamond), ItemRenderType.ENTITY);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public static void setColorForFluidStack(FluidStack fluidstack) {
		if (fluidstack == null) {
			return;
		}

		int color = fluidstack.getFluid().getColor(fluidstack);
		float red = ((color >> 16) & 255) / 255.0F;
		float green = ((color >> 8) & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		GL11.glColor4f(red, green, blue, 0.6f);
	}

	public void renderFluid(Tessellator tessellator, FluidStack fluidstack) {
		if ((fluidstack == null) || (fluidstack.amount <= 0)) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		func_110628_a(TextureMap.field_110575_b);
		InfuserRenderer.setColorForFluidStack(fluidstack);
		Icon icon = InfuserRenderer.getFluidTexture(fluidstack, false);

		double size = fluidstack.amount * 0.001;
		double amount = fluidstack.amount * 0.0009;

		tessellator.startDrawingQuads();
		// top
		tessellator.addVertexWithUV(0.1, 0.05 + amount, 0.1, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(0.1, 0.05 + amount, 0.9, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(0.9, 0.05 + amount, 0.9, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(0.9, 0.05 + amount, 0.1, icon.getMaxU(), icon.getMinV());

		// bottom
		tessellator.addVertexWithUV(0.1, 0.05, 0.9, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(0.1, 0.05, 0.1, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(0.9, 0.05, 0.1, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(0.9, 0.05, 0.9, icon.getMaxU(), icon.getMinV());

		// sides
		tessellator.addVertexWithUV(0.1, 0.05, 0.1, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(0.1, 0.05 + amount, 0.1, icon.getMinU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.9, 0.05 + amount, 0.1, icon.getMaxU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.9, 0.05, 0.1, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(0.9, 0.05, 0.9, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(0.9, 0.05 + amount, 0.9, icon.getMinU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.1, 0.05 + amount, 0.9, icon.getMaxU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.1, 0.05, 0.9, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(0.9, 0.05, 0.1, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(0.9, 0.05 + amount, 0.1, icon.getMinU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.9, 0.05 + amount, 0.9, icon.getMaxU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.9, 0.05, 0.9, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(0.1, 0.05, 0.9, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(0.1, 0.05 + amount, 0.9, icon.getMinU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.1, 0.05 + amount, 0.1, icon.getMaxU(), icon.getMaxV() * size);
		tessellator.addVertexWithUV(0.1, 0.05, 0.1, icon.getMaxU(), icon.getMinV());
		tessellator.draw();

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		Tessellator tessellator = Tessellator.instance;

		if (tileentity instanceof InfuserTopTileEntity) {
			InfuserTopTileEntity tile = (InfuserTopTileEntity) tileentity;

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslated(x, y, z);

			this.frameBox.render();

			// Block.beacon
			GL11.glPushMatrix();
			GL11.glTranslated(0.5, 0.5, 0.5);// center
			// GL11.glScalef(scale, scale, scale);
			float rotationAngle = tile.getRotation();
			GL11.glRotatef(rotationAngle, 0f, 1f, 0f);
			ItemStack ghostStack = tile.getItemStack();
			if (ghostStack != null) {
				EntityItem ghostEntityItem = new EntityItem(tileentity.worldObj);
				ghostEntityItem.hoverStart = 0.0F;
				ghostEntityItem.setEntityItemStack(ghostStack);

				this.customRenderItem.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
			}
			GL11.glPopMatrix();

			FluidStack liquid = tile.getFluid(0);
			renderFluid(tessellator, liquid);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

}
