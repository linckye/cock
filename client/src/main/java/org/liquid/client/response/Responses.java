package org.liquid.client.response;

/**
 * 服务响应工具类
 *
 * @author linckye 2018-05-16
 */
public class Responses {

    private static final Response<Object> successResponse = new Response<>();
    private static final String successMessage = "Success";

    private static final Response<Object> upexpectedErrorResponse = new Response<>();
    private static final String upexpectedErrorMessage = "Upexpected error";

    static {
        successResponse.message = successMessage;
        successResponse.code = CommonCode.SUCCESS.getCode();

        upexpectedErrorResponse.message = upexpectedErrorMessage;
        successResponse.code = CommonCode.UNEXPECTED_ERROR.getCode();
    }

    /**
     * 获取成功响应
     *
     * @return 成功响应
     */
    public static Response<Object> getSuccessResponse() {
        return successResponse;
    }

    /**
     * 获取未知错误响应
     *
     * @return 未知错误响应
     */
    public static Response<Object> getUpexpectedErrorResponse() {
        return upexpectedErrorResponse;
    }

    /**
     * 获取成功响应建造器
     *
     * @param <D> 数据类型
     * @return 成功响应建造器
     */
    public static <D> SuccessResponseBuilder<D> getSuccessResponseBuilder() {
        return new SuccessResponseBuilder<>();
    }

    public static class SuccessResponseBuilder<D> {

        Response<D> response = new Response<>();

        public SuccessResponseBuilder<D> setData(D data) {
            response.data = data;
            return this;
        }

        public Response<D> build() {
            response.code = CommonCode.SUCCESS.getCode();
            response.message = successMessage;
            return response;
        }

    }
}
