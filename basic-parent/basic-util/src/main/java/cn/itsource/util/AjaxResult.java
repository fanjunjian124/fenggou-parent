package cn.itsource.util;

public class AjaxResult {
    private Boolean success=true;
    private String message;
    private String errorCode;
    private Object data;

    public AjaxResult() {
    }

    public Boolean getSuccess() {
        return success;
    }

    /**
     * 将set返回值设置成AjaxResult可以进行链式操作
     * @param success
     * @return
     */
    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public AjaxResult setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Object getData() {
        return data;
    }

    public AjaxResult setData(Object data) {
        this.data = data;
        return this;
    }

    public static AjaxResult me(){
        return new AjaxResult();
    }
}
