package zerobase.wifi.dto;

import zerobase.wifi.service.GetWifiInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int historyId = Integer.parseInt(request.getParameter("historyId"));

        GetWifiInfo.deleteHistory(historyId);

        response.sendRedirect(request.getHeader("referer"));
    }
}
