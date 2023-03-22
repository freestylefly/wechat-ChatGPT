package cn.canghe.robot.groupsave.common.util;


import cn.canghe.robot.groupsave.pojo.bo.AdminInfo;


/**
 * 上下文
 * @author canghe
 */
public class AdminSessionContext {

    private static ThreadLocal<AdminInfo> subjectContext = new ThreadLocal<>();


    public static AdminInfo get() {

        if (subjectContext.get() == null) {
            return new AdminInfo();
        } else {
            return subjectContext.get();
        }
    }

    /**
     * 清除上下文
     */
    public static void clearContext() {
        setContext(null);
    }


    public static void setContext(AdminInfo adminInfo) {
        subjectContext.set(adminInfo);
    }




    public static String getWcId() {
        return get().getWcId();
    }

    public static String getWId() {
        return get().getWId();
    }



}
