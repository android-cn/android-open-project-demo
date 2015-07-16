package com.guogavin.retorifit.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Arrays;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.FormUrlEncodedTypedOutput;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

/**
 * 自定义的数据转换器
 * @author gavinguo
 *
 */
public class StringConverter implements Converter {

	@Override
	public String fromBody(TypedInput body, Type type)
			throws ConversionException {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		try {
			InputStream is = body.in();
			byte[] buffer = new byte[1024];
			int readCount = 0;
			while((readCount = is.read(buffer)) != -1){
				result.append(new String(Arrays.copyOfRange(buffer,0,readCount),"UTF-8"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.toString().contains("<script")){
			return result.toString().split("<script")[0];
		}else{
			return result.toString();
		}
	}

	@Override
	public TypedOutput toBody(Object object) {
		// TODO Auto-generated method stub
		if(object instanceof User){
			User user = (User) object;
			FormUrlEncodedTypedOutput formUrlEncodedTypedOutput = new FormUrlEncodedTypedOutput();
			formUrlEncodedTypedOutput.addField("name",user.getName());
			formUrlEncodedTypedOutput.addField("age",user.getAge()+"");
			formUrlEncodedTypedOutput.addField("money",user.getMoney()+"");
			return formUrlEncodedTypedOutput;
		}
		return null;
	}

}
