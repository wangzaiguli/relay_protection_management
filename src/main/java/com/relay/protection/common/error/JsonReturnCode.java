package com.relay.protection.common.error;

/**
 * 描述: json格式数据返回码
 * <ul>
 * <li>100 : 用户未登录 </li>
 * <li>200 : 成功 </li>
 * <li>300 : 失败 </li>
 * </ul>
 *
 * @author zhuHx
 */
public enum JsonReturnCode {

    /**
     * 所有的错误信息码
     */
    BIZ_SUCCESS("0", "执行成功"),
    BIZ_FAIL("0001", "执行失败"),
    IO_ERROR("4005", "IO错误"),
    SYSTEM_ERROR("4999", "系统异常,请尽快联系技术人员"),

    /**
     * 2
     * 校验
     */
    VALID_ERROR("2000", "参数校验失败"),
    DATE_FORMTER_ERROR("2001", "日期格式化异常"),
    PASSWORD_ERROR("2002", "密码错误，请重试"),
    PASSWORD_NEW_ERROR("2003", "新密码不同，请重试"),
    PASSWORD_NEW_EXIST_ERROR("2004", "密码已存在，请重试"),
    NAME_EXIST_ERROR("2005", "用户名已存在"),
    DEPT_PARENT_ERROR("2006", "部门的上级部门不存在"),
    DEPT_EXIST_ERROR("2007", "部门已经存在"),
    DEPT_HAVE_USER_ERROR("2008", "部门仍然存在人员，不可删除"),
    FLOW_NOT_EXIST_ERROR("2009", "流程不存在"),
    DIR_PARENT_ERROR("2006", "目录的上级目录不存在"),
    DIR_EXIST_ERROR("2007", "名称已经存在"),
    DIR_HAVE_DATA_ERROR("2008", "目录仍然存在文件，不可删除"),
    DIR_NOT_DEL_ROOT("2011", "根节点不可删除"),
    FLOW_CONFIG_ERROR("2012", "流程配置错误"),
    DIR_NOT_FILE("2013", "此数据不是文件"),

    /**
     * 3
     * 数据
     */
    SELECT_TYPE_ERROR("3000", "下拉框类型不存在"),
    DATA_ERROR("3001", "数据不存在"),

    /**
     * 4
     * 文件
     */
    FILE_DOWNLOAD_ERROR("4004", "上传下载失败"),
    FILE_UPLOAD_ERROR("4002", "上传文件失败"),
    FILE_NOT_EXIST_ERROR("4003", "文件不不存在"),


    /**
     * 权限类错误
     * 5开头
     */
    SESSION_EXPIRED("5000", "登录已过期，请重新登录"),
    USET_ERR("5001", "用户名和密码错误，请重试"),
    NO_AUTH_ERR("5002", "您无此权限"),
    NO_LOGIN_ERR("5003", "没有登录或者登录超时，请重新登录。"),
    TOKEN_GET_ERR("5004", "token获取异常"),
    TOKEN_ERR("5005", "token解析异常"),

    /**
     * 数据错误
     * 6开头
     */
    DATA_NOT_EXIST("3000", "数据不存在"),
    TOKEN_NOT_EXIST("3001", "token不存在"),
    SEND_ERR("3002", "发送失败"),

    /**
     * 文件类错误
     * 7开头
     */
    BIZ_PDF_TEMPLATE_FILE_NOT_FOUND("7003", "PDF模板文件未找到"),
    BIZ_PDF_JRE_EXCEPTION("7004", "PDF插件报错"),
    BIZ_PDF_CREATE_ERROR("7005", "PDF生成出错"),
    BIZ_HTML_CREATE_ERROR("7006", "HTML生成出错"),
    BIZ_XML_CREATE_ERROR("7007", "生成申报文件异常"),
    BIZ_XML_JIEXI_ERROR("7008", "解析xml文件报错"),
    BIZ_FILE_NOT_FOUND("7009", "压缩包中未找到文件#{msg}"),
    BIZ_YM_ERR("7010", "数据中未找到申报年月"),
    BIZ_PC_ERR("7011", "数据中未找到申报批次"),
    BIZ_COMPANY_ERR("7012", "文件包中公司id与登录id不同"),
    BIZ_JM_ERR("7013", "解密异常，请选择符合规范的申报文件"),
    BIZ_JSON_JIEXI_ERROR("7014", "JSON 解析错误"),
    BIZ_FILE_CONFIG_ERROR("7101", "excel文件配置错误"),
    BIZ_FILE_FIELD_SIZE_NOT_MATCH("7102", "excel文件配置字段错误"),
    BIZ_FILE_READ("7103", "excel文件读取错误"),
    BIZ_FILE_NULL("7104", "excel文件内容为空"),
    FILE_EXCEL_CREAT("7003", "excel创建错误:%s"),
    FILE_EXCEL_GET("7003", "excel获取错误"),
    FILE_EXCEL_READ("7003", "excel获取错误"),
    FILE_SHEET_CREAT("7003", "sheet获取错误"),
    FILE_EXCEL_TYPE("7003", "不存在的excel类型:%s"),
    FILE_NOTE_CREAT("7003", "读取文件内容操作出错"),

    BIZ_OTHER_ERROR("9900", "其他操作失败！");


    private String code;
    private String msg;

    JsonReturnCode(String code, String desc) {
        this.code = code;
        this.msg = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
