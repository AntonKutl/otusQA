package stubs.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationService {
  private static ConfigurationService instance = null;
  private final Properties properties = new Properties();

  private static final String PROPERTY_FILE = "common.properties";


  private ConfigurationService() {
    try {
      loadProperties();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to load property file.", e);
    }
  }

  public static String getProperty(final String propertyName) {
    if (instance == null) {
      instance = new ConfigurationService();
    }
    return instance.properties.getProperty(propertyName);
  }

  private void loadProperties() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE);
    if (inputStream == null) {
      throw new IOException("Unable to open stream.");
    }
    final Properties props = new Properties();
    props.load(inputStream);
    inputStream.close();
    properties.putAll(props);
  }
}
