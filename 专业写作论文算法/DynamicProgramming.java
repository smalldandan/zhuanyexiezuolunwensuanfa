package imageProcess;

import java.util.ArrayList;
import java.util.List;
 
public class DynamicProgramming {
 
	public DynamicProgramming() {
	}
 
	// 存储类的list
	static final private List<Record> list = new ArrayList<Record>();
	private int k = 0;
 
	// 计算代价值的矩阵表示
	public int[][] getArray(String rowString, String colString) {
		// 行数
		int rowLength = rowString.length();
		// 列数
		int colLength = colString.length();
		int[][] arr = new int[rowLength + 1][colLength + 1];
		// 最底层数据计算
		for (int i = colLength; i >= 0; i--) {
			arr[rowLength][i] = 2 * (colLength - i);
		}
		// 最右侧数据计算
		for (int j = rowLength; j >= 0; j--) {
			arr[j][colLength] = 2 * (rowLength - j);
		}
 
		int penalty = 0;
		for (int i = rowLength - 1; i >= 0; i--) {
			for (int j = colLength - 1; j >= 0; j--) {
				if (rowString.charAt(i) == colString.charAt(j)) {// 计算cost
					penalty = 0;
				} else {
					penalty = 1;
				}
				// 最小的那个
				arr[i][j] = Math.min(arr[i + 1][j + 1] + penalty, Math.min(arr[i + 1][j] + 2, arr[i][j + 1] + 2));
			}
		}
		//////////////////////////////////////////////////////
		for (int i = 0; i <= rowLength; i++) {
			for (int j = 0; j <= colLength; j++) {
				System.out.format("%3d", arr[i][j]);
			}
			System.out.println();
		}
		//////////////////////////////////////////////////////
		return arr;
	}
 
	private int rowDefalut = 0;
	private int colDefalut = 0;
 
	// 回溯 寻找一条路径即可
	public  List<Record> getBackTrack(int arr[][], String rowString, String colString, int tag) {
		// 每次递归 ： 第一个记录为左上方第一个
		if (tag == 0) {
			System.out.println("起始：" + rowDefalut);
			System.out.println(colDefalut);
			list.add(new Record(0, 0));
		} else if (tag == 1) {
			rowDefalut++;
			colDefalut++;
			System.out.println("起始：" + rowDefalut);
			System.out.println(colDefalut);
			list.add(new Record(rowDefalut, colDefalut));
		} else if (tag == 2) {
			rowDefalut++;
			System.out.println("起始：" + rowDefalut);
			System.out.println(colDefalut);
			list.add(new Record(rowDefalut, colDefalut));
		} else if (tag == 3) {
			colDefalut++;
			System.out.println("起始：" + rowDefalut);
			System.out.println(colDefalut);
			list.add(new Record(rowDefalut, colDefalut));
		}
 
		//////////////////////////////////////////////////////
		k++;
		System.out.println("第 " + k + " 次递归！");
 
		//////////////////////////////////////////////////////
		
		// 递归释放条件
		if (arr.length > 1 && arr[0].length > 1) {
			int temp = arr[0][0];// 中间值
			int penalty = 0;// 代价
			if (rowString.charAt(0) == colString.charAt(0)) {
				penalty = 0;
			} else {
				penalty = 1;
			}
 
			// 判断是由右边 下边 右下边 哪一个得到
			// if else if 只会执行一个
			if (temp == arr[0 + 1][0 + 1] + penalty) {
				// list.add(new Record(0 + 1, 0 + 1));
				String SubRowString = rowString.substring(1);
				String SubColString = colString.substring(1);
				// 递归
				getBackTrack(getSubArray(arr, SubRowString, SubColString, 1, 1), SubRowString, SubColString, 1);
 
			} else if (temp == arr[0][0 + 1] + 2) {
				// list.add(new Record(0, 0 + 1));
				String SubRowString = rowString.substring(0);
				String SubColString = colString.substring(1);
				// 递归
				getBackTrack(getSubArray(arr, SubRowString, SubColString, 0, 1), SubRowString, SubColString, 2);
 
			} else if (temp == arr[0 + 1][0] + 2) {
				// list.add(new Record(0 + 1, 0));
				String SubRowString = rowString.substring(1);
				String SubColString = colString.substring(0);
				// 递归
				getBackTrack(getSubArray(arr, SubRowString, SubColString, 1, 0), SubRowString, SubColString, 3);
 
			}
		}
		
		return list;
 
	}
 
