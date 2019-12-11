package com.cumulocity.DeleteMyDeviceByGivenParameter;

import java.io.File;

import org.apache.log4j.Logger;

import com.cumulocity.model.authentication.CumulocityBasicCredentials;
import com.cumulocity.model.authentication.CumulocityCredentials;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.PlatformBuilder;
import com.cumulocity.sdk.client.inventory.InventoryApi;

public class Cumulocity {

	private static final Logger logger = Logger.getLogger(Cumulocity.class);

	public static CumulocityCredentials credentials = null;
	public static Platform platform = null;
	public static InventoryApi inventoryApi = null;

	protected static String filePath = System.getProperty("user.home") + File.separator + "app.properties";

	private Cumulocity() {
	}

	public static boolean initialize() {

		Helper.preparePropertyFile(filePath);

		if (Helper.prop != null) {

			try {
				credentials = new CumulocityBasicCredentials(Helper.username, Helper.tenantId, Helper.password, null,
						null);
			} catch (Exception e) {
				logger.error("Check your credentials and your tenantId. ", e);
				return false;
			}

			try {
				platform = PlatformBuilder.platform().withBaseUrl(Helper.server).withCredentials(credentials).build();
			} catch (Exception e) {
				logger.error("Check your server address and your credentials. ", e);
				return false;
			}

			try {
				inventoryApi = platform.getInventoryApi();
			} catch (Exception e) {
				logger.error("There is a problem with the inventory API. ", e);
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

}
