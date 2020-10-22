package com.hust.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
//import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import com.hust.annotation.ApiVersionDesc;
import com.hust.annotation.TDResult;
import com.hust.accountcommon.entity.dto.TokenDataDto;
import com.hust.util.ErrorCodeEnum;
import com.hust.accountcommon.util.LogUtil;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.apitemplate.BasicOutput;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.apitemplate.TDResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Lyh
 * @Title: ResultAspect
 * @Description: 自定义注解实现
 * @date 2018/9/13 17:00
 */
@Aspect
@Component
public class ResultAspect {

    public static int API_PARSER_FEATURE;
    static {
        int features = 0;
        features |= Feature.AutoCloseSource.getMask();
        features |= Feature.InternFieldNames.getMask();
        features |= Feature.UseBigDecimal.getMask();
        features |= Feature.AllowUnQuotedFieldNames.getMask();
        features |= Feature.AllowSingleQuotes.getMask();
        features |= Feature.AllowArbitraryCommas.getMask();
        features |= Feature.SortFeidFastMatch.getMask();
        API_PARSER_FEATURE = features;
    }


    @Around("@annotation(tdResult)")
    public TDResponse<Object> processTx(ProceedingJoinPoint joinPoint, TDResult tdResult) throws Throwable {
        TDResponse<Object> tdResponse = new TDResponse<>();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) attributes.getRequest();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(PublicUtil.getDefaultBasicInput());
        Object[] args = joinPoint.getArgs();
        String logModelName =  "tdResult";
        if (args != null && args.length > 0 && (args[0] instanceof TDRequest)) {
            TDRequest<Object> tdRequest = (TDRequest<Object>) args[0];
            basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
            logModelName = LogUtil.getLogModelName(tdRequest, "tdResult");


//            String res = JSON.toJSONString(tdRequest);
//            tdRequest = JSON.parseObject(res, new TypeReference<TdRequest<String>>(){});
            LogUtil.info("方法为：" + request.getRequestURI() + ";参数json为：" + JSON.toJSONString(tdRequest), logModelName);
            basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());

            //解析token用户权限,
            if (tdResult.isNeedCheckToken() && PublicUtil.isNotEmpty(tdResult.authorityCode())) {
                if (PublicUtil.isEmpty(tdRequest.getTokenDataDto())) {
                    basicOutput.setCode(ErrorCodeEnum.TD1011.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD1011.msg());
                    tdResponse.setBasic(basicOutput);
                    LogUtil.error("TokenData为空", logModelName);
                    return tdResponse;
                } else {
                    TokenDataDto tokenDataDto = tdRequest.getTokenDataDto();
                    //权限列表
                    List<String> authCodes = tokenDataDto.getAuthority();
                    boolean isExist = authCodes.contains(tdResult.authorityCode());
                    if (!isExist) {
                        basicOutput.setCode(ErrorCodeEnum.TD5000.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD5000.msg());
                        tdResponse.setBasic(basicOutput);
                        return tdResponse;
                    }
                }
            }

            //判断API版本
            if(tdResult.isNeedCheckApiVersion()) {
                String apiVer = tdRequest.getBasic().getVer();
                //解析API版本
                Object controller = joinPoint.getThis();
                Class controllerClass = joinPoint.getTarget().getClass();
                String curMethodName = joinPoint.getSignature().getName();
                //根据协议报文中的版本号，调用对应的版本方法
                String versionMethodName = curMethodName + "_v" + apiVer.replace(".", "_");
                LogUtil.info(String.format("方法版本为：%s, 执行方法：%s", apiVer ,versionMethodName), logModelName);
                try {
                    callVersionMethod(controller, controllerClass, versionMethodName, tdRequest, tdResponse, basicOutput);
                }
                catch (NoSuchMethodException ex){
                    LogUtil.error(String.format("API[%s]没有对应版本的方法", request.getRequestURI()), logModelName);
                    basicOutput.setCode(ErrorCodeEnum.TD6002.code());
                    basicOutput.setMsg("没有对应版本的API：" + request.getRequestURI() + ",版本为：" + apiVer);
                    tdResponse.setBasic(basicOutput);
                }
                catch (JSONException ex) {
                    String message = String.format("API[%s]入参的data数据格式错误,版本为：%s", request.getRequestURI(), apiVer);
                    LogUtil.error(message, logModelName);
                    basicOutput.setCode(ErrorCodeEnum.TD6003.code());
                    basicOutput.setMsg(message);
                    tdResponse.setBasic(basicOutput);
                }
                return tdResponse;
            }


            //判断是否参数校验问题
//            if (PublicUtil.isNotEmpty(tdRequest.getData())) {
//                Map<String, Object> dataParam = JSON.parseObject(tdRequest.getData(), new TypeReference<Map<String, Object>>(){});
//                if (dataParam.get("checkResults") != null) {
//                    String arrCheckResults = String.valueOf(dataParam.get("checkResults"));
//                    List<String> checkResults = JSON.parseObject(arrCheckResults, new TypeReference<List<String>>(){});
//                    if (PublicUtil.isNotEmpty(checkResults)) {
//                        basicOutput.setCode(ErrorCodeEnum.TD1011.code());
//                        basicOutput.setMsg(checkResults.toString());
//                        tdResponse.setBasic(basicOutput);
//                        LogUtil.error("参数校验不通过" + checkResults, "tdResult");
//                        return tdResponse;
//                    }
//                }
//            }
        }

        try {
            TDResponse<Object> result = (TDResponse<Object>) joinPoint.proceed();
            LogUtil.info("方法为：" + request.getRequestURI() + ";返回结果json为：" + JSON.toJSONString(result), logModelName);
            if (PublicUtil.isNotEmpty(result.getBasic()) && PublicUtil.isNotEmpty(result.getBasic().getCode())) {
                basicOutput = result.getBasic();
            }
            tdResponse.setData(result.getData());
            tdResponse.setBasic(basicOutput);
        } catch (Exception e) {
            LogUtil.error("返回结果转换异常", logModelName, e);
            basicOutput.setCode(ErrorCodeEnum.TD6000.code());
            basicOutput.setMsg(ErrorCodeEnum.TD6000.msg());
            tdResponse.setBasic(basicOutput);
        }
        return tdResponse;
    }

    /**
     * 调用版本方法
     * @param controller
     * @param controllerClass
     * @param versionMethodName
     * @param tdRequest
     * @param tdResponse
     * @param basicOutput
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void callVersionMethod(Object controller, Class controllerClass, String versionMethodName, TDRequest tdRequest, TDResponse tdResponse, BasicOutput basicOutput)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JSONException {
        Method methodVersion = controllerClass.getDeclaredMethod(versionMethodName, TDRequest.class);
        ApiVersionDesc apiVersionDesc = methodVersion.getAnnotation(ApiVersionDesc.class);
        if (apiVersionDesc != null) {
            Object data = JSON.parseObject(JSON.toJSONString(tdRequest.getData()), apiVersionDesc.paramClass(), ParserConfig.global, null, API_PARSER_FEATURE);
            tdRequest.setData(data);
        }
        TDResponse<Object> result = (TDResponse<Object>) methodVersion.invoke(controller, tdRequest);
        if (PublicUtil.isNotEmpty(result.getBasic()) && PublicUtil.isNotEmpty(result.getBasic().getCode())) {
            basicOutput = result.getBasic();
        }
        tdResponse.setData(result.getData());
        tdResponse.setBasic(basicOutput);

    }
}
