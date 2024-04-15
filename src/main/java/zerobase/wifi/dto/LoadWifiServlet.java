package zerobase.wifi.dto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zerobase.wifi.service.GetWifiInfo;

import java.io.IOException;

@WebServlet("/load-wifi")
public class LoadWifiServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int wifiTotalCount = GetWifiInfo.saveDateToSQLite();

        request.setAttribute("wifiTotalCount", wifiTotalCount);
        request.getRequestDispatcher("/load-wifi.jsp").forward(request, response);
    }

}
