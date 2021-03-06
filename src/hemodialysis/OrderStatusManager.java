package hemodialysis;

import db.MysqlHelper;
import launcher.Main;

/**
 * Created by 31344 on 2016/3/31.
 * 实时更新医嘱状态，把过期的医嘱禁用
 */
public class OrderStatusManager {

    /**
     * 检查医嘱的停止时间，如果医嘱过期，修改医嘱的状态
     */
    public void updateOrderStatus(){
        updateLongTermOrderStatus();
        updateShortTermOrderStatus();
    }

    /**
     * 判断是否有短期医嘱正在使用，但是停止时间在今天之前
     */
    private void updateShortTermOrderStatus() {
        MysqlHelper helper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        helper.getConnection();

        String sql = "update shortterm_ordermgt \n" +
                "set shord_actst = '00002' \n" +
                "where shord_id > 0 and shord_actst = '00001' and shord_dtactst <> '' and shord_dtactst is not null and shord_dtactst < curdate()";
        helper.executeUpdate(sql);

        helper.closeConnection();
    }

    /**
     * 判断是否有长期医嘱正在使用，但是停止时间在今天之前
     */
    private void updateLongTermOrderStatus() {
        MysqlHelper helper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        helper.getConnection();

        String sql = "update longterm_ordermgt \n" +
                "set lgord_actst = '00002' \n" +
                "where lgord_id > 0 and lgord_actst = '00001' and lgord_dtactst <> '' and lgord_dtactst is not null and lgord_dtactst < curdate()";
        helper.executeUpdate(sql);

        helper.closeConnection();
    }
}
