package zerobase.wifi.dto;

import zerobase.wifi.model.HistoryModel;
import zerobase.wifi.service.GetWifiInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/detail")
public class GetWifiInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 입력한 lat, lnt값 가져옴
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lnt = Double.parseDouble(request.getParameter("lnt"));

        // history에 저장
        HistoryModel history = new HistoryModel();
        history.setLAT(lat);
        history.setLNT(lnt);

        GetWifiInfo.saveHistory(history);

        // 데이터베이스에서 근처 wifi 정보 조회
        List<Row> nearbyWifiInfo = GetWifiInfo.getDateFromSQLite(lat, lnt);

        // 조회된 정보 request에 저장
        request.setAttribute("nearbyWifiInfo", nearbyWifiInfo);

        request.getRequestDispatcher("/detail.jsp").forward(request, response);
    }

}
