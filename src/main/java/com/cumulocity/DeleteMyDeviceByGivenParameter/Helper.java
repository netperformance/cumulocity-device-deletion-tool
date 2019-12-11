package com.cumulocity.DeleteMyDeviceByGivenParameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Helper {

  private static final Logger logger = Logger.getLogger(Helper.class);

  public static Properties prop = new Properties();

  protected static String username;
  protected static String tenantId;
  protected static String password;
  protected static String server;
  protected static String protocol;
  protected static String deleteAllDevicesAfterGivenTimestamp;
  protected static String deleteAllDevicesBetweenGivenTimestamps;
  protected static String deletionMode;

  private Helper() {
  }

  protected static Properties preparePropertyFile(String filePath) {
    try {
      prop.load(new FileInputStream(filePath));
      setSettingsFilerValues();
      return prop;
    } catch (IOException e) {
      logger.error("Can't read the property file. Copy your property file (app.properties) into the following folder: "+ filePath);
      prop = null;
      return null;
    }
  }

  protected static void setSettingsFilerValues() {
    username = valid(prop.getProperty("username"));
    tenantId = valid(prop.getProperty("tenantId"));
    password = valid(prop.getProperty("password"));
    server = valid(prop.getProperty("server"));
    protocol = valid(prop.getProperty("protocol"));
    deleteAllDevicesAfterGivenTimestamp = valid(prop.getProperty("deleteAllDevicesAfterGivenTimestamp"));
    deleteAllDevicesBetweenGivenTimestamps = valid(prop.getProperty("deleteAllDevicesBetweenGivenTimestamps"));
    deletionMode = valid(prop.getProperty("deletionMode"));
  }

  public static String valid(String str) {
    if (str != null && str.length() >= 1) {
      return str.replaceAll("\\s+", "");
    }
    return null;
  }

  public static int toInt(String str) {
    if (valid(str) != null) {
      try {
        return Integer.parseInt(str);
      } catch (Exception e) {
        return 0;
      }
    } else {
      return 0;
    }
  }

}
