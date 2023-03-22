*.返回状态码定义:      
--        
        自定义业务代码code: 以600开头，例如 code:600100 ,msg:"不存在该项目活动"

*.请求分页参数需要继承PageQueryBase,参数大于三个的时候要改为对象接收，绝对不允许map接收       
---

*.返回格式定义必须是以下格式          
---
{
	"code": 200,
	"msg": "success",
	"data": null
}

*.返回page格式      
---
{       
	"code": 200,        
	"msg": "success",       
	"data": {       
		"list": [],     
        "total": 0,     
		"size": 1,      
		"current": 10,      
		"searchCount": true,        
		"pages": 0      
	}
}

*.命名规范：
 ---            
    遵守阿里编码规范，在idea安装插件。     
    返回字段，请求地址统一采用驼峰。
    工具类以Utils结尾，例如JWTUtils
    枚举统一采用Enum结尾，例如 ResultEnum 
    
*.开发注意：
 ---  
    本项目里，mybatis的配置在两个地方需要注意
    2、xxxmapper.xml文件需要与其他mapper.xml文件分开放置的话，需要将文件夹命名为xxx-mapper，在DBConstants配置文件里CLOUD_MAPPER_XML字段有对xml文件的地址限制；
    不分开配置则直接放在cloud-mapper里面

## git commit规范

|  功能   | commit规范  | 示例 | 描述 |
|  ----  | ----  | ---- | ---- |
| 新功能  | feature/module_name | feature/multi_merchant | 开发一个新功能 |
| bug修复  | bugfix/fix_name | bugfix/user | 修复某个功能模块的bug |
| 紧急修复  | hotfix/fix_name | hotfix/create_order | 紧急修复某个严重bug |
| 性能优化  | perf/name | pref/user_login | 优化某个功能的性能 |
| 格式调整  | style/name | style/log_print | 做一下不影响任何业务的优化，比如删掉不使用了的注释之类 |
| 重构  | refactor/name | refactor/user | 重构某个功能模块 |    