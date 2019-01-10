import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SequenceAligner {
	public String sequence1;
	public String sequence2;
	
	public String alignSequence1 = "";
	public String alignSequence2 = "";
	
	public ArrayList<String> traceBack = new ArrayList<String>();
	
	public ArrowAndValue [][] matrix;
	
	public SequenceAligner(String a, String b)
	{		
		setSequences(a, b);
		
	}
	
	public SequenceAligner()
	{
		
	}
	
	public void setSequences(String a, String b)
	{		
		a = a.toUpperCase();
		b = b.toUpperCase();
		a = a.replaceAll("\\s+", "");
		b = b.replaceAll("\\s+", "");
		if(a.length() <= b.length()) //# of columns will have bigger string
		{
			sequence1 = b;
			sequence2 = a;
			matrix = new ArrowAndValue[sequence2.length()+1][sequence1.length()+1];
			for(int i = 0;i<matrix.length;i++)
			{
				for(int j = 0;j<matrix[0].length;j++)
				{
					matrix[i][j] = new ArrowAndValue();
				}
			}
		}
		
		else
		{
			sequence1 = a;
			sequence2 = b;
			matrix = new ArrowAndValue[sequence2.length()+1][sequence1.length()+1];
			for(int i = 0;i<matrix.length;i++)
			{
				for(int j = 0;j<matrix[0].length;j++)
				{
					matrix[i][j] = new ArrowAndValue();
				}
			}
		}
		
		matrix[0][0].setArrowAndValue("stay", 0);
		int default1 = -2;
		for(int i = 1;i<matrix.length;i++)
		{
			matrix[i][0].setArrowAndValue("north", default1);
			default1-=2;
		}
		
		default1 = -2;
		for(int i = 1;i<matrix[0].length;i++)
		{
			matrix[0][i].setArrowAndValue("west", default1);
			default1-=2;
		}
		
	}
	
	public void clearAllData()
	{
		sequence1 = "";
		sequence2 = "";
		alignSequence1 = "";
		alignSequence2 = "";
		matrix = null;
		traceBack.clear();
	}

	public void align()
	{
		for(int i = 1;i<matrix.length;i++)
		{
			for(int j = 1;j<matrix[0].length;j++)
			{
				//look at W
				int west = matrix[i][j-1].getValue() - 2;
				
				//look at N
				int north = matrix[i-1][j].getValue() - 2;
				
				//look at NW
				int nw = matrix[i-1][j-1].getValue() + 1;
				if(sequence1.charAt(j-1) != sequence2.charAt(i-1))
				{
					nw = matrix[i-1][j-1].getValue() - 1;
				}
				
				if(west >= north && west >= nw)
				{
					matrix[i][j].setArrowAndValue("west", west);
				}
				
				if(north >= west && north >= nw)
				{
					matrix[i][j].setArrowAndValue("north", north);
				}
				
				if(nw >= west && nw >= north)
				{
					matrix[i][j].setArrowAndValue("nw", nw);
				}
			}
		}
		traceBack();
	}
	
	public void traceBack()
	{
		int i = matrix.length-2;
		int j = matrix[0].length-2;
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();
		while(matrix[i+1][j+1].getArrow() != "stay")
		{
			if(matrix[i+1][j+1].getArrow() == "north")//gap in north
			{
				traceBack.add("north");
				s1.append('-');
				s2.append(sequence2.charAt(i));
				i -= 1;
			}
			
			else if(matrix[i+1][j+1].getArrow() == "west")//gap in west
			{
				traceBack.add("west");
				s1.append(sequence1.charAt(j));
				s2.append('-');
				j -= 1;
			}
			
			else if(matrix[i+1][j+1].getArrow() == "nw")
			{
				traceBack.add("nw");
				s1.append(sequence1.charAt(j));
				s2.append(sequence2.charAt(i));
				i -= 1;
				j -= 1;
			}
			
		}
		
		alignSequence1 = s1.reverse().toString();
		alignSequence2 = s2.reverse().toString();
	}
	
	public ArrayList<String> getTraceBack()
	{
		return traceBack;
	}
	
	public int getScore()
	{
		return matrix[matrix.length-1][matrix[0].length-1].getValue();
	}
	
	public String getResult1()
	{
		return alignSequence1;
	}
	
	public String getResult2()
	{
		return alignSequence2;
	}
	
	public boolean writeToFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException
	{
		if(alignSequence1.isEmpty() || alignSequence2.isEmpty()) {
			return false;
		}
		if(fileName.isEmpty()) { //default name to results.txt
			fileName = "results";
		}
		PrintWriter writer = new PrintWriter(fileName + ".txt", "UTF-8");
		writer.println("Optimal Score: " + getScore());
		writer.println();
		writer.println("Traceback: " + traceBack);
		writer.println();
		writer.println("Aligned Sequence 1: ");
		writer.println(alignSequence1);
		writer.println();
		writer.println("Aligned Sequence 2: ");
		writer.println(alignSequence2);
		writer.close();
		return true;
	}
}