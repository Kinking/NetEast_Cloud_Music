package com.geek_dream.jerry.json;

import java.util.List;





import com.geek_dream.jerry.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	public List<User> StringFromJson (String jsondata)
	{     
		Gson gson=new Gson();
		List<User> list=gson.fromJson(jsondata, new TypeToken<List<User>>(){}.getType());
		return list;

	}

}
