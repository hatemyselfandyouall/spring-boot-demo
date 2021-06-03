package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class LinkDetailSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据源url")
    @Column(name = "url")
    private String url;

    @ApiModelProperty("数据源端口")
    @Column(name = "port")
    private Integer port;

    @ApiModelProperty("数据源用户名")
    @Column(name = "username")
    private String username;

    @ApiModelProperty("数据源密码")
    @Column(name = "password")
    private String password;

    @ApiModelProperty("数据库名")
    @Column(name = "schema_name")
    private String schemaName;

    @ApiModelProperty("数据源文件key")
    @Column(name = "source_file_key")
    private String sourceFileKey;

    @ApiModelProperty("oracle服务名")
    @Column(name = "oracle_server_name")
    private String oracleServerName;

    @ApiModelProperty("api模式使用的token")
    @Column(name = "token")
    private String token;

    @ApiModelProperty("api模式请求参数")
    @Column(name = "api_request_param")
    private String apiEequestParam;

    @ApiModelProperty("oraclesid")
    @Column(name = "oracle_sid")
    private String oracleSid;

    @ApiModelProperty("1url拼接2JSON")
    @Column( name="inner_request_format")
    private Integer innerRequestFormat;

    @ApiModelProperty("1政务服务2省数据中心3自定义")
    @Column( name="inner_request_object")
    private Integer innerRequestObject;

    @ApiModelProperty("1GET2POST3POST/GET")
    @Column( name="inner_request_way")
    private Integer innerRequestWay;

    @ApiModelProperty("请求样例")
    @Column( name="request_model")
    private Integer requestModel;

    @ApiModelProperty("请求参数样例")
    @Column( name="request_param_example")
    private String requestParamExample;

    @ApiModelProperty("返回参数样例")
    @Column( name="response_param_example")
    private String responseParamExample;

    @ApiModelProperty("配置参数规则url")
    @Column( name="role_url")
    private String roleUrl;
}
