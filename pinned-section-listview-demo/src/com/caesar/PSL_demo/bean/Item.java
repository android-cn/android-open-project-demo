package com.caesar.PSL_demo.bean;

public class Item {
	
	/**
	 * 普通item
	 */
	public static final int ITEM = 0;
	
	/**
	 * 是pinned section
	 */
	public static final int SECTION = 1;
	
	/**
	 * 当前item类型
	 */
	public int type;
	
	/**
	 * section 在数据集合中的位置
	 */
	public int sectionPosition;
	
	/**
	 * 当前item在数据集中的位置
	 */
	public int listPostition;
	
    /**显示的内容
     * 
     */
    public String content;
	

}
