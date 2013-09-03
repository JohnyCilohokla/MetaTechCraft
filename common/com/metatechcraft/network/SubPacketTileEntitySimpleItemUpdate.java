package com.metatechcraft.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;

/**
* MetaTech Craft
*
* As the packet system was based on Pahimar's EE3 packet system it is licensed by LGPL v3
* I have modified it greatly splitting the Tile Entity packet into
* Main packet (x,y,z)
* Simple Item Update subpacket (pos, item)
* Fluid Update subpacket (pos, fluidTag)
* more to come
* @author johnycilohokla
* @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
*
*/

public class SubPacketTileEntitySimpleItemUpdate extends SubPacketTileEntityChild {

	public int position;
	public ItemStack item;

	public SubPacketTileEntitySimpleItemUpdate() {
		super(SubPacketTileEntityType.ITEM_UPDATE);
	}

	public SubPacketTileEntitySimpleItemUpdate(int position, ItemStack item) {
		super(SubPacketTileEntityType.ITEM_UPDATE);
		this.position = position;
		this.item = item;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(this.position);
		if (this.item == null) {
			data.writeInt(-1);
			data.writeInt(-1);
			data.writeInt(-1);
		} else {
			data.writeInt(this.item.itemID);
			data.writeInt(this.item.getItemDamage());
			data.writeInt(this.item.stackSize);
		}
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		
		this.position = data.readInt();
		int itemID = data.readInt();
		int metaData = data.readInt();
		int stackSize = data.readInt();
		if ((itemID == -1) || (stackSize == -1)) {
			this.item = null;
		} else {
			this.item = new ItemStack(itemID, stackSize, metaData);
		}
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		TileEntity tileEntity = this.parent.tileEntity;

		if (tileEntity != null) {
			if (tileEntity instanceof IInventory) {
				((IInventory) tileEntity).setInventorySlotContents(this.position, this.item);
			}
		}
	}
}
