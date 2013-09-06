package com.metatechcraft.multientity;

import java.util.ArrayList;

import com.metatechcraft.block.MetaBlocks;
import com.metatechcraft.item.MetaItems;
import com.metatechcraft.multientity.base.InfernosProxyEntityBase;
import com.metatechcraft.multientity.entites.InfuserTopTileEntity;
import com.metatechcraft.network.PacketMultiTileEntity;
import com.metatechcraft.network.PacketType;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class InfernosMultiEntity extends TileEntity {
	private InfernosProxyEntityBase proxyEntity;

	public InfernosMultiEntity() {
		super();
	}

	public InfernosProxyEntityBase getProxyEntity() {
		return (this.proxyEntity != null) ? (this.proxyEntity) : (InfernosProxyEntityBase.DUMMY);
	}

	private void setProxyEntity(InfernosProxyEntityBase proxyEntity) {
		this.proxyEntity = proxyEntity;
	}

	public void newEntity() {
		// TODO switch!
		setProxyEntity(new InfuserTopTileEntity(this));
	}

	public void newEntity(String entityName) {
		// TODO switch!
		if (this.proxyEntity == null) {
			setProxyEntity(new InfuserTopTileEntity(this));
		}
	}

	public float getBlockHardness() {
		return getProxyEntity().getBlockHardness();
	}

	public boolean canSilkHarvest(EntityPlayer player) {
		return false;
	}

	public void harvestBlock(EntityPlayer player) {
		// TODO abstractify
		player.addExhaustion(0.025F);
		dropBlockAsItems();
		/*if (canSilkHarvest(player) && (player.getCurrentEquippedItem().getItem() == MetaItems.strangeChisel)) {
			ItemStack itemstack = getSilkTouchItemStack();
		
			if (itemstack != null) {
				dropBlockAsItem_do(itemstack);
			}
		} else {
			int fortune = EnchantmentHelper.getFortuneModifier(player);
			dropBlockAsItem(fortune);
		}*/

	}

	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	public void dropBlockAsItemWithChance(int fortune) {
		ArrayList<ItemStack> items = getBlockDropped(fortune);

		for (ItemStack item : items) {
			if (this.worldObj.rand.nextFloat() <= fortune) {
				dropItemStack(item);
			}
		}
	}

	public void dropBlockAsItems() {
		ArrayList<ItemStack> items = getBlockDropped(0);

		for (ItemStack item : items) {
			dropItemStack(item);
		}
	}

	protected void dropItemStack(ItemStack itemstack) {
		if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double d0 = (this.worldObj.rand.nextFloat() * f) + ((1.0F - f) * 0.5D);
			double d1 = (this.worldObj.rand.nextFloat() * f) + ((1.0F - f) * 0.5D);
			double d2 = (this.worldObj.rand.nextFloat() * f) + ((1.0F - f) * 0.5D);
			EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + d0, this.yCoord + d1, this.zCoord + d2, itemstack);
			entityitem.delayBeforeCanPickup = 10;
			this.worldObj.spawnEntityInWorld(entityitem);
		}

	}

	protected ItemStack getSilkTouchItemStack() {
		// TODO abstractify
		return new ItemStack(MetaBlocks.infernosMultiBlock, 1, 0);
	}

	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		// TODO abstractify
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(new ItemStack(MetaItems.metaDust, 1, 0));
		return droppedItems;
	}

	public void renderTileEntityAt(double x, double y, double z) {
		getProxyEntity().renderTileEntityAt(x, y, z);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		// TODO switch!
		System.out.println("Reading entity data! " + tagCompound.getString("MES"));
		setProxyEntity(new InfuserTopTileEntity(this));
		getProxyEntity().readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		System.out.println("Saving entity data!");
		if (getProxyEntity() != null) {
			tagCompound.setString("MES", getProxyEntity().getTypeName());
			getProxyEntity().writeToNBT(tagCompound);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		PacketMultiTileEntity packet = new PacketMultiTileEntity(this.xCoord, this.yCoord, this.zCoord, this.proxyEntity.getTypeName());
		getProxyEntity().addToDescriptionPacket(packet);
		return PacketType.populatePacket(packet);
	}

	@Override
	public void invalidate() {
		if (getProxyEntity() != null) {
			getProxyEntity().invalidate();
			setProxyEntity(null);
		}
	}
}