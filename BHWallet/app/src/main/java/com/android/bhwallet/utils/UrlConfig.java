package com.android.bhwallet.utils;

import com.asuka.android.asukaandroid.comm.utils.StringUtils;

/**
 * Created by egojit on 2017/1/13.
 */
public class UrlConfig {

    public static String token = "5a34012c231e56078587a68ed6eba894";


    public static String BASE_API = "http://121.36.36.231:9724";

    public static String BASE_IMAGE_URL = "http://121.36.36.231:9653";

    /**
     * 查询是否已开通
     */
    public static String VIS_ACC_QRY = BASE_API + "/api/v2_login/queryUser";
    /**
     * 获取token
     */
    public static String GET_TOKRN = BASE_API + "/api/v2_global/getToken";
    /**
     * 获取协议内容
     */
    public static String PROTOCOL = BASE_API + "/api/v1_protocol/protocol";
    /**
     * 上传图片
     */
    public static String UP_PIC = BASE_API + "/api/v2_global/upload";
    /**
     * 钱包账户开户
     */
    public static String OPEN_WALLET = BASE_API + "/api/v2_login/open_wallet";
    /**
     * 开户信息查询
     */
    public static String WALLET_OPEN_DETAIL = BASE_API + "/api/v2_login/visAcctQry";
    /**
     * 电子账户开户
     */
    public static String OPEN_ELE = BASE_API + "/api/v2_login/open_ele_new";
    /**
     * 四要素鉴权
     */
    public static String CHECK_CARD = BASE_API + "/api/v2_login/openCheck";
    /**
     * 账户余额查询
     */
    public static String WALLET_MESSAGE = BASE_API + "/api/v2_amount/get_balance";
    /**
     * 支付密码校验
     */
    public static String PASSWORD_CHECK = BASE_API + "/api/v2_personal/visPwdCheck";
    /**
     * 支付密码修改
     */
    public static String UPDATE_PWD = BASE_API + "/api/v2_personal/updatePassword";

    /**
     * 校验验证码
     */
    public static String CHECK_YZM = BASE_API + "/api/v1_visAccQry/visCheckOTP";
    /**
     * 重置密码
     */
    public static String RESET_PASSWORD = BASE_API + "/api/v2_personal/resetPassword";
    /**
     * 注销账户
     */
    public static String WRITE_OFF_ACC = BASE_API + "/api/v2_personal/removeUser";
    /**
     * 账户信息绑定
     */
    public static String BIND_MESSAGE = BASE_API + "/api/v1_visAccQry/visAcctBinding";
    /**
     * 获取职业
     */
    public static String GET_JOB_LIST = BASE_API + "/api/v2_login/getWorkType";
    /**
     * 账单
     */
    public static String BILL_LIST = BASE_API + "/api/v2_amount/get_bill";
    /**
     * 变更银行卡
     */
    public static String CHANGE_CARD = BASE_API + "/api/v2_personal/updateBank";
    /**
     * 解绑银行卡
     */
    public static String UNBIND_CARD = BASE_API + "/api/v2_personal/removeBank";
    /**
     * 开通添金宝
     */
    public static String OPEN_FUND = BASE_API + "/api/v2_fund/tjbSign";
    /**
     * 添金宝收益
     */
    public static String FUND_INCOME = BASE_API + "/api/v2_fund/tjbIncome";
    /**
     * 添金宝收益明细
     */
    public static String INCOME_LIST = BASE_API + "/api/v2_fund/tjbIncomeDetail";
    /**
     * 添金宝解约
     */
    public static String CLOSE_FUND = BASE_API + "/api/v2_fund/tjbSignCancel";
    /**
     * 添金宝净值信息查询
     */
    public static String JZ_LIST = BASE_API + "/api/v2_fund/tjbNavInfo";
    /**
     * 钱包-充值
     */
    public static String WALLET_PAY_IN = BASE_API + "/api/v2_trade/charge";
    /**
     * 钱包-提现
     */
    public static String WALLET_PAY_OUT = BASE_API + "/api/v2_trade/withdraw";
    /**
     * 电子账户发短信(鉴权)
     */
    public static String ELE_GET_CODE = BASE_API + "/api/v2_login/open_ele_check";
    /**
     * 众智存—产品列表
     */
    public static String SAVE_PRODUCT_LIST = BASE_API + "/api/v2_fund/financialList";
    /**
     * 产品详情
     */
    public static String PRODUCT_DETAIL = BASE_API + "/api/v2_fund/financialInfo";
    /**
     * 购买理财
     */
    public static String BUY_PRODUCT = BASE_API + "/api/v2_fund/purchaseFinancial";
    /**
     * 众智存—我购买的/我的存款
     */
    public static String MY_PRODUCT = BASE_API + "/api/v2_fund/myFund";
    /**
     * 众智存—全额支取
     */
    public static String SELL_ALL = BASE_API + "/api/v2_fund/withdrawAll";
    /**
     * 众智存--部分支取
     */
    public static String SELL = BASE_API + "/api/v2_fund/withdrawPart";
    /**
     * 电子账户充值
     */
    public static String ELE_PAY_IN = BASE_API + "/api/v2_ele/eleCharge";
    /**
     * 电子账户提现
     */
    public static String ELE_PAY_OUT = BASE_API + "/api/v2_ele/eleWithdraw";
    /**
     * 电子账户修改绑定卡
     */
    public static String MODIFY_ELE_CARD = BASE_API + "/api/v2_ele/newUpdateEle";
    /**
     * 电子账户修改密码
     */
    public static String MODIFY_ELE_PASSWORD = BASE_API + "/api/v2_ele/updateElePassword";
    /**
     * 账户注销
     */
    public static String ELE_WRITE_OFF = BASE_API + "/api/v2_ele/removeEleUser";
    /**
     * 钱包修改手机号
     */
    public static String WALLET_MODIFY_TEL = BASE_API + "/api/v2_personal/updatePhone";

}
