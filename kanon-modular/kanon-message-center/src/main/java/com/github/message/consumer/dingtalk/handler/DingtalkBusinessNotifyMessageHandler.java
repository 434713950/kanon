package com.github.message.consumer.dingtalk.handler;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.github.kanon.common.constants.MessageChannel;
import com.github.kanon.common.notify.DingTalkBusinessNotifyTemplate;
import com.github.message.consumer.dingtalk.config.DingTalkConfig;
import com.github.message.exception.MessagePushFailException;
import com.github.message.handler.AbstractNotifyMessageHandler;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>钉钉企业消息推送处理器</p>
 *
 * @author PengCheng
 * @date 2018/11/5
 */
@Component(MessageChannel.DINGTALK_WORKING_NOTIFY)
public class DingtalkBusinessNotifyMessageHandler extends AbstractNotifyMessageHandler<DingTalkBusinessNotifyTemplate> {

    public static final String DING_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";

    public static final String DING_WORKING_MESSAGE_NOTIFY_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    @Autowired
    private DingTalkConfig dingTalkConfig;

    @Override
    public void check(DingTalkBusinessNotifyTemplate notifyTemplate) {
        if (StringUtils.isEmpty(dingTalkConfig.getCropId())|| StringUtils.isEmpty(dingTalkConfig.getCropSecret())){
            throw new MessagePushFailException("Lack of corresponding dingtalk configuration!");
        }
        if (notifyTemplate.getContent()==null){
            throw new MessagePushFailException("having not message!");
        }
    }

    @Override
    public Boolean process(DingTalkBusinessNotifyTemplate notifyTemplate) {
        DefaultDingTalkClient client = new DefaultDingTalkClient(DING_WORKING_MESSAGE_NOTIFY_URL);
        try {
            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            //当前服务不能通知企业下的所有人,必须指定通知人
            request.setToAllUser(false);
            request.setUseridList(notifyTemplate.getUsers());
            request.setAgentId(notifyTemplate.getAgentId());

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
            msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
            msg.getOa().getHead().setText(notifyTemplate.getTitle());
            msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
            msg.getOa().getBody().setContent(notifyTemplate.getContent());
            msg.setMsgtype("oa");
            request.setMsg(msg);

            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,getAccessToken());
            if (response.getErrcode().equals(0L)){
                return true;
            }
            return false;
        } catch (ApiException e) {
            throw new MessagePushFailException("dingtalk working notify connect fail",e);
        }
    }

    /**
     * 获取accessToken
     * @return
     */
    private String getAccessToken() {
        DefaultDingTalkClient client = new DefaultDingTalkClient(DING_ACCESS_TOKEN_URL);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setCorpid(dingTalkConfig.getCropId());
        request.setCorpsecret(dingTalkConfig.getCropSecret());
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response =client.execute(request);
            if (response.getErrcode().equals(0L)){
                return response.getAccessToken();
            }
            throw new MessagePushFailException("dingtalk access token get error\r\nerrorCode:"+response.getErrcode()+";errorMsg:"+response.getErrmsg());
        } catch (ApiException e) {
            throw new MessagePushFailException("dingtalk access token connect fail",e);
        }
    }
}
