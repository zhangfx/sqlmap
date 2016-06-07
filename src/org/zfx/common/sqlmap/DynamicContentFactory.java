package org.zfx.common.sqlmap;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zfx.common.sqlmap.xml.CaseContent;
import org.zfx.common.sqlmap.xml.DynamicContent;
import org.zfx.common.sqlmap.xml.ElseContent;
import org.zfx.common.sqlmap.xml.EmptyContent;
import org.zfx.common.sqlmap.xml.EqualsContent;
import org.zfx.common.sqlmap.xml.IFContent;
import org.zfx.common.sqlmap.xml.IQueryContent;
import org.zfx.common.sqlmap.xml.NotEmptyContent;
import org.zfx.common.sqlmap.xml.NotEqualsContent;

/**
 * 动态查询语句管理器
 * @author zhangfuxue
 *
 */
public class DynamicContentFactory {
	private static Map<String, Class<? extends IQueryContent>> map = new HashMap<String, Class<? extends IQueryContent>>();
	private static Logger logger = LoggerFactory.getLogger(DynamicContentFactory.class);
	static{
		map.put("if", IFContent.class);
		map.put("case", CaseContent.class);
		map.put("else", ElseContent.class);
		map.put("empty", EmptyContent.class);
		map.put("notempty", NotEmptyContent.class);
		map.put("equals", EqualsContent.class);
		map.put("notequals", NotEqualsContent.class);
	}
	
	private Map<String, String> contentMap;
	
	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}
	
	@SuppressWarnings("unchecked")
	public void init(){
		if(contentMap ==null || contentMap.size() <= 0){
			return;
		}
		try{
			for(String key : contentMap.keySet()){
				map.put(key.toLowerCase(), (Class<? extends IQueryContent>) Class.forName(contentMap.get(key)));
			}
		}catch(ClassNotFoundException e){
			throw new IllegalArgumentException(e);
		}
		logger.info("init()....contentMap:"+map);
	}
	
	public static DynamicContent getDynamicContent(String tagName){
		try {
			return (DynamicContent) map.get(tagName).newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("无效标签："+tagName+"\t"+e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
