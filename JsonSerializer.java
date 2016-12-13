package com.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonSerializer
{
	final static ObjectMapper objectMapper;

	static
	{
		SimpleModule module = new SimpleModule();
		module.addSerializer(BigDecimal.class, new com.fasterxml.jackson.databind.JsonSerializer<BigDecimal>()
		{
			@Override
			public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException
			{
				DecimalFormat df = new DecimalFormat("0.000000000000");
				gen.writeString(df.format(value));
			}
		});
		module.addSerializer(Long.class, new com.fasterxml.jackson.databind.JsonSerializer<Long>()
		{
			@Override
			public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException
			{
				gen.writeString(value.toString());
			}
		});
		module.addSerializer(BigInteger.class, new com.fasterxml.jackson.databind.JsonSerializer<BigInteger>()
		{
			@Override
			public void serialize(BigInteger value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException
			{
				gen.writeString(value.toString());
			}
		});
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss"));
		objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		objectMapper.registerModule(module);
	}

	public static String ToJson(Object obj)
	{
		try
		{
			return objectMapper.writeValueAsString(obj);
		}
		catch (Exception ex)
		{
			return null;
		}

	}

	public static String ToPrettyJson(Object obj)
	{
		try
		{
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		}
		catch (Exception ex)
		{
			return null;
		}

	}

	/**
	 * 需要建立一个对应的T类型class来进行反序列化操作。
	 * @param <T>
	 * @param jsonStr
	 * @param objClass
	 * @return
	 */
	public static <T> T FromJson(String jsonStr, Class<T> objClass)
	{
		try
		{
			return objectMapper.readValue(jsonStr, objClass);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	public static <T> T FromJson(String jsonStr, TypeReference<T> typeReference)
	{
		try
		{
			return objectMapper.readValue(jsonStr, typeReference);
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
