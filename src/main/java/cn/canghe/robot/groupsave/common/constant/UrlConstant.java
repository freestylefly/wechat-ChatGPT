package cn.canghe.robot.groupsave.common.constant;

/**
 * @author bin
 * @date 2020/08/24 15:43:29
 * @description URL相关
 * @menu
 */
public class UrlConstant {

    /**
     * 基本域名
     */
    public static final String WK_BASE_URL = "http://39.106.205.87:7415";

    /**
     * 登录平台
     */
    public static final String WK_LOGIN_URL = WK_BASE_URL + "/member/login";

    /**
     * 获取微信二维码
     */
    public static final String WK_IPADE_URL = WK_BASE_URL + "/iPadLogin";

    /**
     * 首次登录微信
     */
    public static final String WK_GET_IPADLOGININFO_URL = WK_BASE_URL + "/getIPadLoginInfo";

    /**
     * 非首次登录微信
     */
    public static final String WK_SECONDLOGIN_URL = WK_BASE_URL + "/secondLogin";

    /**
     * 初始化好友列表
     */
    public static final String WK_INITIALIZEFRIENDS_URL = WK_BASE_URL + "/initializeFriends";

    /**
     * 获取好友列表
     */
    public static final String WK_GETFRIENDS_URL = WK_BASE_URL + "/getFriends";

    /**
     * 获取好友详情
     */
    public static final String WK_GETCONTACT_URL = WK_BASE_URL + "/getContact";

    /**
     * 初始化群列表
     */
    public static final String WK_INITCHATROOM_URL = WK_BASE_URL + "/initChatRoom";

    /**
     * 获取群列表
     */
    public static final String WK_GETCHATROOMS_URL = WK_BASE_URL + "/getChatRooms";

    /**
     * 获取群详细信息
     */
    public static final String WK_GETCHATROOMINFO_URL = WK_BASE_URL + "/getChatRoomInfo";

    /**
     * 获取群成员列表
     */
    public static final String WK_GETCHATROOMMEMBER_URL = WK_BASE_URL + "/getChatRoomMember";

    /**
     * 获取群成员详情
     */
    public static final String WK_GETCHATROOMMEMBERINFO_URL = WK_BASE_URL + "/getChatRoomMemberInfo";

    /**
     * 退出微信登录
     */
    public static final String WK_LOGOUT_URL = WK_BASE_URL + "/logout";

    /**
     * 设置回调服务地址
     */
    public static final String WK_SET_CALLBACK_URL = WK_BASE_URL + "/setHttpCallbackUrl";



    /**
     * 发送文本消息
     */
    public static final String WK_SEND_TEXT_URL = WK_BASE_URL + "/sendText";


    /**
     * 获取好友的朋友圈
     */
    public static final String WK_GETFRIENDCIRCLE_URL = WK_BASE_URL + "/getFriendCircle";

    /**
     * 获取自己的朋友圈
     */
    public static final String WK_GETCIRCLE_URL = WK_BASE_URL + "/getCircle";

    /**
     * 朋友圈点赞
     */
    public static final String WK_SNS_PRAISE_URL = WK_BASE_URL + "/snsPraise";


    /**
     * 检测僵尸粉
     */
    public static final String WK_CHECK_ZOMBIE_URL = WK_BASE_URL + "/checkZombie";

    /**
     * 获取好友标签列表
     */
    public static final String WK_GETCONTACT_LABELLIST_URL = WK_BASE_URL + "/getContactLabelList";


    /**
     * 获取好友某个标签的好友列表
     */
    public static final String WK_GETLABEL_CONTACTS_URL = WK_BASE_URL + "/getLabelContacts";

    /**
     * 搜索好友
     */
    public static final String WK_SEARCHUSER_URL = WK_BASE_URL + "/searchUser";

    /**
     * 添加好友
     */
    public static final String WK_ADDUSER_URL = WK_BASE_URL + "/addUser";

    /**
     * 邀请群成员
     */
    public static final String WK_INVITECHATROOMMEMBER_URL = WK_BASE_URL + "/inviteChatRoomMember";

    /**
     * 同意添加好友
     */
    public static final String WK_ACCEPTUSER_URL = WK_BASE_URL + "/acceptUser";


}