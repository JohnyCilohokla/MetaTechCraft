package com.metatechcraft.multientity.entites;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import com.forgetutorials.lib.network.MultiEntitySystem;
import com.forgetutorials.lib.renderers.BlockTessallator;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.metatechcraft.models.ModelFrameBox;
import com.metatechcraft.tileentity.InventoryWithSide;

public abstract class InventoryLinkTileEntity extends InfernosProxyEntityBase {
	
	static final int[] nullIntArray = {};
	
	public InventoryLinkTileEntity(InfernosMultiEntity entity) {
		super(entity);
	}

	@Override
	public boolean hasInventory() {
		return true;
	}

	@Override
	public boolean hasLiquids() {
		return false;
	}
	private ModelFrameBox frameBox = new ModelFrameBox();

	GLDisplayList frameBoxList = new GLDisplayList();

	@Override
	public void renderItem(ItemRenderType type) {
		if (icons==null){
			registerIcons();
		}
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		if (!this.frameBoxList.isGenerated()) {
			if (Tessellator.instance.isDrawing){
				int drawMode = Tessellator.instance.drawMode;
				Tessellator.instance.draw();
				//--------------------------
				this.frameBoxList.generate();
				this.frameBoxList.bind();

				GL11.glTranslated(0, -0.1, 0);
				GL11.glScaled(0.9, 0.9, 0.9);
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
				
				Tessellator.instance.startDrawingQuads();
				BlockTessallator.addToTessallator(Tessellator.instance, 0, 0, 0, icons[0][0], icons[0][1], icons[0][2], icons[0][3], icons[0][4], icons[0][5]);
				Tessellator.instance.draw();
				
				this.frameBoxList.unbind();
				//--------------------------
				Tessellator.instance.startDrawing(drawMode);
			}else{
				this.frameBoxList.generate();
				this.frameBoxList.bind();

				GL11.glTranslated(0, -0.1, 0);
				GL11.glScaled(0.9, 0.9, 0.9);
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
				
				Tessellator.instance.startDrawingQuads();
				BlockTessallator.addToTessallator(Tessellator.instance, 0, 0, 0, icons[0][0], icons[0][1], icons[0][2], icons[0][3], icons[0][4], icons[0][5]);
				Tessellator.instance.draw();
				
				this.frameBoxList.unbind();
			}
		}
		this.frameBoxList.render();
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(double x, double y, double z) {
		
	}

	
	protected Icon icons[][];
	
	public void registerIcons(){
		Icon icon = ((TextureMap) Minecraft.getMinecraft().renderEngine.getTexture(TextureMap.locationBlocksTexture)).registerIcon("missingno");
		this.icons = new Icon[][] { { icon, icon, icon, icon, icon, icon },
				{ icon, icon, icon, icon, icon, icon },
				{ icon, icon, icon, icon, icon, icon },
				{ icon, icon, icon, icon, icon, icon },
				{ icon, icon, icon, icon, icon, icon },
				{ icon, icon, icon, icon, icon, icon } };
	}
	
	@Override
	public Icon getIconFromSide(int side){
		if (icons==null){
			registerIcons();
		}
		return icons[this.entity.getSide()>0?this.entity.getSide():0][side];
	}
	
	
	@Override
	public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z) {
		renderer.renderStandardBlock(MultiEntitySystem.infernosMultiBlock, x, y, z);
	}
	
	
	
	
	/**
	 * Gets an inventory at the given location to extract items into or take
	 * items from. Can find either a tile entity or regular entity implementing
	 * IInventory.
	 */
	public static IInventory getLinkedInventory(World world, int x, int y, int z) {
		IInventory iinventory = null;
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);

		if ((tileentity != null) && (tileentity instanceof IInventory)) {
			iinventory = (IInventory) tileentity;

			if (iinventory instanceof TileEntityChest) {
				int l = world.getBlockId(x, y, z);
				Block block = Block.blocksList[l];

				if (block instanceof BlockChest) {
					iinventory = ((BlockChest) block).getInventory(world, x, y, z);
				}
			}
		}

		if (iinventory == null) {
			List<?> list = world.getEntitiesWithinAABBExcludingEntity((Entity) null,
					AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectInventories);

			if ((list != null) && (list.size() > 0)) {
				iinventory = (IInventory) list.get(world.rand.nextInt(list.size()));
			}
		}