	// 一个求解子矩阵的方法
	public int[][] getSubArray(int arr[][], String rowSubString, String colSubString, int rowStart, int colStart) {
		// 子串行数
		int SubrowLength = rowSubString.length();
		// 子串列数
		int SubcolLength = colSubString.length();
		System.out.println(rowSubString);
		System.out.println(colSubString);
		// System.out.println(rowStart);
		// System.out.println(colStart);
		int[][] subArr = new int[SubrowLength + 1][SubcolLength + 1];
		for (int i = 0; i <= SubrowLength; i++) {
			for (int j = 0; j <= SubcolLength; j++) {
				// 相对坐标
				// System.out.println(i);
				// System.out.println(j);
				// System.out.println(arr[rowStart + i][colStart + j]);
				// System.out.println("......");
				subArr[i][j] = arr[rowStart + i][colStart + j];
			}
		}
		///////////////////////////////////////////
		System.out.println("子矩阵:");
		for (int i = 0; i <= SubrowLength; i++) {
			for (int j = 0; j <= SubcolLength; j++) {
				System.out.format("%3d", subArr[i][j]);
			}
			System.out.println();
		}
		///////////////////////////////////////////
		return subArr;
	}
 
	// 获得对齐序列
	public char[][] getAlignmentSequence(String rowString, String colString, List<Record> list) {
		int length = Math.max(rowString.length(), colString.length());
		char[][] sequArr = new char[2][length+1];
 
		
 
		// 第一个记录的 横纵坐标值
		int ifirst = list.get(0).getRow();
		int jfirst = list.get(0).getCol();
		// 第一行放 rowString
		// 第二行放 colString
		sequArr[0][0] = rowString.charAt(ifirst);
		sequArr[1][0] = colString.charAt(jfirst);
		// 第一个之后的记录
		for (int i = 1; i < list.size()-1; i++) {
			System.out.println("list的size:"+list.size());
			int iIn = list.get(i).getRow();
			int jIn = list.get(i).getCol();
			System.out.println(iIn);
			System.out.println(jIn);
			// 判断加gap的情况
			if ((iIn != list.get(i - 1).getRow()) && (jIn != list.get(i - 1).getCol())) {
				// 对角线
				sequArr[0][i] = rowString.charAt(jIn);
				sequArr[1][i] = colString.charAt(iIn);
			} else if ((iIn == list.get(i - 1).getRow()) && (jIn != list.get(i - 1).getCol())) {
				// 下方
				sequArr[0][i] = rowString.charAt(jIn);
				sequArr[1][i] = '#';// 加gap
			} else if ((iIn != list.get(i - 1).getRow()) && (jIn == list.get(i - 1).getCol())) {
				// 右边
				sequArr[0][i] = '#';// 加gap
				sequArr[1][i] = colString.charAt(iIn);
			}
		}
 
		///////////////////////////////////////////////////////////////////////
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(sequArr[i][j] + " ");
			}
			System.out.println();
		}
		///////////////////////////////////////////////////////////////////////
		return sequArr;
	}
 
	public static void main(String[] args) {
		String String1 = "AACAGTTACC";
		String String2 = "TAAGGTCA";
		DynamicProgramming sequence = new DynamicProgramming();
		int[][] costArray = sequence.getArray(String1, String2);
		 List<Record> list = sequence.getBackTrack(costArray, String1, String2, 0);
		char[][] sequArr = sequence.getAlignmentSequence(String1, String2, list);
		System.out.println("对齐序列为:\n" + sequArr);
	}
 
	// 回溯的时候新建这个类，然后将类存在指定的数据结构 ArrayList 中
	class Record {
		int row;
		int col;
 
		public Record(int row, int col) {
			this.row = row;
			this.col = col;
		}
 
		public int getRow() {
			return row;
		}
 
		public int getCol() {
			return col;
		}
	}
}
