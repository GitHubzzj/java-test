package com.byedbl.kafka.controller;

import com.byedbl.kafka.producer.KafkaProducerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("producer")
public class KafkaController {

    @Autowired
    private KafkaProducerServer kafkaProducerServer;

    @RequestMapping("/send")
    @ResponseBody
    public Map<String, Object> send(@RequestParam("topic") String topic,
                                                  @RequestParam("value") String value,
                                                  @RequestParam("ifPartition") String ifPartition,
                                                  @RequestParam("partitionNum") Integer partitionNum,
                                                  @RequestParam("role") String role) {
        return kafkaProducerServer.sendMesForTemplate(topic, value, ifPartition, partitionNum, role);
    }

    @RequestMapping("/sends")
    @ResponseBody
    public void sends(@RequestParam("topic") String topic,
                                                  @RequestParam("value") String value,
                                                  @RequestParam("ifPartition") String ifPartition,
                                                  @RequestParam("partitionNum") Integer partitionNum,
                                                  @RequestParam("role") String role) {
        for(int i = 0;i<100;i++) {
            kafkaProducerServer.sendMesForTemplate(topic, value+i, ifPartition, partitionNum, role);
        }
    }



}
