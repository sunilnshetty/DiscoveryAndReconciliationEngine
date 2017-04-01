package com.lte.discovery.reconciliation.engine;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lte.discovery.reconciliation.adapter.dto.NE;

public class ResponseManagerTest {

	@Test
	public void test() {
		NE ne = new NE();
		List<NE> neList = new ArrayList<NE>();

		String neFdn = "NE=1077";
		ne.setNeFdn(neFdn);
		String neName = "ENODEB001";
		ne.setNeName(neName);
		String neType = "ENODE-B";
		ne.setNeType(neType);

		neList.add(ne);

		ne = new NE();
		neFdn = "NE=1078";
		ne.setNeFdn(neFdn);
		neName = "ENODEB002";
		ne.setNeName(neName);
		neType = "ENODE-B";
		ne.setNeType(neType);
		
		neList.add(ne);

		System.out.println(ResponseManager.marshal(neList));
	}

}
