package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigService
{
	private static final Logger LOG = LoggerFactory.getLogger(ConfigService.class);

	/**
	 * @param fileName
	 *            以/target/classes为基准
	 * @return 如果有异常返回null，否则返回Properties对象
	 */
	public static Properties loadPropertiesFromFile(String fileName)
	{
		InputStream inStream = null;
		Properties p = new Properties();
		try
		{
			inStream = ConfigService.class.getClassLoader().getResourceAsStream(fileName);
			if (null == inStream)
			{
				LOG.error("{} not exit!", fileName);
				p = null;
			}
			p.load(inStream);
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
			p = null;
		}
		finally
		{
			if (null != inStream)
			{
				try
				{
					inStream.close();
				}
				catch (IOException e)
				{
					LOG.error(e.getMessage(), e);
					p = null;
				}
				finally
				{
					inStream = null;
				}
			}
		}
		return p;
	}

}
