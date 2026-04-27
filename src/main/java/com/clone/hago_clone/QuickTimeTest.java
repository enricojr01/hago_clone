/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Enrico Tuvera Jr
 */
public class QuickTimeTest {
	public static void main(String[] args) {
		LocalDateTime ldt = LocalDateTime.now();
		LocalDate ld = LocalDate.now().plusDays(1);
		LocalTime midnight = LocalTime.of(0, 0);
		// the input needs YYYY-MM-DDTHH:mm
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'THH:mm");
		String oneDayAhead = LocalDateTime.of(ld, midnight).format(dtf);
		System.out.println("ldt: " + ldt);
		System.out.println("ld: " + ld);
		System.out.println("midnight: " + midnight);
		System.out.println("oneDayAhead: " + oneDayAhead);
				
	}	
}
