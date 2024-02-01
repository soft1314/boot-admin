package com.qiyuan.gateway4096.filter;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.resource.MultiResource;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.*;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对访问参数进行预读，处理等
 */
@Slf4j
@Component
public class ReadAndChangeResFilter implements GlobalFilter, Ordered {

    private static final String CONTENT_DISPOSITION_TEMPLATE = "Content-Disposition: form-data; name=\"{}\"\r\n\r\n";
    private static final String CONTENT_DISPOSITION_FILE_TEMPLATE = "Content-Disposition: form-data; name=\"{}\"; filename=\"{}\"\r\n";

    private static final String CONTENT_TYPE_MULTIPART_PREFIX = ContentType.MULTIPART.getValue() + "; boundary=";
    private static final String CONTENT_TYPE_FILE_TEMPLATE = "Content-Type: {}\r\n\r\n";

    private final static String TOKEN_USERINFO_CACHE_FLAG = "TI-%s";
    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)   {
        Mono<Void> mono = chain.filter(exchange);
        ServerHttpRequest request = exchange.getRequest();
        MediaType contentType = request.getHeaders().getContentType();

        if (Objects.nonNull(contentType) && Objects.nonNull(exchange.getRequest().getMethod())
                && exchange.getRequest().getMethod().equals(HttpMethod.POST)) {
            if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
                // json 请求体处理
                mono = this.transferBody(exchange, chain);
            }else if(MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)){
                // multipart/form-data处理
                mono = this.fileRequest(contentType,exchange,chain);
            }else if(MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType)){
                // x-www-form-urlencoded 格式处理
                mono = this.xwFromBody(exchange,chain);
            }
        }else{
            if(exchange.getRequest().getMethod().equals(HttpMethod.GET)){
                Map<String, String> queryParams = exchange.getRequest().getQueryParams().toSingleValueMap();
            }
        }
        return mono;
    }
    /**
     * 修改form参数
     * @param contentType
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> fileRequest(MediaType contentType,ServerWebExchange exchange, GatewayFilterChain chain){
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    String oldBody = addPara(contentType.toString(), new String(bytes));

                    byte[] bytes1 = oldBody.getBytes();
                    byte[] bytes2 = byteMerger(Arrays.copyOf(bytes,bytes.length-4), bytes1);
                    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes2);
                        DataBufferUtils.retain(buffer);
                        return Mono.just(buffer);
                    });
                    ServerHttpRequestDecorator mutatedRequest = newDecorator(exchange,bytes2.length,cachedFlux);
                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return ServerRequest.create(mutatedExchange, MESSAGE_READERS)
                            .bodyToMono(byte[].class)
                            .then(chain.filter(mutatedExchange));
                });
    }
    public static byte[] byteMerger(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /**
     * 修改 x-www-form-urlencoded参数
     * @param body
     * @param holder
     * @param outputMessage
     */
    private void changeParamByXwForm(Flux<DataBuffer> body, DataBufferHolder holder, MyCachedBodyOutputMessage outputMessage){
        body.subscribe(dataBuffer -> {
            int len = dataBuffer.readableByteCount();
            holder.length = len;
            byte[] bytes = new byte[len];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            String oldBody = new String(bytes, StandardCharsets.UTF_8);
            oldBody+="&test=修改数据";
            DataBuffer data = outputMessage.bufferFactory().allocateBuffer();
            data.write(oldBody.getBytes(StandardCharsets.UTF_8));
            holder.length = data.readableByteCount();
            holder.dataBuffer=data;
        });
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * 修改 x-www-form-urlencoded参数
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> xwFromBody(ServerWebExchange exchange, GatewayFilterChain chain){
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(Mono::just);
        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        MyCachedBodyOutputMessage outputMessage = new MyCachedBodyOutputMessage(exchange, headers);
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
        requestBuilder.headers(k -> k.remove("Content-length"));

        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    Flux<DataBuffer> body = outputMessage.getBody();
                    DataBufferHolder holder = new DataBufferHolder();
                    changeParamByXwForm(body,holder,outputMessage);
                    ServerHttpRequestDecorator decorator = newDecorator(exchange,holder.length, Flux.just(holder.dataBuffer));
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
    }

    /**
     * 修改Json Body参数
     */
    private Mono<Void> transferBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url=exchange.getRequest().getPath().toString();
        if("/api/system/free/user/login".equals(url)){
            System.out.println(url);
        }
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(Mono::just);

        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());

        headers.remove(HttpHeaders.CONTENT_LENGTH);
        MyCachedBodyOutputMessage outputMessage = new MyCachedBodyOutputMessage(exchange, headers);
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
        requestBuilder.headers(k -> k.remove("Content-length"));

        Mono mono = bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    //解决body内数据过长读取不完整的问题
                    Flux<DataBuffer> body = outputMessage.getBody();
                    DataBufferHolder holder = new DataBufferHolder();
                    try{
                        body.subscribe(dataBuffer -> {
                            int len = dataBuffer.readableByteCount();
                            holder.length = len;
                            byte[] bytes = new byte[len];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);
                            String oldBody = new String(bytes, StandardCharsets.UTF_8);
                            JsonNode jsonNode = readNode(oldBody);
                            DataBuffer data = outputMessage.bufferFactory().allocateBuffer();
                            data.write(jsonNode.toString().getBytes(StandardCharsets.UTF_8));
                            holder.length = data.readableByteCount();
                            holder.dataBuffer=data;
                        });
                    }catch (Exception e){
                        return handleFailedRequest(exchange, JSONObject.toJSONString(e.getMessage()));
                    }
                    ServerHttpRequestDecorator decorator = newDecorator(exchange,holder.length, Flux.just(holder.dataBuffer));
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
        return mono;
    }

    private Mono<Void> handleFailedRequest(ServerWebExchange exchange, String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    private JsonNode readNode(String in) {
        try {
            return objectMapper.readTree(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private class DataBufferHolder {
        DataBuffer dataBuffer;
        int length;
    }

    /**
     * 修改form参数
     * @param contentType 请求类型
     * @param bodyString  请求body信息
     */
    @SneakyThrows
    public static String addPara(String contentType, String bodyString) {
        StringBuffer stringBuffer = new StringBuffer();

        String boundary = contentType.substring(contentType.lastIndexOf("boundary=") + 9);//获取随机字符传信息
        String boundary_end = StrUtil.format("--{}--\r\n", boundary);
        Map<String, Object> formMap = Maps.newHashMap();
        /**
         *
         * 根据自己需求进行对bodyString信息修改，例如下面，根据boundary对传入的bodyString进行了分割
         *  String[] split = bodyString.split(boundary);
         *  然后将修改完后的信息封装到formMap中，需要注意的是，file文件需要以new FileResource(file, fileName)的形式当作value放到formMap中
         */
        String part = "^\r\nContent-Disposition: form-data; name=\"([^/?]+)\"\r\n\r\n([^/?]+)\r\n--?$";
        Pattern r = Pattern.compile(part);
        String[] split = bodyString.split(boundary);
        for(int x=1;x<split.length-1;x++){
            Matcher m = r.matcher(split[x]);
            if(m.find()){
                String name = m.group(1);
                String value = m.group(2);
                System.out.println("name:"+name+" value:"+value);
//				formMap.put(name,value);
            }
        }

        formMap.put("ft",11111);
        formMap.put("tcu",22222);
        Integer count =0;
        for (Map.Entry<String, Object> entry : formMap.entrySet()) {
            stringBuffer.append(appendPart(boundary, entry.getKey(), entry.getValue(),count));
            count++;
        }
        stringBuffer.append(boundary_end);//拼接结束信息

        return stringBuffer.toString();
    }

    public ServerHttpRequestDecorator newDecorator(ServerWebExchange exchange,long dataLength,Flux<DataBuffer> body){
        return new ServerHttpRequestDecorator(
                exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                //数据长度变了以后 需要修改header里的数据，不然接收数据时会异常
                //我看别人说删除会自动补充数据长度，但我这个版本不太行
//				long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
//				if (contentLength > 0) {
                httpHeaders.setContentLength(dataLength);
//				} else {
//					httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
//				}
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return body;
            }
        };
    }


    /**
     * 添加Multipart表单的数据项
     *
     * @param boundary      随机串信息
     * @param formFieldName 表单名
     * @param value         值，可以是普通值、资源（如文件等）
     */
    private static String appendPart(String boundary, String formFieldName, Object value,Integer count) throws IORuntimeException {
        StringBuffer stringBuffer = new StringBuffer();
        // 多资源
        if (value instanceof MultiResource) {
            for (Resource subResource : (MultiResource) value) {
                appendPart(boundary, formFieldName, subResource,count);
            }
            return stringBuffer.toString();
        }

        if(count!=0){
            stringBuffer.append("--").append(boundary).append(StrUtil.CRLF);
        }else{
            stringBuffer.append(StrUtil.CRLF);
//			stringBuffer.append(boundary).append(StrUtil.CRLF);
        }

        if (value instanceof Resource) {
            // 文件资源（二进制资源）
            final Resource resource = (Resource) value;
            final String fileName = resource.getName();
            stringBuffer.append(StrUtil.format(CONTENT_DISPOSITION_FILE_TEMPLATE, formFieldName, ObjectUtil.defaultIfNull(fileName, formFieldName)));
            // 根据name的扩展名指定互联网媒体类型，默认二进制流数据
            stringBuffer.append(StrUtil.format(CONTENT_TYPE_FILE_TEMPLATE, HttpUtil.getMimeType(fileName, "application/octet-stream")));
        } else {
            // 普通数据
            stringBuffer.append(StrUtil.format(CONTENT_DISPOSITION_TEMPLATE, formFieldName)).append(value);
        }
        stringBuffer.append(StrUtil.CRLF);
        return stringBuffer.toString();
    }

    /**
     * 内部类
     */
    private class MyCachedBodyOutputMessage implements ReactiveHttpOutputMessage {
        private final DataBufferFactory bufferFactory;
        DataBuffer dataBuffer;
        private final HttpHeaders httpHeaders;
        private Flux<DataBuffer> body; // = Flux.error(new IllegalStateException("The body is not set. Did handling complete with success?"));

        public MyCachedBodyOutputMessage(ServerWebExchange exchange, HttpHeaders httpHeaders) {
            this.bufferFactory = exchange.getResponse().bufferFactory();
            this.httpHeaders = httpHeaders;
            //下面是我加的
            this.body = exchange.getRequest().getBody();
        }

        public void setBody(Flux<DataBuffer> body) {
            this.body = body;
        }

        @Override
        public void beforeCommit(Supplier<? extends Mono<Void>> action) {
        }
        @Override
        public boolean isCommitted() {
            return false;
        }
        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }
        @Override
        public DataBufferFactory bufferFactory() {
            return this.bufferFactory;
        }

        public Flux<DataBuffer> getBody() {
            return this.body;
        }
        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            this.body = Flux.from(body);
            return Mono.empty();
        }

        @Override
        public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return this.writeWith(Flux.from(body).flatMap((p) -> {
                return p;
            }));
        }
        @Override
        public Mono<Void> setComplete() {
            return this.writeWith(Flux.empty());
        }
    }
}