		return iinventory;
	}
	
	public abstract boolean isSneaky();

	public abstract int getMaxPass();

	private InventoryWithSide getLinkedInventory() {
		int direction = this.entity.getSide();
		if (direction<0){
			return InventoryWithSide.NULL;
		}
		IInventory inventory = InventoryLinkTileEntity.getLinkedInventory(this.entity.getWorldObj(), (this.entity.xCoord + Facing.offsetsXForSide[direction]),
				(this.entity.yCoord + Facing.offsetsYForSide[direction]), (this.entity.zCoord + Facing.offsetsZForSide[direction]));
		// int linkPass = 0;
		int leftPass = getMaxPass();
		while (inventory instanceof InfernosMultiEntity && ((InfernosMultiEntity)inventory).getProxyEntity() instanceof InventoryLinkTileEntity ) {
			InventoryLinkTileEntity linkInventory = (InventoryLinkTileEntity) ((InfernosMultiEntity)inventory).getProxyEntity();
			direction = linkInventory.entity.getSide();
			if (direction<0){
				return InventoryWithSide.NULL;
			}
			inventory = InventoryLinkTileEntity.getLinkedInventory(linkInventory.entity.getWorldObj(), (linkInventory.entity.xCoord + Facing.offsetsXForSide[direction]),
					(linkInventory.entity.yCoord + Facing.offsetsYForSide[direction]), (linkInventory.entity.zCoord + Facing.offsetsZForSide[direction]));
			if ((inventory == null) || inventory.equals(this)) {
				return InventoryWithSide.NULL;
			}
			// linkPass++;
			leftPass = (((leftPass) <= linkInventory.getMaxPass()) ? leftPass : linkInventory.getMaxPass());
			leftPass--;
			if (leftPass <= 0) {
				return InventoryWithSide.NULL;
			}
		}
		return new InventoryWithSide(inventory, Facing.oppositeSide[direction]);
	}
	
	
	@Override
	public void closeChest() {
		IInventory inventory = getLinkedInventory().getInventory();
		if (inventory != null) {
			inventory.closeChest();
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.decrStackSize(slot, count) : null;
	}

	@Override
	public int getInventoryStackLimit() {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.getInventoryStackLimit() : 0;
	}

	@Override
	public String getInvName() {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.getInvName() : null;
	}

	@Override
	public int getSizeInventory() {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.getSizeInventory() : 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.getStackInSlot(slot) : null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.getStackInSlotOnClosing(slot) : null;
	}

	@Override
	public boolean isInvNameLocalized() {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.isInvNameLocalized() : false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.isUseableByPlayer(entityplayer) : false;
	}

	@Override
	public void openChest() {
		IInventory inventory = getLinkedInventory().getInventory();
		if (inventory != null) {
			inventory.openChest();
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		IInventory inventory = getLinkedInventory().getInventory();
		if (inventory != null) {
			inventory.setInventorySlotContents(slot, itemstack);
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		IInventory inventory = getLinkedInventory().getInventory();
		return inventory != null ? inventory.isItemValidForSlot(slot, itemstack) : false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		InventoryWithSide inv = getLinkedInventory();
		ISidedInventory sidedInventory = inv.getSidedInventory();
		if (sidedInventory != null) {
			if (!isSneaky()) {
				side = inv.getSide();
			}
			return sidedInventory.getAccessibleSlotsFromSide(side);
		}

		IInventory inventory = inv.getInventory();
		if (inventory != null) {
			int size = inventory.getSizeInventory();
			int[] slots = new int[size];
			for (int i = 0; i < size; i++) {
				slots[i] = i;
			}
			return slots;
		}
		return InventoryLinkTileEntity.nullIntArray;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		InventoryWithSide inv = getLinkedInventory();

		ISidedInventory sidedInventory = inv.getSidedInventory();
		if (sidedInventory != null) {
			if (!isSneaky()) {
				side = inv.getSide();
			}
			return sidedInventory.canInsertItem(slot, itemstack, side);
		}

		IInventory inventory = inv.getInventory();
		if (inventory != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		InventoryWithSide inv = getLinkedInventory();

		ISidedInventory sidedInventory = inv.getSidedInventory();
		if (sidedInventory != null) {
			if (!isSneaky()) {
				side = inv.getSide();
			}
			return sidedInventory.canExtractItem(slot, itemstack, side);
		}

		IInventory inventory = inv.getInventory();
		if (inventory != null) {
			return true;
		}
		return false;
	}
}
