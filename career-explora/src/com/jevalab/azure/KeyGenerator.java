package com.jevalab.azure;

import java.util.Set;
import java.util.TreeSet;

import com.jevalab.azure.persistence.UserKey;
import com.jevalab.azure.persistence.UserKeyJpaController;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class KeyGenerator {

	static Set<String> generateKeys() {
		Set<String> keys = new TreeSet<>();
		int si = 0;
		while (keys.size() < 600) {
			
			String i = Double.toString(Math.random());
			si = i.indexOf('.') + 1;
			if (i.length() > 16) {
				i = i.substring(si, 12);
			}
			String k = Long.toString(System.currentTimeMillis());
			k = k.substring(k.length() - 4, k.length());
			i += k;
			if (i.contains(".")) {
				continue;
			} else {
				keys.add(i);
			}

			try {
				Thread.sleep(1);
			} catch (Exception e) {

			}
		}
		System.out.println(keys);
		return keys;
	}
	
	static void persistKeys(Set<String> keys) throws PreexistingEntityException, RollbackFailureException, Exception {
		
		UserKey uk = null;
		UserKeyJpaController c1 = new UserKeyJpaController();
		int i = 1;
		for(String s: keys) {
			uk = new UserKey(s);
			if((i%6) == 0) {
				uk.setValidity("1");
			} else {
				uk.setValidity("12");
			}
			
			i++;
			
			c1.create(uk);
		}
	}

}
