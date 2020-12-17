package com.wangxinenpu.springbootdemo.config.webservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

@Slf4j
public class CDATAOutInterceptor extends AbstractPhaseInterceptor<Message> {

    public CDATAOutInterceptor() {
//这里代表流关闭之前的阶段，这很重要！可以到官网去看，拦截的阶段分为很多种
        super(Phase.PRE_STREAM);
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

            OutputStream os = message.getContent(OutputStream.class);
            CachedStream cs = new CachedStream();

            message.setContent(OutputStream.class, cs);

            message.getInterceptorChain().doIntercept(message);

            CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);
            InputStream in = csnew.getInputStream();

            String xml = IOUtils.toString(in);
            log.info("记录到请求返回为" + xml);
//转换的方式
            xml = xml.replace("<body>", "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><body>")
                    .replace("<returnMessage>", "<returnMessage><![CDATA[")
                    .replace("</returnMessage>", "]]></returnMessage>").replace("<returnCode>", "<returnCode><![CDATA[")
                    .replace("</returnCode>", "]]></returnCode>");
            // 这里对xml做处理，处理完后同理，写回流中
            System.out.println("replaceAfter" + xml);
            IOUtils.copy(new ByteArrayInputStream(xml.getBytes()), os);
            cs.close();
            os.flush();
            message.setContent(OutputStream.class, os);

        } catch (Exception e) {
            log.error("Error when split original inputStream. CausedBy : " + "\n" + e);
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
