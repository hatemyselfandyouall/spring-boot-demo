//package com.wangxinenpu.springbootdemo.util.rocketmq;
//
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//
//public class MQProduceUtil {
//
//    public static void main(String[] args) throws Exception{
//        produce("please_rename_unique_group_name","TopicTest","10.85.159.202:5432");
//    }
//    public static void produce(String groupName,String topicName,String nameAddr) throws Exception{
//        /*
//         * Instantiate with a producer group name.
//         */
//        DefaultMQProducer producer = new DefaultMQProducer(groupName);
//
//        /*
//         * Specify name server addresses.
//         * <p/>
//         *
//         * Alternatively, you may specify name server addresses via exporting environmental variable: NAMESRV_ADDR
//         * <pre>
//         * {@code
//         * producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
//         * }
//         * </pre>
//         */
//        producer.setNamesrvAddr(nameAddr);
//
//        /*
//         * Launch the instance.
//         */
//        producer.start();
//
//        for (int i = 0; i < 1000; i++) {
//            try {
//
//                /*
//                 * Create a message instance, specifying topic, tag and message body.
//                 */
//                Message msg = new Message(topicName /* Topic */,
//                        "TagA" /* Tag */,
//                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
//                );
//
//                /*
//                 * Call send message to deliver message to one of brokers.
//                 */
//                SendResult sendResult = producer.send(msg);
//
//                System.out.printf("%s%n", sendResult);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Thread.sleep(1000);
//            }
//        }
//
//        /*
//         * Shut down once the producer instance is not longer in use.
//         */
//        producer.shutdown();
//    }
//}
