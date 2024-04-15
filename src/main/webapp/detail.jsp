<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="zerobase.wifi.dto.Row" %>
<%@ page import="java.util.List" %>
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
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    <%
        List<Row> nearbyWifiInfo = (List<Row>) request.getAttribute("nearbyWifiInfo");
        if (nearbyWifiInfo != null && !nearbyWifiInfo.isEmpty()) {
            for (Row row : nearbyWifiInfo) {
    %>
    <tr>
        <td><%= row.getDistance() %></td>
        <td><%= row.getX_SWIFI_MGR_NO() %></td>
        <td><%= row.getX_SWIFI_WRDOFC() %></td>
        <td><%= row.getX_SWIFI_MAIN_NM() %></td>
        <td><%= row.getX_SWIFI_ADRES1() %></td>
        <td><%= row.getX_SWIFI_ADRES2() %></td>
        <td><%= row.getX_SWIFI_INSTL_FLOOR() %></td>
        <td><%= row.getX_SWIFI_INSTL_TY() %></td>
        <td><%= row.getX_SWIFI_INSTL_MBY() %></td>
        <td><%= row.getX_SWIFI_SVC_SE() %></td>
        <td><%= row.getX_SWIFI_CMCWR() %></td>
        <td><%= row.getX_SWIFI_CNSTC_YEAR() %></td>
        <td><%= row.getX_SWIFI_INOUT_DOOR() %></td>
        <td><%= row.getX_SWIFI_REMARS3() %></td>
        <td><%= row.getLAT() %></td>
        <td><%= row.getLNT() %></td>
        <td><%= row.getWORK_DTTM() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="17">조회된 데이터가 없습니다.</td>
    </tr>
    <% } %>

</table>

</body>
</html>