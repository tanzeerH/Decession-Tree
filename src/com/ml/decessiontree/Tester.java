package com.ml.decessiontree;

import java.util.ArrayList;

public class Tester {

	private TreeNode rootNode;
	ArrayList<ArrayList<Integer>> examplList;
	ArrayList<Integer> selectedAts;
	private int targetAtt=9;
	private int TOTAL_ATTRIBUTES=10;
	int start=0;
	int end=0;
	int count=0;
	public Tester(TreeNode  rootNode,ArrayList<ArrayList<Integer>> examplList,int targetAtt,ArrayList<Integer> attList,int s,int e) {
		
		this.rootNode=rootNode;
		this.examplList=examplList;
		this.selectedAts=attList;
		this.targetAtt=targetAtt;
		start=s;
		end=e;
		testDataSet();
	}
	public void testDataSet()
	{
		
		for(int i=start;i<end;i++)
			testData(i,rootNode);
		System.out.println(""+count + " "+ (end-start));
	}
	public void testData(int index,TreeNode rn)
	{
		TreeNode tempNode=rn;
		
		while(tempNode.getAttribute()!=targetAtt)
		{
			//System.out.println("temp node "+ tempNode.getAttribute()+ "value "+tempNode.getAttributeValue());
			int att_no=tempNode.getAttribute();
			int att_value=tempNode.getAttributeValue();
			//System.out.println(""+index+"  "+ att_no+"   "+ att_value+ "   "+ tempNode.getChildList().size());
			for(int i=0;i<tempNode.getChildList().size();i++)
			{
				if(tempNode.getChildList().get(i).getAttribute()==targetAtt || tempNode.getChildList().get(i).getAttributeValue()==-1)
				{
					tempNode=tempNode.getChildList().get(i);
					break;
					
				}
				else
				{
					if(examplList.get(att_no).get(index)==tempNode.getChildList().get(i).getAttributeValue())
					{
						tempNode=tempNode.getChildList().get(i);
						break;
					}
				}
			}
		}
		
		//System.out.println(tempNode.getAttributeValue()+"   "+examplList.get(targetAtt).get(index));
		if(tempNode.getAttributeValue()==examplList.get(targetAtt).get(index))
			count++;
		
	}
}
