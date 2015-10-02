package com.ml.decessiontree;

import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.print.attribute.standard.Severity;
import javax.print.attribute.standard.Sides;

public class DeceesionTreeAlgo {

	
	ArrayList<ArrayList<Integer>> attributeList=new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> tempAttList=new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> completeAttList=new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> selectedAttributes=new ArrayList<Integer>();
	private int targetAtt=9;
	private int NUM_OF_COLUMS=10;
	int h=0;
	int count=0;
	int trainCount=0;
	private ArrayList<Integer> suffleList=new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> attValues=new ArrayList<ArrayList<Integer>>();
	public DeceesionTreeAlgo() {

		
		this.targetAtt=9;
		createArraylists();
		readFile();
		
		
		copyAttributes();
		initShufflelist(count);
		shuffleList();
		createAttValuelist();
		
		TreeNode root=algorithmID3(attributeList,targetAtt,selectedAttributes);
		System.out.println("root att: "+root.getAttribute());
		Tester t=new Tester(root, completeAttList, targetAtt, selectedAttributes,trainCount,count);
		//Tester t=new Tester(root, completeAttList, targetAtt, selectedAttributes,0,60);
	}

	private void  createArraylists()
	{
		for(int i=0;i<NUM_OF_COLUMS;i++)
		{
			ArrayList<Integer> list=new ArrayList<Integer>();
			attributeList.add(list);
		}
		
		for(int i=0;i<NUM_OF_COLUMS;i++)
		{
			ArrayList<Integer> list=new ArrayList<Integer>();
			tempAttList.add(list);
		}
		
		for(int i=0;i<NUM_OF_COLUMS;i++)
		{
			ArrayList<Integer> list=new ArrayList<Integer>();
			completeAttList.add(list);
		}
	
		for(int i=0;i<NUM_OF_COLUMS;i++)
		{
			ArrayList<Integer> list=new ArrayList<Integer>();
			attValues.add(list);
		}
		
		
	}
	private void copyAttributes()
	{
		selectedAttributes.clear();
		for(int i=0;i<NUM_OF_COLUMS-1;i++)
			selectedAttributes.add(i);
		
	}
	private void readFile() {
		count=0;
		BufferedReader br = null;
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(
					"D:/Android_workspace/DecessionTree/data.csv"));

			while ((sCurrentLine = br.readLine()) != null) {
				
				String[] values=sCurrentLine.split(",");
				organizeValues(values);
				
				//for testing purpose
				count++;
				//if(count>200)
				//	break;
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		//Collections.shuffle(attributeList);
		//printValues();
	}
	private void initShufflelist(int count)
	{
		for(int i=0;i<count;i++)
			suffleList.add(i);
	}
	private void shuffleList()
	{
		Collections.shuffle(suffleList);
		for(int i=0;i<count;i++)
			for(int j=0;j<NUM_OF_COLUMS;j++)
				tempAttList.get(j).add(completeAttList.get(j).get(i));
		
		for(int j=0;j<NUM_OF_COLUMS;j++)
			completeAttList.get(j).clear();
		
		//System.out.println("size"+tempAttList.size());
		for(int i=0;i<count;i++)
			for(int j=0;j<NUM_OF_COLUMS;j++)
				{
				completeAttList.get(j).add(tempAttList.get(j).get(suffleList.get(i)));
				//completeAttList.get(j).add(tempAttList.get(j).get(i));
				}
		
		for(int j=0;j<NUM_OF_COLUMS;j++)
			attributeList.get(j).clear();
		
		 trainCount=(int)(count*.8);
		
		for(int i=0;i<trainCount;i++)
		{
			for(int j=0;j<NUM_OF_COLUMS;j++)
			{
				attributeList.get(j).add(completeAttList.get(j).get(i));
			}
		}
		
		
	}
	public void createAttValuelist()
	{
	
		int length=attributeList.get(0).size();
		
		for(int i=0;i<length;i++)
		{
			for(int j=0;j<NUM_OF_COLUMS;j++)
			{
				int val=attributeList.get(j).get(i);
				if(!attValues.get(j).contains(val))
					attValues.get(j).add(val);
			}
		}
		
		
		//System.out.println("Printing values");
		for(int j=0;j<NUM_OF_COLUMS;j++)
		{
			int val=attValues.get(j).size();
			//System.out.println("val"+ j+"  "+val);
			
		}
		//System.out.println("Printing values again");
		for(int j=0;j<attValues.get(0).size();j++)
		{
			int val=attValues.get(0).get(j);
			//System.out.println("val"+ j+"  "+val);
			
		}
		
		
	}
	public TreeNode algorithmID3(ArrayList<ArrayList<Integer>> attList,int targetAtt,ArrayList<Integer> selectedAtts)
	{
		/*h++;
		if(h>10)
			return null;
		*/
		ID3Helper id3Helper=new ID3Helper(attList, targetAtt, selectedAtts,attValues);
		
		TreeNode treeNode=new TreeNode();
		boolean check=id3Helper.checkForAllPositive();
		if(check)
		{
			treeNode.setAttribute(targetAtt);
			treeNode.setAttributeValue(1);
			
		System.out.println("All positive found");
			return treeNode;
		}
		check=id3Helper.checkForAllNegative();
		if(check)
		{
			treeNode.setAttribute(targetAtt);
			treeNode.setAttributeValue(0);
			
			System.out.println("All positive found");
			return treeNode;
		}
		if(selectedAtts.size()==0)
		{
			treeNode.setAttribute(targetAtt);
			treeNode.setAttributeValue(id3Helper.getMostCommonTargetAtt());		
			//System.out.println("attlist empty. most common value :"+targetAtt);
			
			return treeNode;
		}
	  int bestAtt=id3Helper.getBestAttribute();
	  
	  treeNode.setAttribute(bestAtt);  //setting best attribute for tree node
	  
	 // System.out.println("best attr "+ bestAtt);
	  ArrayList<Integer> valueList=id3Helper.getAttributeValueLisByIndex(bestAtt);
	//  System.out.println("best attr "+ bestAtt + "child size "+valueList.size());
	  for(int i=0;i<valueList.size();i++)
	  {
		  TreeNode branchNode=new TreeNode();
		  branchNode.setAttribute(bestAtt);
		  branchNode.setAttributeValue(valueList.get(i));
		  treeNode.getChildList().add(branchNode);
		  
		  ArrayList<ArrayList<Integer>> newExList=id3Helper.getExamplesByAttribute(bestAtt,valueList.get(i));
		//  System.out.println("att size"+ selectedAtts.size());
		 // System.out.println("new example size:"+ newExList.get(bestAtt).size());
		
		  if(newExList.isEmpty())
		  {
			  TreeNode leafNode=new TreeNode();
			  leafNode.setAttribute(targetAtt);
			  leafNode.setAttributeValue(id3Helper.getMostCommonTargetAtt());
			  branchNode.getChildList().add(leafNode);
		  }
		  else{
			  selectedAtts.remove(new Integer(bestAtt));
			  branchNode.getChildList().add( algorithmID3(newExList,targetAtt,selectedAtts));
		  }
	  }
		return treeNode;
		
	}
	
	private void printValues()
	{
		int length=completeAttList.get(0).size();
		for(int i=0;i<length;i++)
		{
			System.out.println(""+completeAttList.get(9).get(i)+  " i"+ i);
		}
	}
	
	private void organizeValues(String [] arr)
	{
		for(int i=0;i<arr.length;i++)
		{
			completeAttList.get(i).add(Integer.parseInt(arr[i]));
		}
	}

}
