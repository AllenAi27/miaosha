package com.imooc.miaosha_1;

import java.util.Scanner;

public class TestScanner {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextInt()) {
			break;
		}
		while(sc.hasNext()) {
			String str = sc.next();
			char[] charArr = str.toCharArray();
			for(char c : charArr) {
				if(c - 'a' >= 0) {
					String strUpperCase = String.valueOf(c).toUpperCase();
					System.out.print(strUpperCase);
				}else if(c - 'A' >= 0 && c - 'a' < 0) {
					String strToLowerCase = String.valueOf(c).toLowerCase();
					System.out.print(strToLowerCase);
				}
			}
		}
		sc.close();
	}
}
