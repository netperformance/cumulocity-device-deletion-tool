package com.cumulocity.DeleteMyDeviceByGivenParameter;

import java.util.List;

import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.inventory.InventoryFilter;
import com.cumulocity.sdk.client.inventory.ManagedObjectCollection;
import com.cumulocity.sdk.client.inventory.PagedManagedObjectCollectionRepresentation;
import com.google.common.collect.Lists;

import c8y.IsDevice;

public class App {
	
	public static void main(String[] args) {
		if (Cumulocity.initialize()) {
			try {
				
				long deleteAllDevicesAfterGivenTimestamp = 0;
				String[] deleteAllDevicesBetweenGivenTimestamps = null;
				
				if(Helper.deletionMode.equals("deleteAllDevicesAfterGivenTimestamp")) {
					deleteAllDevicesAfterGivenTimestamp = Long.parseLong(Helper.deleteAllDevicesAfterGivenTimestamp);
				} else {
					deleteAllDevicesBetweenGivenTimestamps = Helper.deleteAllDevicesBetweenGivenTimestamps.split(",");
				}

				// managed object representation list
				List<ManagedObjectRepresentation> managedObjectRepresentationList = getManagedObjectRepresentationList();
				
				// get the managed objects from the managed object representation list
				for (ManagedObjectRepresentation managedObjectRepresentation : managedObjectRepresentationList) {

					// delete all devices after given timestamp
					if(deleteAllDevicesAfterGivenTimestamp!=0) {
						if(managedObjectRepresentation.getCreationDateTime().isAfter(deleteAllDevicesAfterGivenTimestamp)) {
							System.out.println("Going to delete: " + managedObjectRepresentation.getName() + " - " + managedObjectRepresentation.getCreationDateTime() + " - " + managedObjectRepresentation.getCreationDateTime().getMillis() + " - " + managedObjectRepresentation.getId());
							Cumulocity.inventoryApi.delete(managedObjectRepresentation.getId());
							System.out.println("Deleted: "+ managedObjectRepresentation.getName() + " - " + managedObjectRepresentation.getCreationDateTime() + " - " + managedObjectRepresentation.getCreationDateTime().getMillis() + " - " + managedObjectRepresentation.getId());
						}
					}
					// delete all devices between given timestamps
					else {
						if(managedObjectRepresentation.getCreationDateTime().isAfter(Long.parseLong(deleteAllDevicesBetweenGivenTimestamps[0]))) {
							if(managedObjectRepresentation.getCreationDateTime().isBefore(Long.parseLong(deleteAllDevicesBetweenGivenTimestamps[1]))) {
								System.out.println("Going to delete: " + managedObjectRepresentation.getName() + " - " + managedObjectRepresentation.getCreationDateTime() + " - " + managedObjectRepresentation.getCreationDateTime().getMillis() + " - " + managedObjectRepresentation.getId());
								Cumulocity.inventoryApi.delete(managedObjectRepresentation.getId());
								System.out.println("Deleted: "+ managedObjectRepresentation.getName() + " - " + managedObjectRepresentation.getCreationDateTime() + " - " + managedObjectRepresentation.getCreationDateTime().getMillis() + " - " + managedObjectRepresentation.getId());	
							}
						}
					}					
					
				}
				System.out.println("DONE!");
			} catch(Exception e) {
				System.out.println("Invalid timestamp: "+e);
			}
		}
	}
	
	public static List<ManagedObjectRepresentation> getManagedObjectRepresentationList() {
		try {
	    	ManagedObjectCollection managedObjectCollection = Cumulocity.inventoryApi.getManagedObjectsByFilter(new InventoryFilter().byFragmentType(IsDevice.class));	    	
	    	
	    	PagedManagedObjectCollectionRepresentation pagedManagedObjectCollectionRepresentation = managedObjectCollection.get();
	    	Iterable<ManagedObjectRepresentation> iterable = pagedManagedObjectCollectionRepresentation.allPages();
	    	List<ManagedObjectRepresentation> managedObjectRepresentationList = Lists.newArrayList(iterable);	    	
	    	
	    	return managedObjectRepresentationList;
			
		} catch(Exception e) {
			return null;
		}
	}
}
