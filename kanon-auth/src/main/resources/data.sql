-- 插入网关服务的认证数据
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `scope`, `authorized_grant_types`, `autoapprove`) select 'kanon', 'kanon', 'server', 'password,refresh_token', 'false' from DUAL where not exists (select client_id from oauth_client_details where client_id='kanon');