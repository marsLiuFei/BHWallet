package com.android.bhwallet.views.Dialogs.IOSDialog;

public class SheetItem {

	private String strItemName;
	private int ITEM_CODE;// ��ÿ��item����һ��code �����ص���ʱ������������һ��item;;;

	public SheetItem() {
		super();
	}
	public SheetItem(String strItemName, int iTEM_CODE) {
		super();
		this.strItemName = strItemName;
		ITEM_CODE = iTEM_CODE;
	}

	@Override
	public String toString() {
		return "SheetItem [strItemName=" + strItemName + ", ITEM_CODE=" + ITEM_CODE + "]";
	}
	public String getStrItemName() {
		return strItemName;
	}
	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}
	public int getITEM_CODE() {
		return ITEM_CODE;
	}
	public void setITEM_CODE(int iTEM_CODE) {
		ITEM_CODE = iTEM_CODE;
	}
}
