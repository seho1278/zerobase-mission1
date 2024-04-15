package zerobase.wifi.dto;

import zerobase.wifi.model.HistoryModel;
import zerobase.wifi.service.GetWifiInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/history")
public class GetHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<HistoryModel> savedHistory = GetWifiInfo.getHistoryList();

        // 리스트 역순으로 정렬
        Collections.reverse(savedHistory);

        // 조회된 정보 request에 저장
        request.setAttribute("savedHistory", savedHistory);
        request.getRequestDispatcher("/history.jsp").forward(request, response);
    }
}
