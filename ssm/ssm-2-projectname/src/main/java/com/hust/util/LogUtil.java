package com.hust.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import com.hust.util.apitemplate.TDRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class LogUtil {
    private static org.slf4j.Logger innerLogger;
    private static ch.qos.logback.classic.LoggerContext innerLoggerContext;
    private static final String FQCN = LogUtil.class.getName();
    private static Method innerMethod;
    private static int modelNameLen;

    static {
        innerLogger = LoggerFactory.getLogger(LogUtil.class);
        try {
            //ResourceBundle resource = ResourceBundle.getBundle("commoncfg");
            //modelNameLen = Integer.parseInt(resource.getString("modelNameLen"));
            modelNameLen = 10;
            innerMethod = innerLogger.getClass().getDeclaredMethod("filterAndLog_0_Or3Plus", String.class, Marker.class, Level.class, String.class, Object[].class, Throwable.class);
            innerMethod.setAccessible(true);
//            reloadcfg();
        } catch (NoSuchMethodException | SecurityException e) {
            innerLogger.error("load failed, Exception1:" + e);
        }catch (Exception e) {
            innerLogger.error("load failed, Exception2:" + e);
        }
    }

    private static void reloadcfg(){
        innerLoggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
//        String logbackConfigPath = System.getProperty("user.dir") + "/logback.xml";
//        String logbackConfigPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/logback.xml";
//        String logbackConfigPath = "E:\\logback.xml";
        String clasPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String logbackConfigPath =  clasPath.substring(clasPath.indexOf(":") + 1, clasPath.lastIndexOf("/",clasPath.indexOf(".jar"))) + "/logback.xml";        ;
        innerLogger.info("logback外部配置文件路径：" + logbackConfigPath);
        File file = new File(logbackConfigPath);
        if(!file.exists())
        {
            innerLogger.error("logbackConfigPath file is no exist -- use config in jar");
        }
        else
        {
            if(!file.isFile())
            {
                innerLogger.error("logbackConfigPath file is not a file -- use config in jar");
            }
            else
            {
                if(!file.canRead())
                {
                    innerLogger.error("logbackConfigPath file can not read -- use config in jar");
                }
                else
                {
                    JoranConfigurator joranConfigurator = new JoranConfigurator();
                    joranConfigurator.setContext(innerLoggerContext);
                    Map<String, String> propMap = innerLoggerContext.getCopyOfPropertyMap();
                    innerLoggerContext.reset();
                    for(Map.Entry<String, String> entity: propMap.entrySet()){
                        innerLoggerContext.putProperty(entity.getKey(), entity.getValue());
                    }
                    try {
                        joranConfigurator.doConfigure(file);
                    } catch (Exception e) {
                        innerLogger.error("logbackConfigPath Load logback config file error. Message: {}",e.getMessage());
                    }
                    StatusPrinter.printInCaseOfErrorsOrWarnings(innerLoggerContext);
                }
            }
        }
    }

    private static String embellish(String log, String modelName) {
        return String.format("(%" + modelNameLen + "s)%s", modelName.length() > modelNameLen ? modelName.substring(0, modelNameLen) : modelName, log);
    }

    public static void trace(String msg, String modelName) {
        innerLogMethod(FQCN, null, Level.TRACE, embellish(msg, modelName), null, null);
    }

    public static void trace(String msg, String modelName, Throwable t) {
        innerLogMethod(FQCN, null, Level.TRACE, embellish(msg, modelName), null, t);
    }

    public static void debug(String msg, String modelName) {
        innerLogMethod(FQCN, null, Level.DEBUG, embellish(msg, modelName), null, null);
    }

    public static void debug(String msg, String modelName, Throwable t) {
        innerLogMethod(FQCN, null, Level.DEBUG, embellish(msg, modelName), null, t);
    }

    public static void info(String msg, String modelName) {
        innerLogMethod(FQCN, null, Level.INFO, embellish(msg, modelName), null, null);
    }

    public static void info(String msg, String modelName, Throwable t) {
        innerLogMethod(FQCN, null, Level.INFO, embellish(msg, modelName), null, t);
    }

    public static void warn(String msg, String modelName) {
        innerLogMethod(FQCN, null, Level.WARN, embellish(msg, modelName), null, null);
    }

    public static void warn(String msg, String modelName, Throwable t) {
        innerLogMethod(FQCN, null, Level.WARN, embellish(msg, modelName), null, t);
    }

    public static void error(String msg, String modelName) {
        innerLogMethod(FQCN, null, Level.ERROR, embellish(msg, modelName), null, null);
    }

    public static void error(String msg, String modelName, Throwable t) {
        innerLogMethod(FQCN, null, Level.ERROR, embellish(msg, modelName), null, t);
    }

    private static void innerLogMethod(String localFQCN, Marker marker, Level level, String msg, Object[] params, Throwable t) {
        try {
            innerMethod.invoke(innerLogger, localFQCN, marker, level, msg, params, t);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            innerLogger.error("execute innerMethod failed, Exception:" + e);
        }
    }

    /**
     * 获取日志ModelName，如果TdRequest里携带的LogModelName不为空，返回TdRequest中的，否则返回自定义的
     * @param tdRequest
     * @param modelName
     * @return
     */
    public static String getLogModelName(TDRequest tdRequest, String modelName) {
        //String tdRequestLogModelName = tdRequest.getLogModelName();
        //String result = (tdRequestLogModelName != null && tdRequestLogModelName.length() > 0) ? tdRequestLogModelName : modelName;
        //return (result == null ? "" : result);
        return "";
    }
}
