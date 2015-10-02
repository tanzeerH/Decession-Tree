package com.ml.decessiontree;

import java.awt.Label;
import java.util.ArrayList;

public class TreeNode {

	private int attribute;
	private int attributeValue=-1;
	private int label=-1; 
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	private ArrayList<TreeNode> childList=new ArrayList<TreeNode>();
	public int getAttribute() {
		return attribute;
	}
	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
	public int getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(int attributeValue) {
		this.attributeValue = attributeValue;
	}
	public ArrayList<TreeNode> getChildList() {
		return childList;
	}
	public void setChildList(ArrayList<TreeNode> childList) {
		this.childList = childList;
	}
}
