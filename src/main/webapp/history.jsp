<%@ page import="java.util.List" %>
<%@ page import="zerobase.wifi.model.HistoryModel" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="/res/css/main.css" rel="stylesheet"/>
    <script src="/res/js/index.js"></script>
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<jsp:include page="inc_menu.jsp"/>
<div class="api-action">
    <form id="locationForm" action="/detail" method="post">
        <label>
            LAT:
            <input type="text" name="lat" id="inputLat">
        </label>
        ,
        <label>
            LNT:
            <input type="text" name="lnt" id="inputLnt">
        </label>
        <button type="button" id="getPositionButton">
            내 위치 가져오기
        </button>
        <button type="submit">
            근처 WIFI 정보 보기
        </button>
    </form>
</div>
<table class="table-list">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <%
        List<HistoryModel> historyList = (List<HistoryModel>) request.getAttribute("savedHistory");
        if (historyList != null) {
            for (HistoryModel hm : historyList) {
    %>
    <tr>
        <td><%= hm.getHIS_ID() %></td>
        <td><%= hm.getLAT() %></td>
        <td><%= hm.getLNT() %></td>
        <td><%= hm.getHIS_DT() %></td>
        <td class="td-center">
            <form action="/delete" method="post">
                <input type="hidden" name="historyId" value="<%= hm.getHIS_ID() %>">
                <button type="submit">삭제</button>
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>