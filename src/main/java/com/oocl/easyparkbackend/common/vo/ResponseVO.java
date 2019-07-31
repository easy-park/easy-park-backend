package com.oocl.easyparkbackend.common.vo;

import org.springframework.http.HttpStatus;

public class ResponseVO<M> {
    // 返回状态【0-成功，1-业务失败，999-表示系统异常】
    private int status;
    // 返回信息
    private String msg;
    // 返回数据实体;
    private M data;

    // 分页使用
    private int nowPage;
    private int totalPage;

    private ResponseVO() {
    }

    public static <M> ResponseVO success(M m) {
        ResponseVO<M> responseVO = new ResponseVO<>();
        responseVO.setStatus(HttpStatus.OK.value());
        responseVO.setData(m);

        return responseVO;
    }

    public static ResponseVO successToken(String token) {
        ResponseVO<String> responseVO = new ResponseVO<>();
        responseVO.setStatus(HttpStatus.OK.value());
        responseVO.setMsg("登录成功");
        responseVO.setData(token);
        return responseVO;
    }

    public static <M> ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(HttpStatus.OK.value());
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static <M> ResponseVO serviceFail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(HttpStatus.BAD_REQUEST.value());
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static <M> ResponseVO serviceFail(int status,String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(status);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static <M> ResponseVO appFail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
