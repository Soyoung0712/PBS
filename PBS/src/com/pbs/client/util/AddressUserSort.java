package com.pbs.client.util;

import java.util.Comparator;
import com.pbs.client.model.AddressUser;

public class AddressUserSort implements Comparator<AddressUser> {

	public int compare(AddressUser front, AddressUser back) {
		
		if( front != null && back != null ) {
			// front�� ŭ
			if(front.getName().compareTo(back.getName()) > 0 ) {
				return 1;
			// back�� ŭ
			}else {
				return -1;
			}
		}else {
			return -1;
		}
		
	}

}
