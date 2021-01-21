package com.wangxinenpu.springbootdemo.config.webservice;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

@Slf4j
public class CDATAInInterceptor extends AbstractPhaseInterceptor<Message> {

    public CDATAInInterceptor() {
        //这里代表流关闭之前的阶段，这很重要！可以到官网去看，拦截的阶段分为很多种
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) {

        /*这里是通过注解的方式来加<!CDATA[[]]>的格式
         * message.put("disable.outputstream.optimization", Boolean.TRUE);
         * XMLStreamWriter writer =
         * StaxUtils.createXMLStreamWriter(message.getContent(OutputStream.class
         * )); message.setContent(XMLStreamWriter.class, new
         * CDATAXMLStreamWriter(writer));
         */
        try {
            log.info("感知到接口被调用");
            InputStream is = message.getContent(InputStream.class);
            //这里可以对流做处理，从流中读取数据，然后修改为自己想要的数据

            //处理完毕，写回到message中
            //在这里需要注意一点的是，如果修改后的soap消息格式不符合webservice框架格式，比如：框架封装后的格式为
            //<soap:Envelope xmlns:soap="http://www.w3.org/2001/12/soap-envelope" <soap:Body>
            //<这里是调用服务端方法的命名空间><这是参数名称>
            //这里才是真正的消息
            //</这里是调用服务端方法的命名空间></这是参数名称>
            //</soap:Body>
            //</soap:Envelope>
            //如果是以上这种格式，在暴露的接口方法里才会真正接收到消息，而如果请求中在body里边，没有加方法命名空间和参数名称，直接就是消息体
            //那接口方法里是接收不到消息的，因为cxf是按照上面的这种格式去解析的，所以如果不符合上面的格式，就应该在这里做处理
            //……………………处理代码……………………
            Exchange exchange = message.getExchange();
            String address=exchange.getEndpoint().getEndpointInfo().getAddress();
            String result = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            log.info("被调用的接口为"+address+"记录到接口入参"+result);
//            result=result.replaceFirst("<!\\[CDATA.*\\?>","").replaceFirst("]]>","");
            is = new ByteArrayInputStream(result.getBytes());
            if(is != null)
                message.setContent(InputStream.class, is);
        } catch (Exception e) {
            log.error("Error when split original inputStream. CausedBy : "+"\n"+e);
        }
    }

    private class CachedStream extends CachedOutputStream {

        public CachedStream() {

            super();

        }

        protected void doFlush() throws IOException {

            currentStream.flush();

        }

        protected void doClose() throws IOException {

        }

        protected void onWrite() throws IOException {

        }

    }
}
