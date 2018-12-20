# 服务监控
> 依赖的服务
> * kanon-eurake
> * kanon-config
> * 当使用短信/钉钉通知时,kanon-message-center
---
> 依赖的中间件
> * rabbitmq
---
> 自定义配置
> `motifier.mobile.enabled`开启短信通知  
> `motifier.mobile.sign` 短信签名  
> `motifier.mobile.template_code` 使用的短信模板编号  
> `motifier.mobile.mobiles` 需要短信通知的手机号  
> `motifier.dingtalk.enabled` 开启钉钉通知  
> `motifier.dingtalk.agent_id` 使用通知的微应用id  
> `motifier.dingtalk.user_ids` 需要通知的用户