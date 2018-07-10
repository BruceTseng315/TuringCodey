package turing.turingcodey.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.iot.model.v20170420.QueryDeviceByNameRequest;
import com.aliyuncs.iot.model.v20170420.QueryDeviceByNameResponse;
import com.aliyuncs.iot.model.v20170420.RRpcRequest;
import com.aliyuncs.iot.model.v20170420.RRpcResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.io.InputStream;
import java.util.Properties;

public class DeviceManageUtils {
    public static DefaultAcsClient iotClient;
    public static  String accessKey;
    public static  String accessSecret;
    static{
        Properties prop = new Properties();
        try {
            prop.load(Object.class.getResourceAsStream("/config.properties"));
            accessKey = prop.getProperty("user.accessKeyID");
            accessSecret = prop.getProperty("user.accessKeySecret");
            String regionId = prop.getProperty("iot.regionId");

            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
            DefaultProfile.addEndpoint(regionId, regionId, prop.getProperty("iot.productKey"),
                    prop.getProperty("iot.domain"));
            // 初始化client
            iotClient = new DefaultAcsClient(profile);

        } catch (Exception e) {
            LogUtil.print("初始化client失败！exception:" + e.getMessage());
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static AcsResponse executerequest(RpcAcsRequest request) {
        AcsResponse response = null;
        try {
            response = iotClient.getAcsResponse(request);
        } catch (Exception e) {
            LogUtil.print("执行失败：e:" + e.getMessage());
        }
        return response;
    }

    /**
     * 查询设备
     * @param productKey
     * @param deviceName
     */
    public static JSONObject queryDeviceByName(String productKey, String deviceName) {
        JSONObject result = new JSONObject();
        QueryDeviceByNameRequest request = new QueryDeviceByNameRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        QueryDeviceByNameResponse response = (QueryDeviceByNameResponse)executerequest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("查询设备成功！ " + JSONObject.toJSONString(response));
            result.put("success",true);
            result.put("data",JSONObject.toJSONString(response.getDeviceInfo()));
            return result;
        } else {
            LogUtil.error("查询设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
            result.put("success",false);
            result.put("msg","查询失败，请重试");
            return  result;
        }
    }

    public static JSONObject sendDataToDevice(String accessKey,String accessSecret,String productKey,String deviceName,String data) throws Exception{
        JSONObject result = new JSONObject();
        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Iot", "iot.cn-shanghai.aliyuncs.com");
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, accessSecret);
        DefaultAcsClient client = new DefaultAcsClient(profile); //初始化SDK客户端

        RRpcRequest rrpcRequest = new RRpcRequest();
        rrpcRequest.setProductKey(productKey); //设备所属产品的Key
        rrpcRequest.setDeviceName(deviceName); //设备名称
        rrpcRequest.setRequestBase64Byte(Base64Util.encode(data.getBytes())); //发给设备的数据，要求二进制数据做一次Base64编码
        rrpcRequest.setTimeout(1000); //超时时间，单位毫秒，如果超过这个时间设备没反应则返回"TIMEOUT"
        RRpcResponse rrpcResponse = client.getAcsResponse(rrpcRequest); //得到设备返回的数据信息
       // System.out.println(rrpcResponse.getPayloadBase64Byte()); //得到的数据是设备返回二进制数据然后再经过Base64编码之后的字符串，需要转换一下才能拿到原始的二进制数据
        LogUtil.print("rpcCode:"+rrpcResponse.getRrpcCode()); //对应的响应码(UNKNOW/SUCCESS/TIMEOUT/OFFLINE/HALFCONN等)
        String rpcCode = rrpcResponse.getRrpcCode();
        if("SUCCESS".equalsIgnoreCase(rpcCode)){
            String response = new String(Base64Util.decode(rrpcResponse.getPayloadBase64Byte()));
            LogUtil.print("发送数据收到回复："+response);
            result.put("success",true);
            result.put("data",response);
            return result;
        }else{
            result.put("success",false);
            result.put("msg",rpcCode);
            LogUtil.print(result.toJSONString());
            return result;
        }

    }
}
