package com.lebrwcd.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduorder.entity.Order;
import com.lebrwcd.eduorder.entity.PayLog;
import com.lebrwcd.eduorder.mapper.PayLogMapper;
import com.lebrwcd.eduorder.service.OrderService;
import com.lebrwcd.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.eduorder.utils.HttpClientUtil;
import com.lebrwcd.eduorder.utils.YamlConstantUtil;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.management.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;


    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    @Override
    public Map createNative(String orderNo) {

        //1.根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        //2.使用map设置生成二维码需要的参数
        Map m = new HashMap<>();
        m.put("appid", YamlConstantUtil.appid);
        m.put("mch_id", YamlConstantUtil.partner);
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", YamlConstantUtil.notifyurl+"\n");
        m.put("trade_type", "NATIVE");

        //3.发送httpclient请求，需要传递xml格式的参数，微信支付提供的固定地址
        HttpClientUtil httpClient = new HttpClientUtil("https://api.mch.weixin.qq.com/pay/unifiedorder");

        //3.1 client设置参数
        try {

            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m, YamlConstantUtil.partnerKey));
            httpClient.setHttps(true);
            httpClient.post();

            //3.2返回第三方的数据
            String xml = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //3.3 封装最终返回的map
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            //二维码地址
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);

            //4. 返回map
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"生成二维码失败");
        }

    }


    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {

        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", YamlConstantUtil.appid);
            m.put("mch_id", YamlConstantUtil.partner);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClientUtil client = new HttpClientUtil("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, YamlConstantUtil.partnerKey));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //6、转成Map
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 更新支付记录
     * @param map
     */
    @Override
    public void updateOrder(Map<String, String> map) {

        //获取订单id
        String orderNo = map.get("out_trade_no");

        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        //1为已支付，不需要更新
        if (order.getStatus().intValue() == 1) {
            return;
        }

        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志--开始
        PayLog payLog = new PayLog();
        //支付订单号
        payLog.setOrderNo(order.getOrderNo());
        payLog.setPayTime(new Date());
        //支付类型
        payLog.setPayType(1);
        //总金额(分)
        payLog.setTotalFee(order.getTotalFee());
        //支付状态
        payLog.setTradeState(map.get("trade_state"));
        payLog.setTransactionId(map.get("transaction_id"));
        //其他属性
        payLog.setAttr(JSONObject.toJSONString(map));

        //记录支付日志--结束
        baseMapper.insert(payLog);
    }


}
