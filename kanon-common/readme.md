######################
#通用开发包
#####################

#相关配置
`auth.ignore.urls` 请求的路径白名单，配合认证使用  
`auth.ignore.clients` 请求的服务id白名单，配合认证使用
`server.auth.name` 认证服务的服务名    （必须）
`server.upms.name` 通用权限服务的服务名    （必须）
`swagger2.api.title` swagger2接口界面的标题  
`swagger2.api.description` swagger2接口界面的描述  
`swagger2.api.serviceUrl` swagger2接口界面的项目访问路径  
`swagger2.api.version` swagger2接口界面的接口版本  
`swagger2.api.contactName` swagger2接口界面的联系人姓名  
`swagger2.api.contactUrl` swagger2接口界面的联系人博客地址  
`swagger2.api.contactEmail` swagger2接口界面的联系人邮件地址  

---
* 注：spring-validation使用的国际化文件地址与springboot本身的国际化地址一致。调节`spring.messages.basename`即可