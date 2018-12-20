# 消息中心
### 主要用来发送各种3方消息(如:短信、钉钉通知)
> 现提供消息服务及使用的mq队列：
> * 钉钉消息服务 MqQueueConstant.DINGTALK_SERVICE_STATUS_CHANGE   
> * 阿里云sms服务 MqQueueConstant.SMS_SERVICE_STATUS_CHANGE 
---
> 自定义配置
> `aliyun.enabled`是否启动阿里云短信通知  
> `aliyun.access_key_id`阿里云认证key  
> `aliyun.access_key_secret`阿里云认证secret  
> `dingtalk.enabled`是否启动钉钉通知  
> `dingtalk.crop_id`钉钉企业id 
> `dingtalk.crop_secret`钉钉企业密码  