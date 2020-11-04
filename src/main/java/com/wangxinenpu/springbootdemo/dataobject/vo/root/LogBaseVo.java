package com.wangxinenpu.springbootdemo.dataobject.vo.root;

import star.vo.BaseVo;

import java.util.ArrayList;
import java.util.List;

public class LogBaseVo extends BaseVo {
    private static final long serialVersionUID = 1L;
    protected Long opseno;
    protected String databaseName;
    protected Long prseno;
    protected Long functionId;
    protected String projid;
    protected boolean rollback = true;
    protected String partitionField;
    protected String pfieldValue;
    protected List<Object> list = new ArrayList();

    public LogBaseVo() {
    }

    public LogBaseVo(String databaseName, Long opseno, int orderNum, Long prseno) {
        this.opseno = opseno;
        this.databaseName = databaseName;
        this.prseno = prseno;
    }

    public void putLogBaseVo(String databaseName, Long opseno, int orderNum, Long prseno) {
        this.opseno = opseno;
        this.databaseName = databaseName;
        this.prseno = prseno;
    }

    public void putLogBaseVo(String databaseName, Long opseno, Long prseno) {
        this.opseno = opseno;
        this.databaseName = databaseName;
        this.prseno = prseno;
    }

    public void putLogBaseVo(String databaseName, Long opseno) {
        this.opseno = opseno;
        this.databaseName = databaseName;
    }

    public void putLogBaseVo(String databaseName, Long prseno, int orderNum) {
        this.databaseName = databaseName;
        this.prseno = prseno;
    }

    public Long getOpseno() {
        return this.opseno;
    }

    public void setOpseno(Long opseno) {
        this.opseno = opseno;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Long getPrseno() {
        return this.prseno;
    }

    public void setPrseno(Long prseno) {
        this.prseno = prseno;
    }

    public List<Object> getList() {
        return this.list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Long getFunctionId() {
        return this.functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getProjid() {
        return this.projid;
    }

    public void setProjid(String projid) {
        this.projid = projid;
    }

    public boolean getRollback() {
        return this.rollback;
    }

    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }

    public String getPartitionField() {
        return this.partitionField;
    }

    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }

    public String getPfieldValue() {
        return this.pfieldValue;
    }

    public void setPfieldValue(String pfieldValue) {
        this.pfieldValue = pfieldValue;
    }
}
