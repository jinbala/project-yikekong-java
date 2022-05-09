package com.yikekong.es;

import com.yikekong.dto.DeviceDTO;
import com.yikekong.util.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ESRepository {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //添加设备导入到es中去
    @SneakyThrows
    public void  addDevices(DeviceDTO deviceDTO){
        if (deviceDTO == null) {return;}
        if (deviceDTO.getDeviceId() == null){
            return;
        }
        IndexRequest request=new IndexRequest("devices");
        String json = JsonUtil.serialize(deviceDTO);
        Map map = JsonUtil.getByJson(json, Map.class);
        request.source(map);
        request.id(deviceDTO.getDeviceId());
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }
}
